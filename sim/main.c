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
u16 mem[MEMORY_SIZE] = {0, 2016, 14, 2048};
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

u16 bus1 = 0;
u16 bus2 = 0;

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
}
void CU__HALT() { _HALTED = 1; }

// shortcuts
void FETCH() {
  PC__PC_OUT_B2();
  MEM__MEM_ADDR_B2();
  MEM__MEM_OUT_B1();
  IR__IR_IN_B1();
  PC__PC_INC();
}

// microcode
typedef void (*micro_op_t)(void);
#define MAX_MICRO_STEPS 4
#define NUM_OPCODES 64
micro_op_t DISPATCH_TABLE[NUM_OPCODES][MAX_MICRO_STEPS] = {
    [0] = {
        FETCH,    	  // Step 0: Drive R1 to bus1
        RF__R2_OUT_B2,    // Step 1: Drive R2 to bus2
        ALU__ALU_ADD,     // Step 2: Compute bus1 + bus2 -> ALU_OUT & update flags
        ALU__RES_OUT_B1,  // Step 3: Drive ALU_OUT to bus1
        RF__R1_IN_B1,     // Step 4: Latch bus1 into reg[ARG1]
        CU__DONE          // Step 5: Reset buses / signal end of instruction
    },

    // Opcode 1: SUB
    [1] = {
        RF__R1_OUT_B1,
        RF__R2_OUT_B2,
        ALU__ALU_SUB,
        ALU__RES_OUT_B1,
        RF__R1_IN_B1,
        CU__DONE
    },

    // Opcode 63: HALT
    [63] = {
        CU__HALT,
        CU__DONE
    }
};

int main(void) {
  printf("starting");
  u8 step = 0;
  while (_HALTED) {
    
  }
  printf("halted");
  return 0;
}
