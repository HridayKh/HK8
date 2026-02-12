# upgrade notes

> Note: it does wait for something to finish, directly next clock cycle

- 16 bit data
- 16 bit adress
- alu only does add/sub and takes input register adress
- Micro instruciton to indicate ins end
- segment display output register and alu output register can be set, default is r15 for both
- memory adress register can be loaded from regFile or loaded to regFile but is not part of the regFile

## Interrupts Idea

- Cpu can be interrupted on a port
- when interupted, clock is paused when current instruciton finishes
- the port switches the psr to that port and resets the pc to 0
- it also storeas the previous psr and pc in a return register
- on a return from interrupt signal, goes back to the previous locaiton
- there is a interrupt enable bit, gotta wait for it to interrupt

## waiting

- components like memory, external devices on the ports could pause the clock while they are doing stuff
- example of memory:
  - microcode to get memory
  - memory devices reciveves the signal, pauses trhe clock, while getting the data and unpasues when trhe data is ready on the bus

## High Level Components For An Overview Design

- clock
- program counter
- psr
- mar
- bus1
- bus2
- reg file + multiplexer register selector
- ports: program storage, ram
- alu + alu output register id register + alu temprary out
- 7 segment display + output register id register
- flag register
- control unit + control signals
- instrucion register

## Microcodes / MIcro-Instrucitons

- TODO

## Ports

- 4 Bits port address
- 16 Ports
- Port 0 adress 0 is start point
- Port 0 is the program storage by default and determines memory port
- instruciton to select port
- instrcution to manage port
- port select register (psr) treater like a smaller mar

## Registers

- 4 Bit 16 register file
- Load and store immideate vlaues or port
- alu takes registers as input
- Register usgae:
  - r0-r14: General Purpose
  - r15: Defualt Segment and ALU Output
- the reg file is controlled by the same thing and not decoupled but the micro instrucitn determines if the reg file is ocnnectedto the ir or the alu output register id register

## Instruciton Register (IR) (16-Bits)

- 6b: instruciotn adress
- 4b: A: register 1 / port 1
- 4b: B: register 2 / port 2
- 2b: reserved
- next word: for immideate values and addresses
