package in.hridaykh.hk8sim;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.hridaykh.hk8sim.models.HexFileLine;

public class Hk8Sim {

	public Hk8Sim(String[] args) throws IOException {
		String hexFilePath = args[1]; // Main class ensures atleast 2 args

		if (hexFilePath.isBlank() || !hexFilePath.toLowerCase().endsWith(".hk8hex")) {
			System.err.println("Hex file is required!");
			return;
		}

		@SuppressWarnings("unused")
		HexFileLine[] hexFile = parseHexFile(Files.readAllLines(Paths.get(hexFilePath)));
	}

	private HexFileLine[] parseHexFile(List<String> allLines) {
		List<HexFileLine> hexFileLines = new ArrayList<>();
		Set<Integer> parsedAddress = new HashSet<>();

		boolean isNextWord = false;
		for (String line : allLines) {
			if (line.isBlank())
				continue;

			String[] addressParts = line.split(":\\s+");
			int address = Integer.parseInt(addressParts[0], 16);
			if (parsedAddress.contains(address))
				throw new IllegalArgumentException("Duplicate address found in hex file: " + address);
			parsedAddress.add(address);

			String binaryString = addressParts[1].replaceAll("\\s+", "");
			if (binaryString.length() != 16)
				throw new IllegalArgumentException("Invalid binary string length in line: " + line);

			if (isNextWord) {
				isNextWord = false;
				int nextWord = Integer.parseInt(binaryString, 2);
				hexFileLines.add(new HexFileLine(address, null, null, null, nextWord));
				continue;
			}

			String opcodeStr = binaryString.substring(0, 6);
			String arg1Str = binaryString.substring(6, 10);
			String arg2Str = binaryString.substring(10, 14);
			String reservedPadding = binaryString.substring(14, 16);
			if (!reservedPadding.equals("00"))
				throw new IllegalArgumentException("Invalid reserved bits at: " + address);

			int opcode = Integer.parseInt(opcodeStr, 2);
			int arg1 = Integer.parseInt(arg1Str, 2);
			int arg2 = Integer.parseInt(arg2Str, 2);

			hexFileLines.add(new HexFileLine(address, opcode, arg1, arg2, null));
		}
		return hexFileLines.toArray(new HexFileLine[0]);
	}
}
