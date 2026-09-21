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
		short[] hexFile;
		try {
			hexFile = parseHexFile(Files.readAllLines(Paths.get(hexFilePath)));
		} catch (HexFileParseException e) {
			log.error("Invalid hex line file", e);
			return;
		}

		Hk8State state = new Hk8State();
		state.initPortZero(hexFile);
		state.run();
	}

	private short[] parseHexFile(List<String> allLines) throws HexFileParseException {
		short[] addresses = new short[65536];
		Set<Integer> parsedAddress = new HashSet<>();
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

			addresses[address] = Short.parseShort(binaryString, 2);
		}
		return addresses;
	}
}
