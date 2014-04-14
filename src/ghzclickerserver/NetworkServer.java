package ghzclickerserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkServer {
	private ServerSocket server;
	private Socket client = null;
	
	public NetworkServer() {
		this(13337);
	}
	
	public NetworkServer(int port) {
		try {
			server = new ServerSocket(port);						// Accept connections on specified port
			client = server.accept();								// Get client connected
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public ArrayList<String> getData() {
		BufferedReader input;
		ArrayList<String> response = new ArrayList<String>();
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));	// Get data from server
			String responseLine;
            while ((responseLine = input.readLine()) != null) {
            	response.add(responseLine);
            }
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public void sendData(String data) {
		PrintStream output;
		try {
			output = new PrintStream(client.getOutputStream());	// send data from client
			output.println(data);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			client.close();
			server.close();
	    } catch (IOException e) {
	    	System.out.println(e);
	    }
	}
}
