package ghzclicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Describes a network client. You can connect to a server on specified ip and port. The client can then send and recieve data to/from the server. And lastly close the connection if so needed.
 * 
 * @author Marcus Orwén
 */
public class NetworkClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int port;

    /**
     * Creates a network client with specified ip
     * 
     * @param ip The ip to connect to
     * @throws IOException
     */
    public NetworkClient(String ip) throws IOException {
        this(ip, 13337);
    }

    /**
     * Creates a network client with specified ip and port
     * 
     * @param ip The ip to connect to
     * @param port The port to connect to
     * @throws IOException
     */
    public NetworkClient(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        connect();
    }

    /**
     * Connect to the server.
     * 
     * @throws IOException
     */
    public void connect() throws IOException {
        System.out.println("[Info] Connecting to server...");
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Get data from server
            out = new PrintWriter(socket.getOutputStream(), true); // send data from client
            System.out.println("[Info] Connected to server");
        } catch (ConnectException e) {
            System.err.println("[Error] Connection refused");
        }
    }

    /**
     * Send data to the server
     * 
     * @param data The data to be sent
     */
    public void sendData(String data) {
        if (out != null) {
            out.println(data);
        }
    }

    /**
     * get data from server
     */
    public String getData() throws IOException {
        if (in != null) {
            return in.readLine();
        }
        return "";
    }

    /**
     * Open a listening connection to the server.
     */
    public void open() {
        new NetworkThread().start();
    }

    /**
     * Check if we have connection to the server
     * 
     * @return true if socket is closed else false
     */
    public boolean isClosed() {
        if (socket != null) {
            return socket.isClosed();
        }
        return true;
    }

    /**
     * Close down the connection to the server.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
        if (socket != null) {
            socket.close();
        }
        System.out.println("[Info] Disconnected from server");
    }

    /**
     * creates a networkThread that listens to server messages
     */
    private class NetworkThread extends Thread {
        /**
         * Always recieve messages from server.
         */
        @Override
        public void run() {
            try {
                String message = null;
                while (((message = in.readLine()) != null)) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("[Info] Disconnected from server");
            }
        }
    }
}
