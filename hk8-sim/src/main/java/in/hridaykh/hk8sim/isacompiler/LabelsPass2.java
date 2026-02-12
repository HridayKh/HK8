package in.hridaykh.hk8sim.isacompiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.hridaykh.hk8sim.isacompiler.model.Instruction;
import in.hridaykh.hk8sim.isacompiler.model.Label;

public class LabelsPass2 {

	public static String[] processPass2(List<Label> labels) {
		for (Label label : labels)
			calculateLabelSize(label);
		determineLabelZero(labels);
		String[] result = new String[giveLabelAddresses(labels) - 1];
		putLabelAddressesInInstructions(labels);
		fillResult(result, labels);
		return result;
	}

	private static void calculateLabelSize(Label label) {
		if (label.instructions == null || label.instructions.isEmpty()) {
			label.sizeInWords = 0;
			return;
		}
		int size = 0;
		for (Instruction instruction : label.instructions)
			if (instruction.nextWord != null || (instruction.labelNameForPass2 != null
					&& !instruction.labelNameForPass2.isBlank()))
				size += 2;
			else
				size += 1;
		label.sizeInWords = (short) size;
	}

	private static void determineLabelZero(List<Label> labels) {
		if (labels.isEmpty())
			throw new IllegalArgumentException(
					"At least one label is required to determine the address of the first label.");
		if (labels.get(0).address != null && labels.get(0).address != 0)
			throw new IllegalArgumentException("The first label " + labels.get(0).name
					+ " has address " + labels.get(0).address
					+ ", but it must be 0 as the first label's address is reserved to be 0.");
		labels.get(0).address = 0;
	}

	private static int giveLabelAddresses(List<Label> labels) {
		int lastLabelAddress = labels.get(0).address;
		int lastLabelSize = labels.get(0).sizeInWords;
		boolean first = true;
		for (Label label : labels) {
			if (first) {
				first = false;
				continue;
			}

			if (label.address != null && label.address == 0)
				throw new IllegalArgumentException("Label " + label.name
						+ " has address 0, which is reserved for the first label.");

			int minimumAdress = lastLabelAddress + lastLabelSize;
			if (minimumAdress > 0xFFFF)
				throw new IllegalArgumentException(
						"The total size of the labels exceeds the maximum addressable memory of 0xFFFF.");
			if (label.address != null && label.address >= minimumAdress) {
				lastLabelAddress = label.address;
				lastLabelSize = label.sizeInWords;
			} else {
				if (label.address != null && label.address < minimumAdress)
					throw new IllegalArgumentException("Label " + label.name + " has address "
							+ label.address
							+ ", which is less than the minimum required address of "
							+ minimumAdress
							+ " based on the previous label's address and size.");
				label.address = (short) minimumAdress;
				lastLabelAddress = label.address;
				lastLabelSize = label.sizeInWords;
			}
		}
		return lastLabelAddress + lastLabelSize;
	}

	private static void putLabelAddressesInInstructions(List<Label> labels) {
		Map<String, Short> labelAddressMap = new HashMap<>();
		for (Label label : labels)
			labelAddressMap.put(label.name.toLowerCase(), label.address);
		for (Label label : labels) {
			if (label.instructions == null || label.instructions.isEmpty())
				continue;
			for (Instruction ins : label.instructions) {
				if (ins.nextWord != null
						|| (ins.labelNameForPass2 == null || ins.labelNameForPass2.isBlank()))
					continue;
				ins.nextWord = labelAddressMap.get(ins.labelNameForPass2.toLowerCase());
				if (ins.nextWord == null)
					throw new IllegalArgumentException(
							"Label " + ins.labelNameForPass2 + " not found!");
			}
		}
	}

	private static void fillResult(String[] result, List<Label> labels) {
		int idx = 0;
		for (Label label : labels) {
			for (Instruction ins : label.instructions) {
				// Use 0 if the argument is null
				long opVal = (ins.opcode != null) ? ins.opcode : 0;
				long a1Val = (ins.arg1 != null) ? ins.arg1 : 0;
				long a2Val = (ins.arg2 != null) ? ins.arg2 : 0;

				String opcode = toBinary(opVal, 6, 0b00111111);
				String arg1 = toBinary(a1Val, 4, 0x0F);
				String arg2 = toBinary(a2Val, 4, 0x0F);

				result[idx++] = opcode + arg1 + arg2 + "00";

				if (ins.nextWord != null) {
					result[idx++] = toBinary(ins.nextWord, 16, 0xFFFF);
				}
			}
		}
	}

	private static String toBinary(long value, int bitCount, int mask) {
		long maskedValue = value & mask;
		String binary = Long.toBinaryString(maskedValue);
		return String.format("%" + bitCount + "s", binary).replace(' ', '0');
	}

}
