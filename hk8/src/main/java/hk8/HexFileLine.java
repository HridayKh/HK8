package hk8.hk8isacompiler.model;

public record HexFileLine(int address, Integer opcode, Integer arg1, Integer arg2, Integer nextWord) {
}
