package in.hridaykh.hk8sim.isacompiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsaCompiler {

	public IsaCompiler(String[] args) throws IOException {
		String inputFile = args.length > 0 ? args[0] : null;
		String outputFile = args.length > 1 ? args[1] : null;

		if (inputFile == null || inputFile.isBlank()) {
			System.err.println("Input file is required!");
			return;
		}
		if (outputFile == null || outputFile.isBlank()) {
			outputFile = inputFile + ".hk8hex";
			System.err.println("No output file specified. Using default: " + outputFile);
		}

		List<String> allLines = Files.readAllLines(Paths.get(inputFile));
		List<String> linesAfterIncludes = Includes.processIncludes(allLines);
		List<String> linesAfterDefines = Defines.processDefines(linesAfterIncludes);
		List<String> linesAfterLabels = Labels.processLabels(linesAfterDefines);
		for (String line : linesAfterLabels)
			System.out.println(line);
	}

}
