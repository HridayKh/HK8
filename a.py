l = """- 00: LOAD - [srcMemAddrReg, destValReg] *memory -> register* (address from register)
- 01: STR - [srcValReg, destMemAddrReg] *register -> memory* (address from register)
- 02: PSI - set port from immideate value
- 03: PSR - set port from register value
- 04: PSS - (port select (register) store) store current port to register
- 05: PEI - set port execute from immideate value
- 06: PER - set port execute from register value
- 07: PES - store current port execute to register
- 08: COPY - [srcReg, destReg] copy value from one register to another register
- 09: IMM - set a register to an immideate value
- 10: PCS - Store program counter value (ie cutrent instruction address) to register
- 20: ALU - set the output register for the alu operations
- 21: ADD - add two registers and store in output register
- 22: SUB - subtract two registers and store in output register
- 23: INC - increment a register and store in output register
- 24: DEC - decrement a register and store in output register
- 25: NOT - not a register and store in output register
- 26: AND - and two registers and store in output register
- 27: OR - or two registers and store in output register
- 28: XOR - xor two registers and store in output register
- 29: SHL - shift left a register and store in output register
- 30: SHR - shift right a register and store in output register
- 40: CMP - compare two registers (does subtract but does not store value to output register, only to alu internal temp register, sets flags)
- 41: JMP - jump to address from register
- 42: JZ - jump to address from register if zero flag is set
- 43: JNZ - jump to address from register if zero flag is not set
- 44: JC - jump to address from register if carry flag is set
- 45: JNC - jump to address from register if carry flag is not set
- 50: RETI - return from interupt (only used in interupt triggered from the interupt handler externally, no way to start an interupt other ways)
- 51: EI - enable interupts
- 62: NOP - No operation for 1 cycle.
- 63: HALT - stop the clock""".splitlines()
p = []
for i in l:
    i = i[2:]
    a, desc = i.split(" - ", 1)
    adr, name = a.split(": ", 1)
    print("\n\n" + i)
    args = int(input("args: "))
    p.append(f"{adr},{name},{args},n")

for i in p:
	print(i)