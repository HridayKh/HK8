package hk8.sim.state;

import hk8.HexFileLine;
import hk8.sim.Address;

public class Ports {
	private static final Port[] PORTS = createPorts();
	private static int PSR = 0;
	private static int PER = 0;

	private static Port[] createPorts() {
		Port[] ports = new Port[16];
		for (int i = 0; i < 16; i++)
			ports[i] = new Port(i);
		return ports;
	}

	public static void initPortDataFromAddresses(int id, Address[] data) {
		PORTS[id].initData(data);
	}

	private static class Port {
		private int id = 0;
		private Address[] data;
		private boolean isInit = false;

		Port(int id) {
			this.id = id;
		}

		void initData(Address[] data) {
			if (isInit)
				throw new IllegalStateException("Port " + id + " is already initialised!");
			this.data = data;
			isInit = true;
		}
	}
}
