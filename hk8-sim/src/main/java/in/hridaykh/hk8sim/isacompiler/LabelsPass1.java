package in.hridaykh.hk8sim.isacompiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.hridaykh.hk8sim.isacompiler.model.Instruction;
import in.hridaykh.hk8sim.isacompiler.model.Label;

public class LabelsPass1 {

	public static List<Label> processLabelsPass1(List<String> lines) {
		List<Label> labels = new ArrayList<>();
		Label currentLabel = null;
		for (String line : lines) {
			Label label = isLabel(line);
			if (label == null && currentLabel == null) // instruciotn outside label
				throw new IllegalArgumentException("Instructions started outside label not allowed!");
			else if (label != null && currentLabel == null) // first label started
				currentLabel = label;
			else if (label == null && currentLabel != null) // instruction inside label
				handleInstructionPass1(currentLabel, line);
			else { // next label started
				labels.add(currentLabel);
				currentLabel = label;
			}
		}
		labels.add(currentLabel);
		return labels;
	}

	private static Label isLabel(String line) {
		String regex = "^\\s*(\\d+)?\\s*(\\w+)\\s*:(?!\\S)";
		Matcher matcher = Pattern.compile(regex).matcher(line);
		if (!matcher.find())
			return null;

		String address = matcher.group(1);
		String name = matcher.group(2);

		Label label = new Label();
		label.name = name;

		if (address != null && !address.isBlank())
			label.address = (short) Integer.parseInt(address);
		else
			label.address = null;

		System.out.println("Found label: " + label.name + ", address: " + label.address);

		return label;
	}

	private static void handleInstructionPass1(Label label, String line) {
		if (label.instructions == null)
			label.instructions = new ArrayList<>();
		Instruction instruction = new Instruction();

		String[] untrimmedParts = line.trim().split("[^\\w.:]");
		String[] parts = trimArray(untrimmedParts);

		Instruction insType = IsaCompiler.INSTRUCTION_MAP.get(parts[0].trim().toLowerCase());

		if (insType == null)
			throw new IllegalArgumentException("Invalid instruction: " + parts[0] + " in line: " + line);

		boolean[] needs = determineNeeds(insType);
		boolean isArg1Needed = needs[0];
		boolean isArg2Needed = needs[1];
		boolean isNextWordNeeded = needs[2];

		if (parts.length == 1 && (isArg1Needed || isArg2Needed || isNextWordNeeded))
			throw new IllegalArgumentException(
					"Instruction " + insType.opcode + " needs arguments in line: " + line);

		// System.out.println("\n\n\n\nlabels pass 1: processing line: " + line);
		if (parts.length < 2) {
			instruction.opcode = insType.opcode;
			label.instructions.add(instruction);
			return;
		}

		boolean firstPart = true;
		// System.out.println("arg1: " + isArg1Needed + ", arg2: " + isArg2Needed + ",
		// nextWord: "
		// + isNextWordNeeded);

		// int idx = 0;
		for (String part : parts) {
			// System.out.println(idx + ": |" + part + "|");
			// idx++;
			if (firstPart) {
				instruction.opcode = insType.opcode;
				firstPart = false;
				continue;
			}
			if (isArg1Needed && instruction.arg1 == null) {
				instruction.arg1 = parseNumber(part);
			} else if (isArg2Needed && instruction.arg2 == null) {
				instruction.arg2 = parseNumber(part);
			} else if (isNextWordNeeded && instruction.nextWord == null) {
				instruction.nextWord = parseNextWordPass1(instruction, part);
			} else {
				throw new IllegalArgumentException(
						"Too many arguments for instruction: " + insType.opcode);
			}
			// System.out.println(">>> " + instruction.toString());
		}

		label.instructions.add(instruction);

	}

	private static Short parseNextWordPass1(Instruction instruction, String part) {
		part = part.strip();

		// handle labels for pass 1
		if (part.startsWith(":")) {
			instruction.labelNameForPass2 = part.substring(1).toLowerCase();
			return null;
		}

		try {
			int value;
			String lowerPart = part.toLowerCase();

			// hadnle hex
			if (lowerPart.startsWith("0x")) {
				value = Integer.parseInt(part.substring(2), 16);
			}
			// handle binary
			else if (part.startsWith(".")) {
				value = Integer.parseInt(part.substring(1), 2);
			}
			// handle decimal
			else {
				value = Integer.parseInt(part);
			}

			// Validate range for a 16-bit word (0 to 65535)
			if (value < 0 || value > 65535) {
				throw new IllegalArgumentException("Value out of 16-bit range: " + part);
			}

			// Note: 65535 becomes -1 in Java, but the binary is still 0xFFFF.
			return (short) value;

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid numeric format: " + part);
		}
	}

	private static Byte parseNumber(String s) {
		s = s.strip();
		String lower = s.toLowerCase();
		int value;

		try {
			if (lower.startsWith("r") || lower.startsWith("p")) {
				value = Integer.parseInt(s.substring(1));
			} else if (lower.startsWith("0x")) {
				value = Integer.parseInt(s.substring(2), 16);
			} else if (s.startsWith(".")) {
				value = Integer.parseInt(s.substring(1), 2);
			} else {
				value = Integer.parseInt(s);
			}

			// the args are 4 bits and stored in a byte
			if (value < 0 || value > 15) {
				throw new IllegalArgumentException("Argument out of range: " + s);
			}

			return (byte) value;

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid numeric format for argument: " + s);
		}
	}

	public static String[] trimArray(String[] input) {
		int start = 0;
		int end = input.length - 1;

		while (start <= end && input[start].isBlank()) {
			start++;
		}

		while (end >= start && input[end].isBlank()) {
			end--;
		}

		if (start > end) {
			return new String[0];
		}

		return Arrays.copyOfRange(input, start, end + 1);
	}

	private static boolean[] determineNeeds(Instruction i) {
		boolean isArg1Needed = false;
		boolean isArg2Needed = false;
		boolean isNextWordNeeded = false;

		if (i.arg1 != null)
			isArg1Needed = true;
		if (i.arg2 != null)
			isArg2Needed = true;
		if (i.nextWord != null)
			isNextWordNeeded = true;

		return new boolean[] { isArg1Needed, isArg2Needed, isNextWordNeeded };
	}

}
