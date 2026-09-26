import json
data = json.loads(
    open("/home/hridaykh/Code/HK8/ins_spec.json", "r").read())["instructions"]

start = "func KERNEL_ROM[64][4][8] = {\n"
opcodeS = "// Opcode [[ADR]]: [[NAME]]\n[[[ADR]]] = {\n"

step = "{[[STEPS]]},"

opcodeE = "},\n"
end = "};"

out = start
for adr, ins in data.items():
    out += opcodeS.replace("[[ADR]]", adr).replace("[[NAME]]", ins["name"])
    steps = ins["steps"]
    for step__ in steps:
        out += step.replace("[[STEPS]]",
                            ", ".join([x.replace(".", "__") for x in step__]))
    out += opcodeE

out += end

print(out)
