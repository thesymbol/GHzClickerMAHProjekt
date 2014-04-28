package ghzclickerserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Describes a network server. The server listens on a port for clients to accept. The server can then send and recieve data to/from the client. And lastly close the connection's if so needed.
 * 
 * @author Marcus Orwén
 */

public class ServerController extends Thread {
	private FileHandler fileHandler;
	private BufferedReader in;
	private PrintWriter out;
	private ServerSocket serverSocket;

	public ServerController() throws IOException {
		this(13337);
	}

	public ServerController(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		fileHandler = new FileHandler();
		this.start();
		System.out.println("Server Started...");
	}

	public void sendData(String data) {
		if(out != null) {
			out.println(data);
		}
	}

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

	private class NetworkThread extends Thread {
		public NetworkThread(Socket socket) throws IOException {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Get data from server
			out = new PrintWriter(socket.getOutputStream(), true); // send data from client
		}

		@Override
		public void run() {
			try {
				System.out.println("Client connected");
				String message = null;
				while ((message = in.readLine()) != null) {
					System.out.println(message);
					if (message.equals("sendsave")) {
						fileHandler.save(in.readLine());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
