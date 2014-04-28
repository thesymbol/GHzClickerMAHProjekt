package ghzclickerserver;

import java.io.IOException;

/**
 * 
 * @author Marcus Orwén
 */
public class Server {
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			ServerController controller = new ServerController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
