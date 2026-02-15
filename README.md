# HK8 Architecture Specification (v2.0)

## 1. System Specifications

- Word Size: 16-bit Data / 16-bit Address.
- General Purpose Registers: 16 registers (R0 through R15).
  - R0​–R14​: General Purpose.
  - R15: General purpose but also default destination for ALU operations and 7-Segment output (configurable).
- Address Space: Managed via the `MAR` (`Memory Address Register`) and `PSR` (`Port Select Register`).

## 2. Instruction Format (16-Bit Word)

- Instructions follow a fixed-width format, followed by an optional 16-bit "Next Word" for constants.
- Some instrucitons have the next word after them to same a immedeate value for a memory address or data.

```txt
000000 0000 0000 00
  /\    /\   /\  /\
  ||    ||   ||  ||
opcode  ||   ||  ||
       Arg1  ||  ||
            Arg2 ||
              Reserved
```

> Arg1 and Arg2 are 4 bits each represent either: Port, Execution Port or Register

## 3. Execution & Control Logic

- Micro-Instructions: The Control Unit uses microcode to manage state. Every instruction sequence must end with a "End of Instruction" micro-op to trigger the next PC fetch.
- Wait States (Clock Stretching): The system supports an asynchronous "Ready" signal. If a device (like slow RAM) is not ready, it can pull a "Pause" line to stall the global clock until the data is valid on the bus.
- MAR (Memory Address Register): A dedicated register that interfaces with the address bus. It can be loaded from or stored to the Register File but exists outside of it.

## 4. Port & Memory Mapping

- Ports determine which bank/device or memory is being used.

### Types of ports

1. Execution Port: Port used by the `PC` for instruction execution.
2. Data Port: Port used for data access (load/store).

## 5. Interrupt Handling

> The interrupt system is designed for hardware-level context switching.

1. Trigger: An external signal on a port initiates the interrupt.
2. Cycle-Accuracy: The CPU finishes the current instruction before pausing.
3. Context Save: The current PC and PSR are automatically saved into dedicated internal "Return Registers."
4. Vectoring: The PSR switches to the interrupting port’s ID, and the PC is reset to `0x0000`.
5. Return: The `RETI` instruction restores the saved PC and PSR, resuming the main program.

> Note: Interrupts only trigger if the "Interrupt Enable" bit in the flag register is set.

## 6. ALU & Output Logic

- Operations: Primarily focused on `ADD` and `SUB`.
- Flexible Output: While  is the default, the destination register for ALU results and the 7-Segment display can be dynamically remapped via the `ALU` and `SEG` instructions.
- Isolation: The ALU has a "Temporary Out" buffer to prevent race conditions during register write-back.

## 7. Core Component Overview

### Control & Timing

- Clock: The heartbeat of the system; supports "Clock Stretching" (pausing) for slow memory/port response.
- Control Unit: The brain of the CPU; decodes the Instruction Register (IR) into specific Control Signals and micro-instructions.
- Program Counter (PC): 16-bit register holding the address of the next instruction to fetch.
- Instruction Register (IR): 16-bit register that holds the current opcode and arguments ().

### Addressing & Data Flow

- Bus 1 & Bus 2: A dual 16-bit internal bus system allowing for two operands to be sent to the ALU or memory simultaneously.
- Memory Address Register (MAR): A 16-bit dedicated register for pointing to memory locations; decoupled from the Register File.
- Port Select Register (PSR): A 4-bit register that determines which of the 16 ports is active; essentially acts as a "Device MAR."
- Port Execution Register (PER): A 4-bit register that determines which of the 16 ports is used for instruction execution.

### Storage & Computation

- Register File: A 16×16-bit storage block. Includes a Multiplexer Register Selector to route specific registers to the buses based on IR arguments.
- ALU: Performs 16-bit Addition and Subtraction.
- ALU Output ID Register: Stores which register index (0–15) the ALU should write back to.
- ALU Temp Out: A buffer to hold the result before it is committed to the Register File.
- Flag Register (4-Bit): Stores status bits: Zero (Z), Carry (C), and the Interrupt Enable (IE) bit.

### Output & External Interface

- Ports: Interface for Program Storage (Default Port 0) and RAM.
- 7-Segment Display: Visual output component.
- Display Output ID Register: Stores which register index's value is currently being piped to the display.
