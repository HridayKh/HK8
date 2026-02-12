package in.hridaykh.hk8sim.isacompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Defines {

	private static Map<String, String> DEFINES;

	public static List<String> processDefines(List<String> linesAfterIncludes) {
		DEFINES = new HashMap<>();
		List<String> linesWithoutDefines = registerDefines(linesAfterIncludes);
		return addDefines(linesWithoutDefines);
	}

	private static List<String> registerDefines(List<String> lines) {
		List<String> finalLines = new ArrayList<>();
		for (String line : lines) {
			if (!line.startsWith("#define")) {
				finalLines.add(line);
				continue;
			}

			String[] parts = line.split("\\s+", 3);

			if (parts.length < 3)
				throw new IllegalArgumentException("Invalid #define directive: " + line);

			DEFINES.put(parts[1], parts[2]);
		}
		return finalLines;
	}

	private static List<String> addDefines(List<String> lines) {
		List<String> finalLines = new ArrayList<>();
		for (String line : lines) {
			if (!line.contains("$")) {
				finalLines.add(line);
				continue;
			}
			finalLines.add(handleLineParts(line));
		}
		return finalLines;
	}

	private static String handleLineParts(String line) {
		String newLine = "";
		String[] parts = line.split("\\$");
		boolean firstPartDone = false;
		for (String part : parts) {
			if (!firstPartDone) {
				firstPartDone = true;
				newLine += part;
				continue;
			}
			if (part.isBlank())
				throw new IllegalArgumentException("#define var name blank");

			String varNameArr[] = part.split("\\W", 2);
			String varName = varNameArr[0];
			String varValue = DEFINES.get(varName);
			if (varValue == null)
				throw new IllegalArgumentException("unknown var: " + varName);

			newLine += varValue + (varNameArr.length > 1 ? varNameArr[1] : "") + " ";
		}
		return newLine;
	}
}