package hk8.sim.state;

import hk8.HexFileLine;

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

	public static void initPortDataFromAddresses(int id, short[] data) {
		PORTS[id].initData(data);
	}

	public void psrOutB1() {
	}
	public void psrInB1() {
	}
	public void psrInA2() {
	}

	private static class Port {
		private int id = 0;
		private short[] data;
		private boolean isInit = false;

		Port(int id) {
			this.id = id;
		}

		void initData(short[] data) {
			if (isInit)
				throw new IllegalStateException("Port " + id + " is already initialised!");
			this.data = data;
			isInit = true;
		}
	}
}
