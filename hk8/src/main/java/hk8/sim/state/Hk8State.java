package hk8.sim.state;

import hk8.HexFileLine;

public class Hk8State {
	public Hk8State() {
	}

	public void initPortZero(short[] data) {
		Ports.initPortDataFromAddresses(0, data);
	}

	public void run() {

	}
}
