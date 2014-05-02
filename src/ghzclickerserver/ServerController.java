package ghzclickerserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Describes a network server. The server listens on a port for clients to accept. The server can then send and recieve data to/from the client.
 * 
 * @author Marcus Orwén
 */
public class ServerController extends Thread {
	private FileHandler fileHandler;
	private BufferedReader in;
	private PrintWriter out;
	private ServerSocket serverSocket;

	/**
	 * Constructs a server with default port 13337
	 * 
	 * @throws IOException
	 */
	public ServerController() throws IOException {
		this(13337);
	}

	/**
	 * Constructs a server with specified port
	 * 
	 * @param port The port to listen on
	 * @throws IOException
	 */
	public ServerController(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		fileHandler = new FileHandler();
		this.start();
		System.out.println("[Info] Server Started...");
	}

	/**
	 * Send data to client
	 * 
	 * @param data The data to send
	 */
	public void sendData(String data) {
		if (out != null) {
			out.println(data);
		}
	}

	/**
	 * Network thread to accept client connections
	 */
	@Override
	public void run() {
		while (true) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				new NetworkThread(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Describes a NetworkThread that accepts client messages.
	 */
	private class NetworkThread extends Thread {
		/**
		 * Opens a connection to and from specified client/socket.
		 * 
		 * @param socket The socket to accept input/output from.
		 */
		public NetworkThread(Socket socket) throws IOException {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Get data from server
			out = new PrintWriter(socket.getOutputStream(), true); // send data from client
		}

		/**
		 * Always recieve messages from client
		 */
		@Override
		public void run() {
			try {
				System.out.println("[Info] Client connected");
				String message = null;
				while ((message = in.readLine()) != null) {
					System.out.println("[Info] Command: " + message);
					if (message.equals("sendsave")) {
						fileHandler.save(in.readLine(), "res/", "GHzSaveGame.save", false);
					}
					if (message.equals("loadsave")) {
						String loaded = fileHandler.load("res/", "GHzSaveGame.save").get(0);
						out.println("loadsave");
						out.println(loaded);
					}
					if (message.equals("sendlogininfo")) {
						ArrayList<String> loaded = fileHandler.load("res/", "users.dat");
						String username = in.readLine();
						String password = in.readLine();
						Iterator<String> itr = loaded.iterator();
						while (itr.hasNext()) {
							String[] userData = itr.next().split(";");
							if (username.equals(userData[0]) && password.equals(userData[1])) { // if there is not username already
								out.println("loginsuccessfull");
							}
						}
						out.println("error");
					}
					if (message.equals("sendregdata")) {
						System.out.println("[Info] Trying to register new user");
						ArrayList<String> loaded = fileHandler.load("res/", "users.dat");
						String username = in.readLine();
						String password = in.readLine();
						boolean alreadyExist = false;
						Iterator<String> itr = loaded.iterator();
						while (itr.hasNext()) {
							String[] userData = itr.next().split(";");
							if (username.equals(userData[0])) { // if there is not username already
								alreadyExist = true;
							}
						}
						if (!alreadyExist) { // if user don't already exist add it
							if (fileHandler.save(("\n" + username + ";" + password), "res/", "users.dat", true)) {
								out.println("regsuccessfull");
								System.out.println("[Info] Registerd new user");
							}
						} else {
							out.println("error");
							System.err.println("[Error] User already exist");
						}
					}
				}
				System.out.println("[Info] Client disconnected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
