package hk8.sim.state;

import hk8.HexFileLine;
import hk8.sim.Address;

public class Hk8State {
	public Hk8State() {
	}

	public void initPortZeroFromHexFile(HexFileLine[] hexFile) {
		Address[] addresses = new Address[Math.powExact(2, 16)];
		for (HexFileLine line : hexFile)
			addresses[line.address()] = new Address(line.opcode(), line.arg1(), line.arg2(), line.nextWord());
		for (int i = 0; i < addresses.length; i++)
			if (addresses[i] == null)
				addresses[i] = new Address(0, 0, 0, 0);
		Ports.initPortDataFromAddresses(0, addresses);
	}

	public void run() {

	}
}
