# HK8 Instrctions

> The instructions with more than one 4-Bit input (for register, ports, etc.) have their order defined as `[Arg1, Arg2]`

## Macros

> Macros to define repeated combinations of micro-instructions.

1. **`FETCH`**: `PC.PC_OUT_B2`, `MEM.MEM_ADDR_B2`, `MEM.MEM_OUT_B1`, `IR.IR_IN_B1`
   > All instructions have implied step 0 as the `FETCH` macro unless specified otherwise.
2. **`DONE`**: `PC.PC_INC`, `CU.INS_DONE`
   > All instructions' last step contains the `DONE` macro unless specified otherwise.
3. **`ALU_OUT`**: `ALU.RES_OUT_B1`, `RF.RALU_IN_B1`

## No Operation

- 00: NOP - No operation for 1 cycle.
  0. `FETCH`, `DONE`

## Ports, Memory, Registers, and Addressing (1-20)

- 01: LDI - *memory -> register* (immideate value for address)
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `MEM.MEM_ADDR_B2`, `MEM.MEM_OUT_B1`, `RF.R1_IN_B1`
  2. `RF.R1_OUT_B1`, `MEM.MEM_ADDR_B1`, `MEM.MEM_OUT_B2`, `RF.R1_IN_B2`

- 02: LDR - [srcMemAddrReg, destValReg] *memory -> register* (address from register)
  1. `RF.R1_OUT_B1`, `MEM.MEM_ADDR_B1`, `MEM.MEM_OUT_B2`, `RF.R2_IN_B2`

- 03: STI - *register -> memory* (immideate value for address)
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `MEM.MEM_ADDR_B2`, `RF.R1_OUT_B1`, `MEM.MEM_IN_B1`

- 04: STR - [srcValReg, destMemAddrReg] *register -> memory* (address from register)
  1. `RF.R2_OUT_B2`, `MEM.MEM_ADDR_B2`, `RF.R1_OUT_B1`, `MEM.MEM_IN_B1`

- 05: PSRI - set port from immideate value
  1. `MEM.PSR_IN_A1`

- 06: PSRR - set port from register value
  1. `RF.R1_OUT_B1`, `MEM.PSR_IN_B1`

- 07: PSRS - store current port to register
  1. `MEM.PSR_OUT_B1`, `RF.R1_IN_B1`

- 08: PERI - set port execute from immideate value
  1. `MEM.PER_IN_A1`

- 09: PERR - set port execute from register value
  1. `RF.R1_OUT_B1`, `MEM.PER_IN_B1`

- 10: PERS - store current port execute to register
  1. `MEM.PER_OUT_B1`, `RF.R1_IN_B1`

- 11: COPY - [srcReg, destReg] copy value from one register to another register
  1. `RF.R1_OUT_B1`, `RF.R2_IN_B1`

- 12: IMM - set a register to an immideate value
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `RF.R1_IN_B2`

- 13: PCS - Store program counter value (ie cutrent instruction address) to register
  1. `PC.PC_OUT_B2`, `RF.R1_IN_B2`

## ALU (21-40)

- 21: ALU - set the output register for the alu operations
  1. `ALU.RID_IN_A1`

- 22: ADD - add two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_ADD`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 23: ADDI - add a register and an immideate value and store in output register
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `RF.R1_OUT_B1`, `ALU.ALU_ADD`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 24: SUB - subtract two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_SUB`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 25: SUBI - subtract an immideate value from a register and store in output register
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `RF.R1_OUT_B1`, `ALU.ALU_SUB`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 26: INC - increment a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_INC`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 27: DEC - decrement a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_DEC`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 28: NOT - not a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_NOT`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 29: AND - and two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_AND`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 30: ANDI - and a register and an immideate value and store in output register
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `RF.R1_OUT_B1`, `ALU.ALU_AND`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 31: OR - or two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_OR`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 32: ORI - or a register and an immideate value and store in output register
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `RF.R1_OUT_B1`, `ALU.ALU_OR`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 33: XOR - xor two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_XOR`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 34: XORI - xor a register and an immideate value and store in output register
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `RF.R1_OUT_B1`, `ALU.ALU_XOR`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 35: SHL - shift left a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_SHL`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

- 36: SHR - shift right a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_SHR`, `ALU.SET_FLAGS`
  2. `ALU_OUT`

## Branching (41-55)

- 41: CMP - compare two registers (does subtract but does not store value to output register, only to alu internal temp register, sets flags)
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_SUB`, `ALU.SET_FLAGS`

- 42: CMPI - compare a register and an immideate value
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `RF.R1_OUT_B1`, `ALU.ALU_SUB`, `ALU.SET_FLAGS`

- 43: JMP - jump to address from register
  1. 

- 44: JMPI - jump to address from immideate value
- 45: JZ - jump to address from register if zero flag is set
- 46: JZI - jump to address from immideate value if zero flag is set
- 47: JNZ - jump to address from register if zero flag is not set
- 48: JNZI - jump to address from immideate value if zero flag is not set
- 49: JC - jump to address from register if carry flag is set
- 50: JCI - jump to address from immideate value if carry flag is set
- 51: JNC - jump to address from register if carry flag is not set
- 52: JNCI - jump to address from immideate value if carry flag is not set

## Interupts (56-60) (not yet implemented)

- 56: RETI - return from interupt (only used in interupt triggered from the interupt handler externally, no way to start an interupt other ways)
  0. `FETCH`, `DONE`

- 57: EI - enable interupts
  0. `FETCH`, `DONE`

- 58: DI - disable interupts
  0. `FETCH`, `DONE`

## Other (61-63)

- 61: HALT - stop the clock
  0. `CU.HALT`
