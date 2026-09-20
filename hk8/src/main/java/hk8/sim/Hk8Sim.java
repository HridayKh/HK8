package hk8.sim;

import hk8.HexFileLine;
import hk8.sim.exceptions.HexFileParseException;
import hk8.sim.state.Hk8State;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Hk8Sim {

	private static final int IMM_INS_OP_CODE = 9;

	public Hk8Sim(String[] args) throws IOException {
		String hexFilePath = args[1]; // Main class ensures at least 2 args

		if (hexFilePath.isBlank() || !hexFilePath.toLowerCase().endsWith(".hk8hex")) {
			System.err.println("Hex file is required!");
			return;
		}
		HexFileLine[] hexFile;
		try {
			hexFile = parseHexFile(Files.readAllLines(Paths.get(hexFilePath)));
		} catch (HexFileParseException e) {
			log.error("Invalid hex line file", e);
			return;
		}

		Hk8State state = new Hk8State();
		state.initPortZeroFromHexFile(hexFile);
		state.run();
	}

	private HexFileLine[] parseHexFile(List<String> allLines) throws HexFileParseException {
		List<HexFileLine> hexFileLines = new ArrayList<>(allLines.size());
		Set<Integer> parsedAddress = new HashSet<>();

		boolean isNextWord = false;
		for (String line : allLines) {
			if (line.isBlank())
				continue;

			String[] addressParts = line.split(":\\s+");
			int address = Integer.parseInt(addressParts[0], 16);
			parsedAddress.add(address);

			if (parsedAddress.contains(address))
				throw new HexFileParseException("Duplicate address found in hex file: " + address);

			String binaryString = addressParts[1].replaceAll("\\s+", "");
			if (binaryString.length() != 16)
				throw new HexFileParseException("Invalid binary string length in line: " + line);

			// TODO: check if this ins needs next word
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
				throw new HexFileParseException("Invalid reserved bits at: " + address);

			int opcode = Integer.parseInt(opcodeStr, 2);
			int arg1 = Integer.parseInt(arg1Str, 2);
			int arg2 = Integer.parseInt(arg2Str, 2);


			isNextWord = opcode == IMM_INS_OP_CODE;

			hexFileLines.add(new HexFileLine(address, opcode, arg1, arg2, null));
		}
		return hexFileLines.toArray(new HexFileLine[0]);
	}
}
