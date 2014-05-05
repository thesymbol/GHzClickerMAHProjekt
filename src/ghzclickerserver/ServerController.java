package ghzclickerserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Describes a network server. The server listens on a port for clients to accept. The server can then send and recieve data to/from the client.
 * 
 * @author Marcus Orwén
 */
public class ServerController extends Thread {
    private FileHandler fileHandler;
    private ServerSocket serverSocket;

    /**
     * Constructs a server with default port 13337
     * 
     * @throws IOException
     */
    public ServerController() throws IOException {
        this(13337);
    }

    /**
     * Constructs a server with specified port
     * 
     * @param port The port to listen on
     * @throws IOException
     */
    public ServerController(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        fileHandler = new FileHandler();
        if(!fileHandler.createDir("", "saves")) { // if there was a major error (permissions problem on OS) we exit the server to stop crashes.
            System.err.println("[Error] Folder could not be created exiting (no premissions?)...");
            try {
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.start();
        System.out.println("[Info] Server Started...");
    }

    /**
     * Network thread to accept client connections
     */
    @Override
    public void run() {
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                new NetworkThread(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Describes a NetworkThread that accepts client messages.
     */
    private class NetworkThread extends Thread {
        private String username = "";
        private BufferedReader in;
        private PrintWriter out;
        /**
         * Opens a connection to and from specified client/socket.
         * 
         * @param socket The socket to accept input/output from.
         */
        public NetworkThread(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Get data from server
            out = new PrintWriter(socket.getOutputStream(), true); // send data from client
        }

        /**
         * Always recieve messages from client
         * 
         * @TODO Need username to load correct save file.
         */
        @Override
        public void run() {
            try {
                System.out.println("[Info] Client connected");
                String message = null;
                while ((message = in.readLine()) != null) {
                    System.out.println("[Info] Command: " + message);
                    if (message.equals("sendsave")) {
                        System.out.println("Username: " + username);
                        fileHandler.save(in.readLine(), "saves/", "GHzSaveGame.save", false);
                    }
                    if (message.equals("loadsave")) {
                        System.out.println("Username: " + username);
                        out.println(fileHandler.load("saves/", "GHzSaveGame.save").get(0));
                    }
                    if (message.equals("sendlogininfo")) {
                        username = in.readLine();
                        if(login(username, in.readLine())) {
                            out.println("loginsuccessfull");
                        } else {
                            out.println("error");
                            username = "";
                        }
                    }
                    if (message.equals("sendregdata")) {
                        if(register(in.readLine(), in.readLine())) {
                            out.println("regsuccessfull");
                        } else {
                            out.println("error");
                        }
                    }
                }
                System.out.println("[Info] Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Register new user to file, if user already exists then do not insert new user.
     * 
     * @param username The username
     * @param password The password
     */
    public boolean register(String username, String password) {
        System.out.println("[Info] Trying to register new user");
        ArrayList<String> loaded = fileHandler.load("", "users.dat");
        Iterator<String> itr = loaded.iterator();
        while (itr.hasNext()) {
            String[] userData = itr.next().split(";");
            if (username.equals(userData[0])) { // if there is not username already
                if (fileHandler.save((username + ";" + password + "\n"), "", "users.dat", true)) {
                    System.out.println("[Info] Registerd new user");
                    return true;
                }
            }
        }
        System.err.println("[Error] User already exist");
        return false;
    }
    
    /**
     * Login a user
     * 
     * @param username The username
     * @param password The password
     */
    public boolean login(String username, String password) {
        System.out.println("[Info] " + username + " trying to login");
        ArrayList<String> loaded = fileHandler.load("", "users.dat");
        Iterator<String> itr = loaded.iterator();
        while (itr.hasNext()) {
            String[] userData = itr.next().split(";");
            if (username.equals(userData[0]) && password.equals(userData[1])) { // if there is not username already
                System.out.println("[Info] " + username + " logged in successfully");
                return true;
            }
        }
        System.err.println("[Error] " + username + " tried to login with invalid username or password");
        return false;
    }
}
