package ghzclicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 
 * @author Marcus Orwén
 */
public class NetworkClient {
	private Socket client;
	private BufferedReader in;
	private PrintStream out;

	public NetworkClient(String ip) {
		this(ip, 13337);
	}

	public NetworkClient(String ip, int port) {
		try {
			client = new Socket(ip, port); // Connect to server with ip and port
			in = new BufferedReader(new InputStreamReader(client.getInputStream())); // Get data from server
			out = new PrintStream(client.getOutputStream()); // send data from client
		} catch (IOException e) {
			System.err.println("ERROR: Could not connect to server ip: " + ip + " port: " + port);
		}
	}

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

	public void sendData(String data) {
		try {
			out.println(data);
		} catch (Exception e) {
			System.err.println("ERROR: Could not send message");
		}
	}

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
