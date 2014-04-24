package ghzclickerserver;

import java.util.Iterator;

/**
 * 
 * @author Marcus Orwén
 */
public class Controller {
	public Controller() {
		NetworkServer network = new NetworkServer();
		boolean running = true;
		while (running) {
			network.accept();
			Iterator<String> itr = network.getData().iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}
			network.sendData("respond");
			network.closeClient();
		}
		network.closeServer();
	}
}
