package in.hridaykh.hk8isacompiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.hridaykh.hk8isacompiler.model.Instruction;
import in.hridaykh.hk8isacompiler.model.Label;

public class Hk8IsaCompiler {

	public final static Map<String, Instruction> INSTRUCTION_MAP = new HashMap<>();

	public Hk8IsaCompiler(String[] args) throws IOException {
		String inputFile = args[1]; // Main class ensures atleast 2 args
		String outputFile = args.length > 1 ? args[2] : null;

		if (inputFile.isBlank()) {
			System.err.println("Input file is required!");
			return;
		}
		if (outputFile == null || outputFile.isBlank()) {
			outputFile = inputFile + ".hk8hex";
			System.err.println("No output file specified. Using default: " + outputFile);
		}

		createInstructionMap();

		List<String> allLines = Files.readAllLines(Paths.get(inputFile));
		List<String> linesAfterIncludes = Includes.processIncludes(allLines);
		List<String> linesAfterDefines = Defines.processDefines(linesAfterIncludes);
		List<Label> labelsAfterPass1 = LabelsPass1.processLabelsPass1(linesAfterDefines);
		String[] afterPass2 = LabelsPass2.processPass2(labelsAfterPass1);

		String outData = "";
		for (int i = 0; i < afterPass2.length; i++)
			outData += String.format("%04X: %s\n", i, afterPass2[i]);
		Files.writeString(Paths.get(outputFile), outData);
	}

	private void createInstructionMap() {
		String mapFileName = "isaMap.csv";

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(mapFileName);

		if (inputStream == null)
			throw new IllegalArgumentException("Resource file not found: " + mapFileName);

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			reader.lines().forEach(line -> {
				parseInstructionMapLine(line);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseInstructionMapLine(String line) {
		String[] parts = line.split(",");
		if (parts.length != 4) {
			System.err.println("Invalid line in map file: " + line);
			System.exit(1);
		}
		Instruction i = new Instruction();
		i.opcode = Byte.parseByte(parts[0]);
		switch (Integer.parseInt(parts[2])) {
			case 0:
				i.arg1 = null;
				i.arg2 = null;
				break;
			case 1:
				i.arg1 = 1;
				i.arg2 = null;
				break;
			case 2:
				i.arg1 = 1;
				i.arg2 = 1;
				break;
			default:
				throw new IllegalArgumentException(
						"Invalid number of arguments for instruction: " + line);
		}

		i.nextWord = parts[3].equalsIgnoreCase("yes") ? (short) 1 : null;

		INSTRUCTION_MAP.put(parts[1].toLowerCase(), i);
	}

}
