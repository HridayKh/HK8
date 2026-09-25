import json
SIGNAL_MAP = {
    "MEM.MEM_ADDR_B1": 0,
    "MEM.MEM_ADDR_B2": 1,
    "MEM.MEM_OUT_B1": 2,
    "MEM.MEM_OUT_B2": 3,
    "MEM.MEM_IN_B1": 4,
    "RF.R1_OUT_B1": 5,
    "RF.R1_IN_B1": 6,
    "RF.R1_IN_B2": 7,
    "RF.R2_OUT_B2": 8,
    "RF.R2_IN_B1": 9,
    "RF.R2_IN_B2": 10,
    "RF.RALU_IN_B1": 11,
    "PC.PC_OUT_B2": 12,
    "PC.PC_IN_B1": 13,
    "PC.PC_INC": 14,
    "ALU.ALU_ADD": 15,
    "ALU.ALU_SUB": 16,
    "ALU.ALU_INC": 17,
    "ALU.ALU_DEC": 18,
    "ALU.ALU_NOT": 19,
    "ALU.ALU_AND": 20,
    "ALU.ALU_OR": 21,
    "ALU.ALU_XOR": 22,
    "ALU.ALU_SHL": 23,
    "ALU.ALU_SHR": 24,
    "ALU.RID_IN_A1": 25,
    "ALU.RES_OUT_B1": 26,
    "IR.IR_IN_B1": 27,
    "CU.DONE": 28,
    "CU.HALT": 29,
    "FETCH": 30,
    "ALU_OUT": 31,
    "JUMP": 32
}
code = """
- 00: NOP - No operation for 1 cycle. # Must be add the adr 0 as blank addresses are initialised as 0
  0. `FETCH`, `CU.DONE`

- 00: LOAD - [srcMemAddrReg, destValReg] *memory -> register* (address from register)
  1. `RF.R1_OUT_B1`, `MEM.MEM_ADDR_B1`, `MEM.MEM_OUT_B2`, `RF.R2_IN_B2`, `CU.DONE`

- 01: STR - [srcValReg, destMemAddrReg] *register -> memory* (address from register)
  1. `RF.R2_OUT_B2`, `MEM.MEM_ADDR_B2`, `RF.R1_OUT_B1`, `MEM.MEM_IN_B1`, `CU.DONE`

- 02: COPY - [srcReg, destReg] copy value from one register to another register
  1. `RF.R1_OUT_B1`, `RF.R2_IN_B1`, `CU.DONE`

- 03: IMM - set a register to an immideate value
  1. `PC.PC_INC`, `PC.PC_OUT_B2`, `MEM.MEM_ADDR_B2`, `MEM.MEM_OUT_B1`, `RF.R1_IN_B1`, `CU.DONE`

- 04: PCS - Store program counter value (ie cutrent instruction address) to register
  1. `PC.PC_OUT_B2`, `RF.R1_IN_B2`, `CU.DONE`

- 20: ALU - set the output register for the alu operations
  1. `ALU.RID_IN_A1`, `CU.DONE`

- 21: ADD - add two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_ADD`
  2. `ALU_OUT`

- 22: SUB - subtract two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_SUB`
  2. `ALU_OUT`

- 23: INC - increment a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_INC`
  2. `ALU_OUT`

- 24: DEC - decrement a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_DEC`
  2. `ALU_OUT`

- 25: NOT - not a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_NOT`
  2. `ALU_OUT`

- 26: AND - and two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_AND`
  2. `ALU_OUT`

- 27: OR - or two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_OR`
  2. `ALU_OUT`

- 28: XOR - xor two registers and store in output register
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_XOR`
  2. `ALU_OUT`

- 29: SHL - shift left a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_SHL`
  2. `ALU_OUT`

- 30: SHR - shift right a register and store in output register
  1. `RF.R1_OUT_B1`, `ALU.ALU_SHR`
  2. `ALU_OUT`

- 40: CMP - compare two registers (does subtract but does not store value to output register, only to alu internal temp register, sets flags)
  1. `RF.R1_OUT_B1`, `RF.R2_OUT_B2`, `ALU.ALU_SUB`, `CU.DONE`

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

- 50: RETI - return from interupt (only used in interupt triggered from the interupt handler externally, no way to start an interupt other ways)
  1. `CU.DONE`

- 51: EI - enable interupts
  1. `CU.DONE`

- 52: DI - disable interupts
  1. `CU.DONE`

## Other (55-63, 1/8)

- 63: HALT - stop the clock
  0. `CU.HALT`
""".splitlines()

ins = {}
curr_ins = {}
curr_ins_adr = ""
for i in code:
    if i.strip() == "" or i.strip().startswith("#"):
        continue
    if i.startswith("-"):
        ins[curr_ins_adr] = curr_ins
        curr_ins_adr = i.split(":")[0].strip().replace("-", "").strip()
        name = i.split(":")[1].strip().split(" - ")[0].strip()
        curr_ins = {"name": name, "steps": [], "notes": ""}
    else:
        if i.split(". ")[0].strip() != "0" and len(curr_ins["steps"]) == 0:
            curr_ins["steps"].append(["FETCH"])
        steps = i.split(". ")[1].replace("`", "").strip().split(", ")
        curr_ins["steps"].append([x.replace(".", "__") for x in steps])
json.dump(ins, open("instructions.json", "w"), indent=4)
