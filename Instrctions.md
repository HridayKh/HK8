# HK8 Instrctions

> The instructions with more than one 4-Bit input (for register, ports, etc.) have their order defined as `[Arg1, Arg2]`

## Macros

> Macros to define repeated combinations of micro-instructions.

1. **`FETCH`**: `PC.PC_OUT_B1`, `MEM.MEM_ADDR_B1`, `MEM.MEM_OUT_B2`, `IR.IR_IN_B2`
   > All instructions have implied step 0 as the `FETCH` macro unless specified otherwise.
2. **`DONE`**: `PC.PC_INC`, `CU.INS_DONE`
   > All instructions have implied last step as the `DONE` macro unless specified otherwise.

## No Operation

- 00: NOP - No operation for 1 cycle.
  0. `FETCH`, `DONE`

## Ports, Memory, Registers, and Addressing (1-20)

- 01: LDI - get from memory to register (immideate value for address)
  1. `PC.PC_INC`, `PC.PC_OUT_B1`, `MEM.MAR_IN_B1`, `MEM.MEM_OUT_B2`, `RF.R1_IN_B2`
  2. `RF.R1_OUT_B1`, `MEM.MAR_IN_B1`
  3. `MEM.MEM_OUT_B1`, `RF.R1_IN_B1`

- 02: LDR - [addrReg, destReg] get from memory to register (address from register)
  1. `RF.R1_OUT_B1`, `MEM.MAR_IN_B1`
  2. `MEM.MEM_OUT_B1`, `RF.R1_IN_B1`

- 03: STI - store to memory from register (immideate value for address)
- 04: STR - store to memory from register (address from register)
- 05: PORTI - set port from immideate value
- 06: PORTR - set port from register value
- 07: PORTS - store current port to register
- 08: PERI - set port execute from immideate value
- 09: PERR - set port execute from register value
- 10: PERS - store current port execute to register
- 11: COPY - copy value from one register to another register
- 12: IMM - set a register to an immideate value
- 13: PCS - Store program counter value (ie cutrent instruction address) to register

## ALU (21-40)

- 21: ALU - set the output register for the alu operations
- 22: ADD - add two registers and store in output register
- 23: ADDI - add a register and an immideate value and store in output register
- 24: SUB - subtract two registers and store in output register
- 25: SUBI - subtract an immideate value from a register and store in output register
- 26: INC - increment a register and store in output register
- 27: DEC - decrement a register and store in output register
- 28: NOT - not a register and store in output register
- 29: AND - and two registers and store in output register
- 30: ANDI - and a register and an immideate value and store in output register
- 31: OR - or two registers and store in output register
- 32: ORI - or a register and an immideate value and store in output register
- 33: XOR - xor two registers and store in output register
- 34: XORI - xor a register and an immideate value and store in output register
- 35: SHL - shift left a register and store in output register
- 36: SHR - shift right a register and store in output register

## Branching (41-55)

- 41: CMP - compare two registers (does subtract but does not store value to output register, only to alu internal temp register, sets flags)
- 42: CMPI - compare a register and an immideate value
- 43: JMP - jump to address from register
- 44: JMPI - jump to address from immideate value
- 45: JZ - jump to address from register if zero flag is set
- 46: JZI - jump to address from immideate value if zero flag is set
- 47: JNZ - jump to address from register if zero flag is not set
- 48: JNZI - jump to address from immideate value if zero flag is not set
- 49: JC - jump to address from register if carry flag is set
- 50: JCI - jump to address from immideate value if carry flag is set
- 51: JNC - jump to address from register if carry flag is not set
- 52: JNCI - jump to address from immideate value if carry flag is not set

## Interupts (56-60)

- 56: RETI - return from interupt (only used in interupt triggered from the interupt handler externally, no way to start an interupt other ways)
- 57: EI - enable interupts
- 58: DI - disable interupts

## Other (61-63)

- 61: HALT - stop the clock
