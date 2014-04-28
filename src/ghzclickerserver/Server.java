package ghzclickerserver;

import java.io.IOException;

/**
 * Describes a server, this starts the whole server.
 * 
 * @author Marcus Orwén
 */
public class Server {
	/**
	 * Starts the server.
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			ServerController controller = new ServerController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
