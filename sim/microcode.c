func KERNEL_ROM[64][4][8] = {
    // Opcode 00: NOP
    [00] =
        {
            {MACRO__FETCH, CU__DONE},
        },
    // Opcode 01: LOAD
    [01] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, MEM__MEM_ADDR_B1, MEM__MEM_OUT_B2, RF__R2_IN_B2,
             CU__DONE},
        },
    // Opcode 02: STR
    [02] =
        {
            {MACRO__FETCH},
            {RF__R2_OUT_B2, MEM__MEM_ADDR_B2, RF__R1_OUT_B1, MEM__MEM_IN_B1,
             CU__DONE},
        },
    // Opcode 03: COPY
    [03] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, RF__R2_IN_B1, CU__DONE},
        },
    // Opcode 04: IMM
    [04] =
        {
            {MACRO__FETCH},
            {PC__PC_OUT_B2, MEM__MEM_ADDR_B2, MEM__MEM_OUT_B1, RF__R1_IN_B1,
             PC__PC_INC, CU__DONE},
        },
    // Opcode 05: IMS
    [05] =
        {
            {MACRO__FETCH},
            {IR__ARG2_TO_B1, RF__R1_IN_B1, CU__DONE},
        },
    // Opcode 06: PCS
    [06] =
        {
            {MACRO__FETCH},
            {PC__PC_OUT_B2, RF__R1_IN_B2, CU__DONE},
        },
    // Opcode 20: ALU
    [20] =
        {
            {MACRO__FETCH},
            {ALU__RID_IN_A1, CU__DONE},
        },
    // Opcode 21: ADD
    [21] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, RF__R2_OUT_B2, ALU__ALU_ADD},
            {MACRO__ALU_OUT},
        },
    // Opcode 22: SUB
    [22] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, RF__R2_OUT_B2, ALU__ALU_SUB},
            {MACRO__ALU_OUT},
        },
    // Opcode 23: INC
    [23] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, ALU__ALU_INC},
            {MACRO__ALU_OUT},
        },
    // Opcode 24: DEC
    [24] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, ALU__ALU_DEC},
            {MACRO__ALU_OUT},
        },
    // Opcode 25: NOT
    [25] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, ALU__ALU_NOT},
            {MACRO__ALU_OUT},
        },
    // Opcode 26: AND
    [26] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, RF__R2_OUT_B2, ALU__ALU_AND},
            {MACRO__ALU_OUT},
        },
    // Opcode 27: OR
    [27] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, RF__R2_OUT_B2, ALU__ALU_OR},
            {MACRO__ALU_OUT},
        },
    // Opcode 28: XOR
    [28] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, RF__R2_OUT_B2, ALU__ALU_XOR},
            {MACRO__ALU_OUT},
        },
    // Opcode 29: SHL
    [29] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, ALU__ALU_SHL},
            {MACRO__ALU_OUT},
        },
    // Opcode 30: SHR
    [30] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, ALU__ALU_SHR},
            {MACRO__ALU_OUT},
        },
    // Opcode 40: CMP
    [40] =
        {
            {MACRO__FETCH},
            {RF__R1_OUT_B1, RF__R2_OUT_B2, ALU__ALU_SUB, CU__DONE},
        },
    // Opcode 41: JMP
    [41] =
        {
            {MACRO__FETCH},
            {MACRO__JUMP},
        },
    // Opcode 42: JZ
    [42] =
        {
            {MACRO__FETCH},
            {MACRO__JUMP},
        },
    // Opcode 43: JNZ
    [43] =
        {
            {MACRO__FETCH},
            {MACRO__JUMP},
        },
    // Opcode 44: JC
    [44] =
        {
            {MACRO__FETCH},
            {MACRO__JUMP},
        },
    // Opcode 45: JNC
    [45] =
        {
            {MACRO__FETCH},
            {MACRO__JUMP},
        },
    // Opcode 46: SKIP
    [46] =
        {
            {MACRO__FETCH},
            {PC__PC_INC, CU__DONE},
        },
    // Opcode 50: RETI
    [50] =
        {
            {MACRO__FETCH},
            {CU__DONE},
        },
    // Opcode 51: EI
    [51] =
        {
            {MACRO__FETCH},
            {CU__DONE},
        },
    // Opcode 52: DI
    [52] =
        {
            {MACRO__FETCH},
            {CU__DONE},
        },
    // Opcode 63: HALT
    [63] =
        {
            {CU__HALT, CU__DONE},
        },
};