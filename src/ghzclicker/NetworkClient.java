package ghzclicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Describes a network client. You can connect to a server on specified ip and port. The client can then send and recieve data to/from the server. And lastly close the connection if so needed.
 * 
 * @author Marcus Orwén
 */
public class NetworkClient {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private boolean running = false;

	public NetworkClient(String ip) throws IOException {
		this(ip, 13337);
	}

	public NetworkClient(String ip, int port) throws IOException {
		System.out.println("Connecting to server...");
		socket = new Socket(ip, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Get data from server
		out = new PrintWriter(socket.getOutputStream(), true); // send data from client
		System.out.println("Connected to server");
	}

	public void sendData(String data) {
		out.println(data);
	}
	
	public void open() {
		running = true;
		new NetworkThread().start();
	}

	public void close() {
		try {
			running = false;
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class NetworkThread extends Thread {
		@Override
		public void run() {
			try {
				String message = null;
				while (((message = in.readLine()) != null) && running) {
					System.out.println(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
