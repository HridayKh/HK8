csv_data = """opcode,name,args,nextWord
0,LDI,1,yes
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


def process_csv_string(data_string):
	lines = data_string.strip().split('\n')
	header = lines[0]
	rows = lines[1:]

	# Store existing data in a dictionary {idx: [name, int, yes/no]}
	data_map = {}
	for row in rows:
		parts = row.split(',')
		if parts:
			idx = int(parts[0])
			data_map[idx] = parts[1:]

	# Print the header back out
	print(header)

	# Iterate through the required range 0-63
	for i in range(64):
		if i in data_map:
			# Join the index with the existing data
			print(f"{i},{','.join(data_map[i])}")
		else:
			# Create a placeholder row
			# Format: idx, name_placeholder, 0, no
			print(f"{i},PLACEHOLDER,0,no")


# Execute
process_csv_string(csv_data)
