package ghzclickerserver;

import java.util.Iterator;

/**
 * 
 * @author Marcus Orwén
 */
public class Main {
	public static void main(String[] args) {
		NetworkServer network = new NetworkServer();
		boolean running = true;
		while(running) {
			network.accept();
			Iterator itr = network.getData().iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}
			network.closeClient();
		}
		network.closeServer();
	}
}
