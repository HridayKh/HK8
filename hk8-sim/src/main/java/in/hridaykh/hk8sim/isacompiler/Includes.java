package in.hridaykh.hk8sim.isacompiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Includes {

	public static List<String> processIncludes(List<String> allLines) throws IOException {
		List<String> processedLines = new ArrayList<>();

		for (String line : allLines) {
			String cleanLine = line.split(";")[0].trim();
			if (cleanLine.isBlank())
				continue;

			// dont set line to lower case as file paths are case sensitive
			if (!cleanLine.toLowerCase().startsWith("#include")) {
				processedLines.add(cleanLine.toLowerCase());
				continue;
			}

			String fileToInclude = cleanLine.substring(8).trim();
			System.out.println("Including file: " + fileToInclude);

			Path path = Paths.get(fileToInclude);
			if (!Files.exists(path))
				throw new IOException("Included file not found: " + fileToInclude);

			List<String> includedLines = Files.readAllLines(path);
			processedLines.addAll(processIncludes(includedLines));
		}
		return processedLines;
	}

}
