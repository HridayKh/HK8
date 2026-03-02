package in.hridaykh;

import java.io.IOException;

import in.hridaykh.hk8isacompiler.Hk8IsaCompiler;
import in.hridaykh.hk8sim.Hk8Sim;

public class Main {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("bad args!");
			return;
		}
		switch (args[0]) {
			case "compile":
				if (args.length < 2) {
					System.out.println("bad args for compile!");
					return;
				}
				new Hk8IsaCompiler(args);
				break;
			case "sim":
				if (args.length < 2) {
					System.out.println("bad args for sim!");
					return;
				}
				new Hk8Sim(args);
				break;
			default:
				System.out.println("wrong command!");
				break;
		}
	}

}
