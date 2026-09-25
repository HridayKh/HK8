#include <stdio.h>

// variable convention
// camelCase: component of hk8
// UPPER_CASE: code vars/macros/sizes/constants

typedef unsigned char u8;
typedef unsigned short u16;
typedef unsigned int u32;
int _HALTED = 0;

u16 ir = 0;
#define OPCODE ((ir >> 10) & 0x3F)
#define ARG1 ((ir >> 5) & 0x1F)
#define ARG2 (ir & 0x1F)

u16 pc = 0;

#define MEMORY_SIZE 65536
u16 mem[MEMORY_SIZE];
u16 mem_adr = 0;

#define REGISTER_FILE_SIZE 32
u16 reg[REGISTER_FILE_SIZE] = {0};
u16 ralu = 0;

u16 ALU_OUT = 0;

u8 flags = 0;
#define FLAG_CARY (1 << 3) // 0b0000 1000 (8)
#define FLAG_ZERO (1 << 2) // 0b0000 0100 (4)
#define FLAG_INTR (1 << 1) // 0b0000 0010 (2)
#define FLAG_MODE (1 << 0) // 0b0000 0001 (1)
#define UPDATE_ALU_FLAGS(temp)                                                 \
  do {                                                                         \
    flags = (flags & ~FLAG_CARY) | (((temp) & 0x10000) >> 13);                 \
    flags = (flags & ~FLAG_ZERO) | ((ALU_OUT == 0) << 2);                      \
  } while (0)
#define IS_KERNEL_MODE ((flags & FLAG_MODE) == 0)

u16 bus1 = 0;
u16 bus2 = 0;
u8 step = 0;
u8 subStep = 0;

void MEM__MEM_ADDR_B1() { mem_adr = bus1; }
void MEM__MEM_ADDR_B2() { mem_adr = bus2; }
void MEM__MEM_OUT_B1() { bus1 = mem[mem_adr]; }
void MEM__MEM_OUT_B2() { bus2 = mem[mem_adr]; }
void MEM__MEM_IN_B1() { mem[mem_adr] = bus1; }

void RF__R1_OUT_B1() { bus1 = reg[ARG1]; }
void RF__R1_IN_B1() { reg[ARG1] = bus1; }
void RF__R1_IN_B2() { reg[ARG1] = bus2; }
void RF__R2_OUT_B2() { bus2 = reg[ARG2]; }
void RF__R2_IN_B1() { reg[ARG2] = bus1; }
void RF__R2_IN_B2() { reg[ARG2] = bus2; }
void RF__RALU_IN_B1() { ralu = bus1; }

void PC__PC_OUT_B2() { bus2 = pc; }
void PC__PC_IN_B1() { pc = bus1; }
void PC__PC_INC() { pc++; }

