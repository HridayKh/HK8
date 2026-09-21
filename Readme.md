# The HK8 - A 16-Bit CPU Inspired by the 8-Bit Ben Eater CPU

## 0. Custom Terminology and Units

- `b`: Bit
- `B`: Byte
- `w`: 2B (16b)
- `rN`: refers to register number N, where 0 <= N <= 31

## 1. Specs

- Big endian
- 16b data width
- 16b adress width
- 32 Registers
- Each instruction is upto 4 steps
- each step is one clock cycle
- each step triggers multiple or single signals, part of the control word, called micro-instructions
- a 7-segment display is throw on r31

## 2. Instructions

> All instructions and micro-instructions are in their respective documents

```txt
000000 00000 00000
  /\     /\    /\
  ||     ||    ||
opcode   ||   Arg2
        Arg1
```

- Arg1 and Arg2 are usually register id in the register file
- 1w other than `IMM`, which is 2w

## 3. Memory mappings and structure

### 3.1. Specified addresses

- `0x0000`: Execution start
- `0x0100`: Interupt
- `0x0200`: Syscall
- `0x0300`: Scheduler
- `0xFE00`-`0xFFFF`: Memory maped I/O (convention)

### 3.2. Pages and MMU

- Each page is the lease significant 8b
- Each page is 256 bytes long
- The mmu design is currently being cooked

## 4. Interupts

- Being cooked rn

## 5. ALU

- supports add, subtract, inc, dec, shift left, shift right, and, or, xor, not
- the alu stores all results in a temprary buffer
- the register where the output of the temprary buffer goes can be changed

## 6. Flags

- `C`: Carry
- `Z`: Zero
- `M`: Mode (user/kernel mode)
- `I`: Interupts-disabled
