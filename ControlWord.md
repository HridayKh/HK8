# Control word for all components (36 total)

- The components and orignal list was designed on paper and is used as a reference while writing this.
- General Pattern for control word: `[SUB-COMPONENT]_[ACTION]_[BUS]`
- Indivisual control signals are reffered to as `COMPONENT`.`WORD`
- Temprary "used" marker `[ ]` is used to find any micro instrucion that remains unused and removed after instrucion steps are determined.

## 1. Memory / Ports (MEM)

 1. [x] `PSR_OUT_B1`
 2. [x] `PSR_IN_B1`
 3. [x] `PSR_IN_A1`
 4. [x] `PER_OUT_B1`
 5. [x] `PER_IN_B1`
 6. [x] `PER_IN_A1`
 7. [x] `MEM_ADDR_B1`
 8. [x] `MEM_ADDR_B2`
 9. [x] `MEM_OUT_B1`
10. [x] `MEM_OUT_B2`
11. [x] `MEM_IN_B1`

## 2. Register File (RF)

1. [x] `R1_OUT_B1` - Select and enable first output register from agr1 for bus 1
2. [x] `R1_IN_B1` - Input to register selected by arg 1 of `IR` on bus 1
3. [x] `R1_IN_B2` - Input to register selected by arg 1 of `IR` on bus 2
4. [x] `R2_OUT_B2` - Select and enable second output register from arg2 for bus 2
5. [x] `R2_IN_B1` - Input to register selected by arg 2 of `IR` on bus 1
6. [x] `R2_IN_B2` - Input to register selected by arg 2 of `IR` on bus 2
7. [x] `RALU_IN_B1` - Input to register selected by Alu Output Register Id Register on bus 1

## 3. Program Counter (PC)

1. [x] `PC_OUT_B2`
2. [ ] `PC_IN_B1`
3. [x] `PC_INC`

## 4. Arithmetic Logic Unit (ALU)

 1. [x] `ALU_ADD` - Add
 2. [x] `ALU_SUB` - Subtract
 3. [x] `ALU_INC` - Increment
 4. [x] `ALU_DEC` - Decrement
 5. [x] `ALU_NOT` - Bitwise NOT
 6. [x] `ALU_AND` - Bitwise AND
 7. [x] `ALU_OR`  - Bitwise OR
 8. [x] `ALU_XOR` - Bitwise XOR
 9. [x] `ALU_SHL` - Shift left
10. [x] `ALU_SHR` - Shift right
11. [x] `RID_IN_A1`  - Select output register id for alu resutls.
12. [x] `RES_OUT_B1` - Output result from temprary result register to bus 1.
13. [x] `SET_FLAGS`

## 5. Instruciton Register (IR)

1. [x] `IR_IN_B1`

## 6. Control Unit (CU)

1. [x] `INS_DONE` - Mark instruction as done. Starts next instruciton the next clock cycle.
2. [x] `HALT`
