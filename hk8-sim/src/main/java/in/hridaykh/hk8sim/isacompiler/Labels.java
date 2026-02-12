package in.hridaykh.hk8sim.isacompiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.hridaykh.hk8sim.isacompiler.model.Instruction;
import in.hridaykh.hk8sim.isacompiler.model.Label;

public class Labels {
	private static List<Label> labels;

	public static List<String> processLabels(List<String> lines) {
		labels = new ArrayList<>();
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
		return null;

	}

	private static Label isLabel(String line) {
		String regex = "^(?<address>\\d+)?\\s+(?<name>\\w+):";
		Matcher matcher = Pattern.compile(regex).matcher(line);
		if (!matcher.find())
			return null;

		String address = matcher.group("address");
		String name = matcher.group("name");

		Label label = new Label();
		label.name = name;

		if (address != null)
			label.address = Short.parseShort(address);

		System.out.println("Extracted Number: " + (address != null ? address : "None"));
		System.out.println("Extracted Name: " + name);

		return label;
	}

	private static void handleInstructionPass1(Label label, String line) {
		if (label.instructions == null)
			label.instructions = new ArrayList<>();
		Instruction instruction = new Instruction();

		String[] untrimmedParts = line.trim().split("\\W", 2);
		String[] parts = trimArray(untrimmedParts);

		Instruction insType = IsaCompiler.INSTRUCTION_MAP.get(parts[0].trim().toLowerCase());

		if (insType == null)
			throw new IllegalArgumentException("Invalid instruction: " + parts[0] + " in line: " + line);

		boolean[] needs = determineNeeds(insType);
		boolean isArg1Needed = needs[0];
		boolean isArg2Needed = needs[1];
		boolean isNextWordNeeded = needs[2];

		boolean firstPartDone = false;
		for (String part : parts) {
			if (!firstPartDone) {
				firstPartDone = true;
				continue;
			}
			if (isArg1Needed && instruction.arg1 == null) {
				instruction.arg1 = Byte.parseByte(part.trim());
			} else if (isArg2Needed && instruction.arg2 == null) {
				instruction.arg2 = part.trim();
			} else if (isNextWordNeeded && instruction.nextWord == null) {
				instruction.nextWord = part.trim();
			} else {
				throw new IllegalArgumentException(
						"Too many arguments for instruction: " + insType.name);
			}
		}

	}

	private static int parseNumber(String s) {
		switch (s.charAt(0)) {
			case value:

				break;

			default:
				break;
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
