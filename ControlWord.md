# Control word for all components (31 total)

- The components and orignal list was designed on paper and is used as a reference while writing this.
- General Pattern for control word: `[SUB-COMPONENT]_[ACTION]_[BUS]`
- Indivisual control signals are reffered to as `COMPONENT`.`WORD`
- Temprary "used" marker `[ ]` is used to find any micro instrucion that remains unused and removed after instrucion steps are determined.

## 1. Memory / Ports (MEM)

0. [x] `MEM_ADDR_B1`
1. [x] `MEM_ADDR_B2`
2. [x] `MEM_OUT_B1`
3. [x] `MEM_OUT_B2`
4. [x] `MEM_IN_B1`

## 2. Register File (RF)

 5. [x] `R1_OUT_B1` - Select and enable first output register from agr1 for bus 1
 6. [x] `R1_IN_B1` - Input to register selected by arg 1 of `IR` on bus 1
 7. [x] `R1_IN_B2` - Input to register selected by arg 1 of `IR` on bus 2
 8. [x] `R2_OUT_B2` - Select and enable second output register from arg2 for bus 2
 9. [x] `R2_IN_B1` - Input to register selected by arg 2 of `IR` on bus 1
10. [x] `R2_IN_B2` - Input to register selected by arg 2 of `IR` on bus 2
11. [x] `RALU_IN_B1` - Input to register selected by Alu Output Register Id Register on bus 1

## 3. Program Counter (PC)

12. [x] `PC_OUT_B2`
13. [x] `PC_IN_B1`
14. [x] `PC_INC`

## 4. Arithmetic Logic Unit (ALU)

> micro-instricton 1-10 also set flags

15. [x] `ALU_ADD` - Add
16. [x] `ALU_SUB` - Subtract
17. [x] `ALU_INC` - Increment
18. [x] `ALU_DEC` - Decrement
19. [x] `ALU_NOT` - Bitwise NOT
20. [x] `ALU_AND` - Bitwise AND
21. [x] `ALU_OR`  - Bitwise OR
22. [x] `ALU_XOR` - Bitwise XOR
23. [x] `ALU_SHL` - Shift left
24. [x] `ALU_SHR` - Shift right
25. [x] `RID_IN_A1`  - Select output register id for alu resutls.
26. [x] `RES_OUT_B1` - Output result from temprary result register to bus 1.

## 5. Instruciton Register (IR)

27. [x] `IR_IN_B1`

## 6. Control Unit (CU)

28. [x] `DONE` - Mark instruction as done. Starts next instruciton the next clock cycle.
29. [x] `HALT`
