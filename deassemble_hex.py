hex = """0000: 0000001111000000
0001: 0000000000000000
0002: 0000000000000000
0003: 0000000000000111
0004: 0000000001000000
0005: 0000000000001010
0006: 0000000010000000
0007: 0000000000000000
0008: 0010110010000000
0009: 0111110000000000
000A: 1001000000000000
000B: 0000000000010011
000C: 0011000010000100
000D: 0010110000000000
000E: 0100010000000000
000F: 0010110010000000
0010: 1000100000000000
0011: 0000000000001010
0012: 0000100010000000
0013: 0000000001100100
0014: 1110000010000000
0015: 1110100000000000"""

m = """0,LDI,1,yes
1,LDR,2,no
2,STI,1,yes
3,STR,2,no
4,PORTI,1,no
5,PORTR,1,no
6,PORTS,1,no
7,COPY,2,no
11,ALU,1,no
12,ADD,2,no
13,ADDI,1,yes
14,SUB,2,no
15,SUBI,1,yes
16,INC,1,no
17,DEC,1,no
18,NOT,1,no
19,AND,2,no
20,ANDI,1,yes
21,OR,2,no
22,ORI,1,yes
23,XOR,2,no
24,XORI,1,yes
25,SHL,1,no
27,SHR,1,no
31,CMP,2,no
32,CMPI,1,yes
33,JMP,1,no
34,JMPI,0,yes
35,JZ,1,no
36,JZI,0,yes
37,JNZ,1,no
38,JNZI,0,yes
39,JC,1,no
40,JCI,0,yes
41,JNC,1,no
42,JNCI,0,yes
51,RETI,0,no
52,EI,0,no
53,DI,0,no
56,SEG,1,no
57,NOP,0,no
58,HALT,0,no"""

mm = {}
for i in m.splitlines():
	opcode, name, args, imm = i.split(",")
	mm[int(opcode)] = {"name": name, "args": int(args), "imm": imm == "yes"}

isWord = False
for i in hex.splitlines():
	address, value = i.split(": ")
	address = int(address, 16)
	if isWord:
		print(f"{address}: {int(value, 2)}")
		isWord = False
		continue
	opcode = int(value[:6], 2)
	arg1 = int(value[6:10], 2)
	arg2 = int(value[10:14], 2)
	empty = int(value[14:], 2)
	if empty != 0:
		print(f"Warning: non-empty bits in instruction at address {address}")
	isWord = mm[opcode]["imm"]
	a1 = str(arg1) if mm[opcode]["args"] == 1 else ""
	a2 = str(arg2) if mm[opcode]["args"] == 2 else ""
	if mm[opcode]["args"] == 0 and a1 != "":
		print(f"Warning: unexpected first argument for instruction at address {address}")
	if mm[opcode]["args"] == 1 and a2 != "":
		print(f"Warning: unexpected second argument for instruction at address {address}")
	print(f"{address}: {mm[opcode]['name']} {a1} {a2}")
