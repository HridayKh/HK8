import json

# Your JSON structure
data = [
    {
        "adr": "00",
        "name": "NOP",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30,
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "01",
        "name": "LOAD",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            0,
                            3,
                            10,
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "02",
        "name": "STR",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            8,
                            1,
                            5,
                            4,
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "03",
        "name": "COPY",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            9,
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "04",
        "name": "IMM",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            14,
                            12,
                            1,
                            2,
                            6,
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "05",
        "name": "PCS",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            12,
                            7,
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "20",
        "name": "ALU",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            25,
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "21",
        "name": "ADD",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            8,
                            15
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "22",
        "name": "SUB",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            8,
                            16
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "23",
        "name": "INC",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            17
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "24",
        "name": "DEC",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            18
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "25",
        "name": "NOT",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            19
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "26",
        "name": "AND",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            8,
                            20
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "27",
        "name": "OR",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            8,
                            21
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "28",
        "name": "XOR",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            8,
                            22
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "29",
        "name": "SHL",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            23
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "30",
        "name": "SHR",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            24
                        ]
                    },
                    {
                        "step_num": "2",
                        "signals": [
                            31
                        ]
                    }
                ]
    },
    {
        "adr": "40",
        "name": "CMP",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            5,
                            8,
                            16,
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "41",
        "name": "JMP",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            32
                        ]
                    }
                ]
    },
    {
        "adr": "42",
        "name": "JZ",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            32
                        ]
                    }
                ]
    },
    {
        "adr": "43",
        "name": "JNZ",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            32
                        ]
                    }
                ]
    },
    {
        "adr": "44",
        "name": "JC",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            32
                        ]
                    }
                ]
    },
    {
        "adr": "45",
        "name": "JNC",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            32
                        ]
                    }
                ]
    },
    {
        "adr": "50",
        "name": "RETI",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "51",
        "name": "EI",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "52",
        "name": "DI",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            30
                        ]
                    },
                    {
                        "step_num": "1",
                        "signals": [
                            28
                        ]
                    }
                ]
    },
    {
        "adr": "63",
        "name": "HALT",
                "steps": [
                    {
                        "step_num": "0",
                        "signals": [
                            29
                        ]
                    }
                ]
    }
]

# Microcode ROM Configuration
NUM_OPCODES = 64
MAX_STEPS = 4
MAX_SIGNALS = 8

# Initialize empty lookup table [64 opcodes][4 steps][8 signals]
microcode_rom = [[[0 for _ in range(MAX_SIGNALS)] for _ in range(
    MAX_STEPS)] for _ in range(NUM_OPCODES)]

for item in data:
    opcode = int(item["adr"])
    for step in item["steps"]:
        step_idx = int(step["step_num"])
        signals = step["signals"]

        # Insert signals into matrix (pad remainder with 0 / NOP signal)
        for sig_idx, sig in enumerate(signals[:MAX_SIGNALS]):
            microcode_rom[opcode][step_idx][sig_idx] = sig

# Output as a compact C Header file array
print("const uint8_t MICROCODE_ROM[64][4][8] = {")
for op in range(NUM_OPCODES):
    print(f"    // Opcode {op:02d}")
    print("    {")
    for step in range(MAX_STEPS):
        sigs_str = ", ".join(f"{sig:2d}" for sig in microcode_rom[op][step])
        print(f"        {{ {sigs_str} }},")
    print("    },")
print("};")