void ALU__ALU_ADD() {
  u32 temp = (u32)bus1 + (u32)bus2;
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_SUB() {
  u32 temp = (u32)bus1 + (0xFFFF ^ (u32)bus2) + (u32)1; // 2's compliment
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_INC() {
  u32 temp = (u32)bus1 + (u32)1;
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_DEC() {
  u32 temp = (u32)(u16)(bus1 - 1);
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_NOT() {
  u32 temp = (u32)(~bus1);
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_AND() {
  u32 temp = (u32)(bus1 & bus2);
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_OR() {
  u32 temp = (u32)(bus1 | bus2);
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_XOR() {
  u32 temp = (u32)(bus1 ^ bus2);
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_SHL() {
  u32 temp = (u32)bus1 << (u32)1;
  ALU_OUT = (u16)temp;
  UPDATE_ALU_FLAGS(temp);
}
void ALU__ALU_SHR() {
  u32 temp = ((u32)bus1 >> 1) | (((u32)bus1 & 1) << 16);
  ALU_OUT = (u16)(bus1 >> 1);
  UPDATE_ALU_FLAGS(temp);
}
void ALU__RID_IN_A1() { reg[ralu] = ARG1; }
void ALU__RES_OUT_B1() { bus1 = ALU_OUT; }

void IR__IR_IN_B1() { ir = bus1; }

void CU__DONE() { // prep for next clock cycle
  bus1 = 0;
  bus2 = 0;
  step = 0;
  subStep = -1;
  for (int i = 0; i < 32; i++) {
    printf("%s%d: 0x%04X\t", i < 10 ? " " : "", i, reg[i]);
    if ((i + 1) % 8 == 0) {
      printf("\n");
    }
  }
}
void CU__HALT() { _HALTED = 1; }

// temp shortcuts
void MACRO__FETCH() {
  PC__PC_OUT_B2();
  MEM__MEM_ADDR_B2();
  MEM__MEM_OUT_B1();
  IR__IR_IN_B1();
  PC__PC_INC();
}
void MACRO__ALU_OUT() {
  ALU__RES_OUT_B1();
  RF__RALU_IN_B1();
  CU__DONE();
}
void MACRO__JUMP() {
  RF__R1_OUT_B1();
  PC__PC_IN_B1();
  CU__DONE();
}

typedef void (*func)(void);

func CONTROL_ID_TO_SIGNAL[33] = {
    MEM__MEM_ADDR_B1, MEM__MEM_ADDR_B2, MEM__MEM_OUT_B1, MEM__MEM_OUT_B2, // 0
    MEM__MEM_IN_B1,   RF__R1_OUT_B1,    RF__R1_IN_B1,    RF__R1_IN_B2,    // 4
    RF__R2_OUT_B2,    RF__R2_IN_B1,     RF__R2_IN_B2,    RF__RALU_IN_B1,  // 8
    PC__PC_OUT_B2,    PC__PC_IN_B1,     PC__PC_INC,      ALU__ALU_ADD,    // 12
    ALU__ALU_SUB,     ALU__ALU_INC,     ALU__ALU_DEC,    ALU__ALU_NOT,    // 16
    ALU__ALU_AND,     ALU__ALU_OR,      ALU__ALU_XOR,    ALU__ALU_SHL,    // 20
    ALU__ALU_SHR,     ALU__RID_IN_A1,   ALU__RES_OUT_B1, IR__IR_IN_B1,    // 24
    CU__DONE,         CU__HALT,         MACRO__FETCH,    MACRO__ALU_OUT,  // 28
    MACRO__JUMP};                                                         // 32

#include "microcode.c"

#define USER_ROM KERNEL_ROM

int main(void) {
  printf("Starting CPU simulation...\n");
  
  mem[0] = (u16)0x0000; // NOP
  mem[1] = (u16)0x13E0; // IMM r31, ...
  mem[2] = (u16)0xabcd; // Data
  mem[3] = (u16)0xfc00; // HALT
  
  u32 clock = 0;
  while (!_HALTED) {
    if (subStep >= 8 && subStep != 255) { // start the next step
      subStep = 0;
      step++;
      if (step >= 4) {
        printf("ERROR: Instruction reached step 4 without CU__DONE\n");
        break;
      }
    }

    if (step == 0 && subStep == 0) // clock and pc at start of ever instruciton
      printf("\n\n[%d] pc %d\n", clock++, pc);

    u8 sig = IS_KERNEL_MODE ? KERNEL_ROM[OPCODE][step][subStep]
                            : USER_ROM[OPCODE][step][subStep];
    u8 sig_next = subStep < 7
                      ? (IS_KERNEL_MODE ? KERNEL_ROM[OPCODE][step][subStep + 1]
                                        : USER_ROM[OPCODE][step][subStep + 1])
                      : sig;

    printf("MICROCODE_ROM[%d][%d][%d] = %d\n", OPCODE, step, subStep, sig);

    if (sig == 0 && sig_next == 0) {
      subStep = 0;
      step++;
      continue;
    }

    if (sig >= 33) {
      printf("ERROR: Unrecognized signal ID %d at OP:%d Step:%d Sub:%d\n", sig,
             OPCODE, step, subStep);
      break;
    }
    CONTROL_ID_TO_SIGNAL[sig]();

    subStep++;
  }

  printf("Halted successfully.\n");
  return 0;
}