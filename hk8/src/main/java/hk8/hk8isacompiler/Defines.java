package hk8.hk8isacompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Matcher matcher = Pattern.compile("\\$([A-Za-z_][A-Za-z0-9_]*)").matcher(line);
		StringBuffer newLine = new StringBuffer();
		while (matcher.find()) {
			String varName = matcher.group(1);
			String varValue = DEFINES.get(varName);
			if (varValue == null)
				throw new IllegalArgumentException("unknown var: " + varName);
			matcher.appendReplacement(newLine, Matcher.quoteReplacement(varValue));
		}
		matcher.appendTail(newLine);
		return newLine.toString();
	}
}