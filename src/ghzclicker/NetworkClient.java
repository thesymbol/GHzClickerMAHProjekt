package ghzclicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Describes a network client. You can connect to a server on specified ip and port. The client can then send and recieve data to/from the server. And lastly close the connection if so needed.
 * 
 * @author Marcus Orwén
 */
public class NetworkClient {
	private Socket client;
	private BufferedReader in;
	private PrintStream out;

	/**
	 * Constructs a client object with port 13337 and specified server ip
	 * 
	 * @param ip The ip of the server.
	 */
	public NetworkClient(String ip) {
		this(ip, 13337);
	}

	/**
	 * Constructs a client object with specified server port and ip.
	 * 
	 * @param ip The ip of the server.
	 * @param port The port of the server.
	 */
	public NetworkClient(String ip, int port) {
		try {
			client = new Socket(ip, port); // Connect to server with ip and port
			in = new BufferedReader(new InputStreamReader(client.getInputStream())); // Get data from server
			out = new PrintStream(client.getOutputStream()); // send data from client
		} catch (IOException e) {
			System.err.println("ERROR: Could not connect to server ip: " + ip + " port: " + port);
		}
	}

	/**
	 * Get data from server
	 * 
	 * @return ArrayList<String> The data that the server sent.
	 */
	public ArrayList<String> getData() {
		ArrayList<String> response = new ArrayList<String>();
		try {
			String responseLine;
			while ((responseLine = in.readLine()) != null) {
				response.add(responseLine);
			}
		} catch (IOException e) {
			System.err.println("ERROR: Could not get data from server");
		}
		return response;
	}

	/**
	 * Send data to server
	 * 
	 * @param data The data to be sent to the server.
	 */
	public void sendData(String data) {
		try {
			out.println(data);
		} catch (Exception e) {
			System.err.println("ERROR: Could not send message");
		}
	}

	/**
	 * Close the connection to the server.
	 */
	public void close() {
		try {
			in.close();
			out.close();
			client.close();
		} catch (Exception e) {
			System.err.println("ERROR: Could not close socket");
		}
	}
}
