package in.hridaykh.hk8sim.isacompiler.model;

import java.util.List;

public class Label {
	public List<Instruction> instructions;
	public Short sizeInWords;
	public Short address;
	public String name;

	@Override
	public String toString() {
		String instructionsString = "";
		if (instructions != null)
			instructionsString = "\n\t" + instructions.stream().map(i -> i.toString())
					.reduce((a, b) -> a + "\n\t" + b).orElse("");
		return address + ": " + name + " (size: " + sizeInWords
				+ ")" + instructionsString;
	}
}
