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
	private PrintWriter out;
	private BufferedReader in;
	private ArrayList<String> recievedData;

	public NetworkClient(String ip) throws IOException {
		this(ip, 13337);
	}

	public NetworkClient(String ip, int port) throws IOException {
		System.out.println("Connecting to server...");
		new NetworkThread(new Socket(ip, port)).start(); // Connect to server with ip and port
		recievedData = new ArrayList<String>();
		System.out.println("Connected to server");
	}

	public void sendData(String data) {
		out.println(data);
	}

	public ArrayList<String> getRecievedData() {
		return recievedData;
	}

	public void close() {

	}

	private class NetworkThread extends Thread {
		public NetworkThread(Socket socket) throws IOException {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Get data from server
			out = new PrintWriter(socket.getOutputStream(), true); // send data from client
		}

		@Override
		public void run() {
			try {
				String message = null;
				while ((message = in.readLine()) != null) {
					System.out.println(message);
					recievedData.add(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
