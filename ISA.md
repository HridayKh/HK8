# 16-Bit Custom ISA Reference (Revised)

## Data Movement & Immediates
- 0x01: LI Ra, [imm]     | Ra = Next Word
- 0x02: MOV Ra, Rb       | Ra = Rb
- 0x05: LPSRI [imm]      | PSR = Next Word (Set Port via immediate)
- 0x06: LMARI [imm]      | MAR = Next Word (Set Address via immediate)

## ALU Operations (Results go to Reg ID in ALU_OUT_REG)
- 0x10: ADD Ra, Rb       | ALU_Out = Ra + Rb; Set Flags
- 0x11: SUB Ra, Rb       | ALU_Out = Ra - Rb; Set Flags
- 0x12: SETALU Ra        | ALU_Output_ID_Register = Ra (Default R15)

## Memory & Port Access (Uses PSR + MAR)
- 0x20: LMAR Ra          | MAR = Ra (Set address from register)
- 0x21: RMAR Ra          | Ra = MAR (Read current address)
- 0x22: LOAD Ra          | Ra = Port[PSR] at Address[MAR]
- 0x23: STORE Ra         | Port[PSR] at Address[MAR] = Ra
- 0x25: SETADDR Ra, Rb   | PSR = Ra, MAR = Rb (Set both in one go)

## Control Flow
- 0x30: SPSR Ra          | PSR = Ra (Set Port from register)
- 0x31: JMP [addr]       | PC = Next Word
- 0x32: JZ [addr]        | If Zero Flag: PC = Next Word
- 0x33: JC [addr]        | If Carry Flag: PC = Next Word
- 0x3F: HALT             | Signal End of Instruction

## Peripherals
- 0x40: OUTR Ra          | Segment_Display_ID_Register = Ra (Default R15)
