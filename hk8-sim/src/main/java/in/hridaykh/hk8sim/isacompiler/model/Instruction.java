package in.hridaykh.hk8sim.isacompiler.model;

public class Instruction {
	public Byte opcode;
	public Byte arg1;
	public Byte arg2;
	public Short nextWord;
	public String labelNameForPass2;

	@Override
	public String toString() {
		return opcode + ": " + (arg1 == null ? "n" : (int) arg1) + " " + (arg2 == null ? "n" : (int) arg2)
				+ "; " + (nextWord == null ? "n" : (int) nextWord) + (labelNameForPass2 == null ? "" : " (:" + labelNameForPass2 + ")");
	}
}
