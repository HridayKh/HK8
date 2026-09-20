# HK8 Instrctions

> The instructions with more than one 4-Bit input (for register, ports, etc.) have their order defined as `[Arg1, Arg2]`

## Macros

> Macros to define repeated combinations of micro-instructions.

1. **`FETCH`**: `PC.PC_OUT_B2`, `MEM.MEM_ADDR_B2`, `MEM.MEM_OUT_B1`, `IR.IR_IN_B1`, `PC.PC_INC`
    > All instructions have implied step 0 as the `FETCH` unless step 0 specified manually.
2. **`ALU_OUT`**: `ALU.RES_OUT_B1`, `RF.RALU_IN_B1`, `CU.DONE`
3. **`JUMP`**: `RF.R1_OUT_B1`, `PC.PC_IN_B1`, `CU.DONE`
    > Jump flag checking is hardcoded in hardware for the specific instructions

## Ports, Memory, Registers, and Addressing (0-19, 11/20)

- 00: LOAD - [srcMemAddrReg, destValReg] *memory -> register* (address from register)
  1. `RF.R1_OUT_B1`, `MEM.MEM_ADDR_B1`, `MEM.MEM_OUT_B2`, `RF.R2_IN_B2`, `CU.DONE`

- 01: STR - [srcValReg, destMemAddrReg] *register -> memory* (address from register)
  1. `RF.R2_OUT_B2`, `MEM.MEM_ADDR_B2`, `RF.R1_OUT_B1`, `MEM.MEM_IN_B1`, `CU.DONE`

- 02: PSI - set port from immideate value
  1. `MEM.PSR_IN_A1`, `CU.DONE`

- 03: PSR - set port from register value
  1. `RF.R1_OUT_B1`, `MEM.PSR_IN_B1`, `CU.DONE`

- 04: PSS - (port select (register) store) store current port to register
  1. `MEM.PSR_OUT_B1`, `RF.R1_IN_B1`, `CU.DONE`

- 05: PEI - set port execute from immideate value
  1. `MEM.PER_IN_A1`, `CU.DONE`

- 06: PER - set port execute from register value
  1. `RF.R1_OUT_B1`, `MEM.PER_IN_B1`, `CU.DONE`

- 07: PES - store current port execute to register
  1. `MEM.PER_OUT_B1`, `RF.R1_IN_B1`, `CU.DONE`

- 08: COPY - [srcReg, destReg] copy value from one register to another register
  1. `RF.R1_OUT_B1`, `RF.R2_IN_B1`, `CU.DONE`

- 09: IMM - set a register to an immideate value
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `MEM.MEM_ADDR_B2`, `MEM.MEM_OUT_B1`, `RF.R1_IN_B1`, `CU.DONE`

- 10: PCS - Store program counter value (ie cutrent instruction address) to register
  1. `PC.PC_OUT_B2`, `RF.R1_IN_B2`, `CU.DONE`

## ALU (20-39, 11/20)

- 20: ALU - set the output register for the alu operations
  1. `ALU.RID_IN_A1`, `CU.DONE`

- 21: ADD - add two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_ADD`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 22: SUB - subtract two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_SUB`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 23: INC - increment a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_INC`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 24: DEC - decrement a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_DEC`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 25: NOT - not a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_NOT`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 26: AND - and two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_AND`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 27: OR - or two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_OR`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 28: XOR - xor two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_XOR`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 29: SHL - shift left a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_SHL`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 30: SHR - shift right a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_SHR`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

## Branching (40-49, 6/10)

- 40: CMP - compare two registers (does subtract but does not store value to output register, only to alu internal temp register, sets flags)
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_SUB`, `ALU.SET_FLAGS`, `CU.DONE`

- 41: JMP - jump to address from register
  1. `JUMP`

- 42: JZ - jump to address from register if zero flag is set
  1. `JUMP`

- 43: JNZ - jump to address from register if zero flag is not set
  1. `JUMP`

- 44: JC - jump to address from register if carry flag is set
  1. `JUMP`

- 45: JNC - jump to address from register if carry flag is not set
  1. `JUMP`

## Interupts (50-54, 3/5) (not yet implemented)

- 50: RETI - return from interupt (only used in interupt triggered from the interupt handler externally, no way to start an interupt other ways)
  1. `CU.DONE`

- 51: EI - enable interupts
  1. `CU.DONE`

- 52: DI - disable interupts
  1. `CU.DONE`

## Other (55-63, 2/8)

- 62: NOP - No operation for 1 cycle.
  0. `FETCH`, `CU.DONE`

- 63: HALT - stop the clock
  0. `CU.HALT`
