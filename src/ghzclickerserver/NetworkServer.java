package ghzclickerserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Describes a network server. The server listens on a port for clients to accept. The server can then send and recieve data to/from the client. And lastly close the connection's if so needed.
 * 
 * @author Marcus Orwén
 */
public class NetworkServer {
	private ServerSocket server;
	private BufferedReader in;
	private PrintStream out;
	private Socket client = null;

	/**
	 * Constructs the server object with default port 13337
	 */
	public NetworkServer() {
		this(13337);
	}

	/**
	 * Constructs the server object with specified port.
	 * 
	 * @param port The port to listen for connections on.
	 */
	public NetworkServer(int port) {
		try {
			server = new ServerSocket(port); // Accept connections on specified port
		} catch (IOException e) {
			System.err.println("ERROR: Could not open server socket on port: " + port);
		}
	}

	/**
	 * Accept connection from client.
	 */
	public void accept() {
		try {
			client = server.accept(); // Get client connected
			out = new PrintStream(client.getOutputStream()); // send data from client
			in = new BufferedReader(new InputStreamReader(client.getInputStream())); // Get data from server
		} catch (IOException e) {
			System.err.println("ERROR: Could not accept client request");
		}
	}

	/**
	 * Get data from client
	 * 
	 * @return ArrayList<String> The data that the client sent.
	 */
	public ArrayList<String> getData() {
		ArrayList<String> response = new ArrayList<String>();
		try {
			String responseLine;
			if((responseLine = in.readLine()) != null) {
				response.add(responseLine);
			}
			/*while ((responseLine = in.readLine()) != null) {
				response.add(responseLine);
			}*/
		} catch (Exception e) {
			System.err.println("ERROR: Could not get data from client");
		}
		return response;
	}

	/**
	 * Send data to client
	 * 
	 * @param data The data to be sent to the client.
	 */
	public void sendData(String data) {
		try {
			out.println(data);
		} catch (Exception e) {
			System.err.println("ERROR: Could not send message");
		}
	}

	/**
	 * Close the connection to the client.
	 */
	public void closeClient() {
		try {
			in.close();
			out.close();
			client.close();
		} catch (IOException e) {
			System.err.println("ERROR: Could not close socket");
		}
	}

	/**
	 * Close the server, so it stops listening.
	 */
	public void closeServer() {
		try {
			server.close();
		} catch (Exception e) {
			System.err.println("ERROR: Could not close socket");
		}
	}
}
