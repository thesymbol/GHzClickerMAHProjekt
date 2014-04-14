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
	
	public NetworkClient(String ip) {
		this(ip, 13337);
	}
	
	public NetworkClient(String ip, int port) {
		try {
			client = new Socket(ip, port);							// Connect to server with ip and port
		} catch (IOException e) {
			e.printStackTrace();
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
	    } catch (IOException e) {
	    	System.out.println(e);
	    }
	}
}
