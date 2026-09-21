code = """
nop
imm 31 ; load into reg 31 000001 11111 00000
14	; value to be loaded
halt
""".splitlines()

final = []


def name_to_num(name):
    if name == "nop":
        return 0
    elif name == "imm":
        return 1
    elif name == "halt":
        return 2
    else:
        raise ValueError(f"Unknown instruction: {name}")


for line in code:
    line = line.split(';')[0].strip()
    if not line:
        continue
    parts = line.split()
    num = 0
    isNum = False
    try:
        num = int(parts[0])
        isNum = True
    except ValueError:
        pass
    if isNum:
        final.append(num)
    else:
        num = name_to_num(parts[0]) << 10
        if len(parts) > 1:
            num |= int(parts[1]) << 5
            if len(parts) > 2:
                num |= int(parts[2])
        final.append(num)
print("{", end="")
for i in final:
    print(f'{i}, ', end="")
print("}")
