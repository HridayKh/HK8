package hk8;

import hk8.hk8isacompiler.Hk8IsaCompiler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
class Main {
	void main(String[] args) {
		if (args.length == 0) {
			log.info("bad args!");
			return;
		}
		switch (args[0]) {
			case "compile":
				if (args.length < 2) {
					log.info("bad args for compile!");
					return;
				}
				try {
					new Hk8IsaCompiler(args);
				} catch (IOException e) {
					log.error("error: ", e);
				}
				break;
			case "sim":
				if (args.length < 2) {
					System.out.println("bad args for sim!");
					return;
				}
				try {
					new Hk8Sim(args);
				} catch (IOException e) {
					log.error("error: ", e);
				}
				break;
			default:
				System.out.println("wrong command!");
				break;
		}
	}
}
