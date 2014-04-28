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
	 * 
	 * @throws IOException
	 */
	public NetworkServer() throws IOException {
		this(13337);
	}

	/**
	 * Constructs the server object with specified port.
	 * 
	 * @param port The port to listen for connections on.
	 * @throws IOException
	 */
	public NetworkServer(int port) throws IOException {
		server = new ServerSocket(port); // Accept connections on specified port
	}

	/**
	 * Accept connection from client.
	 * 
	 * @throws IOException
	 */
	public void accept() throws IOException {
		client = server.accept(); // Get client connected
		out = new PrintStream(client.getOutputStream()); // send data from client
		in = new BufferedReader(new InputStreamReader(client.getInputStream())); // Get data from server
	}

	/**
	 * Get data from client
	 * 
	 * @return ArrayList<String> The data that the client sent.
	 * @throws IOException
	 */
	public ArrayList<String> getData() throws IOException {
		ArrayList<String> response = new ArrayList<String>();
		String responseLine;
		if(in != null) {
			while ((responseLine = in.readLine()) != null) {
				response.add(responseLine);
			}
		}
		return response;
	}

	/**
	 * Send data to client
	 * 
	 * @param data The data to be sent to the client.
	 */
	public void sendData(String data) {
		if (out != null) {
			out.println(data);
		}
	}

	/**
	 * Close the connection to the client.
	 * 
	 * @throws IOException
	 */
	public void closeClient() throws IOException {
		if(in != null) {
			in.close();
		}
		if(out != null) {
			out.close();
		}
		if(client != null) {
			client.close();
		}
	}

	/**
	 * Close the server, so it stops listening.
	 * 
	 * @throws IOException
	 */
	public void closeServer() throws IOException {
		if(server != null) {
			server.close();
		}
	}
}
