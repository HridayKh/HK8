# HK8 Instrctions

## No Operation

- 00: NOP - No operation

## Ports, Memory, Registers, and Addressing (0-10)

- 01: LDI - get from memory to register (immideate value for address)
- 02: LDR - get from memory to register (address from register)
- 03: STI - store to memory from register (immideate value for address)
- 04: STR - store to memory from register (address from register)
- 05: PORTI - set port from immideate value
- 06: PORTR - set port from register value
- 07: PORTS - store current port to register
- 08: COPY - copy value from one register to another register

## ALU (11-30)

- 11: ALU - set the output register for the alu operations
- 12: ADD - add two registers and store in output register
- 13: ADDI - add a register and an immideate value and store in output register
- 14: SUB - subtract two registers and store in output register
- 15: SUBI - subtract an immideate value from a register and store in output register
- 16: INC - increment a register and store in output register
- 17: DEC - decrement a register and store in output register
- 18: NOT - not a register and store in output register
- 19: AND - and two registers and store in output register
- 20: ANDI - and a register and an immideate value and store in output register
- 21: OR - or two registers and store in output register
- 22: ORI - or a register and an immideate value and store in output register
- 23: XOR - xor two registers and store in output register
- 24: XORI - xor a register and an immideate value and store in output register

## Branching (31-50)

- 31: CMP - compare two registers (does subtract but does not store value to output register, only to alu internal temp register, sets flags)
- 32: CMPI - compare a register and an immideate value
- 33: JMP - jump to address from register
- 34: JMPI - jump to address from immideate value
- 35: JZ - jump to address from register if zero flag is set
- 36: JZI - jump to address from immideate value if zero flag is set
- 37: JNZ - jump to address from register if zero flag is not set
- 38: JNZI - jump to address from immideate value if zero flag is not set
- 39: JC - jump to address from register if carry flag is set
- 40: JCI - jump to address from immideate value if carry flag is set
- 41: JNC - jump to address from register if carry flag is not set
- 42: JNCI - jump to address from immideate value if carry flag is not set

## Interupts (51-55)

- 51: RETI - return from interupt (only used in interupt triggered from the interupt handler externally, no way to start an interupt other ways)
- 52: EI - enable interupts
- 53: DI - disable interupts

## Other (56-63)

- 56: SEG - immideate value only for which register to output on the 7 segment display
- 57: HALT - stop the clock
