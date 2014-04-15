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
	private BufferedReader in;
	private PrintStream out;
	private Socket client = null;
	
	public NetworkServer() {
		this(13337);
	}
	
	public NetworkServer(int port) {
		try {
			server = new ServerSocket(port);						// Accept connections on specified port
		} catch (IOException e) {
			System.err.println("ERROR: Could not open server socket on port: " + port);
		}
	}
	
	public void accept() {
		try {
			client = server.accept();	// Get client connected
			out = new PrintStream(client.getOutputStream());	// send data from client
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));	// Get data from server
		} catch (IOException e) {
			System.err.println("ERROR: Could not accept client request");
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
			System.err.println("ERROR: Could not get data from client");
		}
		return response;
	}
	
	public void sendData(String data) {
		try {
			out.println(data);
		} catch(Exception e) {
			System.err.println("ERROR: Could not send message");
		}
	}
	
	public void closeClient() {
		try {
			in.close();
			out.close();
			client.close();
	    } catch (IOException e) {
	    	System.err.println("ERROR: Could not close socket");
	    }
	}
	
	public void closeServer() {
		try {
			server.close();
		} catch (Exception e) {
	    	System.err.println("ERROR: Could not close socket");
	    }
	}
}
