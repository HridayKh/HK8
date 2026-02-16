# Control word for all components

- The components and orignal list was designed on paper and is used as a reference while writing this.
- General Pattern for control word: `[SUB-COMPONENT]_[ACTION]_[BUS]`
- Indivisual control signals are reffered to as `COMPONENT`.`WORD`
- Temprary "used" marker `[ ]` is used to find any micro instrucion that remains unused and removed after instrucion steps are determined.

## 1. Memory / Ports (MEM)

 1. [ ] `PSR_OUT_B1`
 2. [ ] `PSR_IN_B1`
 3. [ ] `PER_OUT_B1`
 4. [ ] `PER_IN_B1`
 5. [x] `MAR_IN_B1`
 6. [x] `MEM_ADDR_B1`
 7. [x] `MEM_OUT_B1`
 8. [x] `MEM_OUT_B2`
 9. [ ] `MEM_IN_B1`
10. [ ] `MEM_IN_B2`

## 2. Register File (RF)

1. [x] `R1_OUT_B1` - Select and enable first output register from agr1 for bus 1
2. [x] `R1_IN_B1` - Input to register selected by arg 1 of `IR` on bus 1
3. [x] `R1_IN_B2` - Input to register selected by arg 1 of `IR` on bus 2
4. [ ] `R2_OUT_B2` - Select and enable second output register from arg2 for bus 2
5. [ ] `RALU_IN_B1` - Input to register selected by Alu Output Register Id Register on bus 1

## 3. Program Counter (PC)

1. [x] `PC_OUT_B1`
2. [ ] `PC_IN_B1`
3. [x] `PC_INC`

## 4. Arithmetic Logic Unit (ALU)

 1. [ ] `ALU_ADD` - Add
 2. [ ] `ALU_SUB` - Subtract
 3. [ ] `ALU_SHL` - Shift left
 4. [ ] `ALU_SHR` - Shift right
 5. [ ] `ALU_AND` - Bitwise AND
 6. [ ] `ALU_OR`  - Bitwise OR
 7. [ ] `ALU_XOR` - Bitwise XOR
 8. [ ] `ALU_NOT` - Bitwise NOT
 9. [ ] `ALU_INC` - Increment
10. [ ] `ALU_DEC` - Decrement
11. [ ] `RID_IN_B1`  - Select output register id for alu resutls.
12. [ ] `RES_OUT_B1` - Output result from temprary result register to bus 1.
13. [ ] `SET_FLAGS`

## 5. Instruciton Register (IR)

1. [x] `IR_IN_B2`

## 6. Control Unit (CU)

1. [x] `INS_DONE` - Mark instruction as done. Starts next instruciton the next clock cycle.
