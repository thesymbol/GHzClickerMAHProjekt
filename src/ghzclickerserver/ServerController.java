package ghzclickerserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Describes a network server. The server listens on a port for clients to accept. The server can then send and recieve data to/from the client.
 * 
 * @author Marcus Orwén
 */
public class ServerController extends Thread {
    private SaveFileHandler fileHandler;
    private ServerSocket serverSocket;
    private ArrayList<NetworkThread> networkThreads;
    private boolean listening = false;
    private ArrayList<String> loggedInUsers;
    @SuppressWarnings("unused")
    private ServerGUI serverGUI;
    //private final static Logger logger = Logger.getLogger(ServerLogger.class.getName());
    private final static Logger logger = ServerLogger.getLogger();

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
        logger.log(Level.INFO, "test");
        
        serverGUI = new ServerGUI();
        serverSocket = new ServerSocket(port);
        fileHandler = new SaveFileHandler();
        networkThreads = new ArrayList<NetworkThread>();
        loggedInUsers = new ArrayList<String>();
        if (!fileHandler.createDir("", "saves")) { // if there was a major error (permissions problem on OS) we exit the server to stop crashes.
            System.err.println("[Error] Folder could not be created exiting (no premissions?)...");
            try {
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        listening = true;
        this.start();
        System.out.println("[Info] Server Started...");

        // safely close connection to server when game is closing.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("[Info] Preparing to close server...");
                    listening = false;
                    System.out.println("[Info] Closing active connections...");
                    Iterator<NetworkThread> itr = networkThreads.iterator();
                    while (itr.hasNext()) {
                        NetworkThread temp = itr.next();
                        if (!temp.isClosed()) {
                            itr.next().close();
                        }
                    }
                    System.out.println("[Info] Closing server listener...");
                    serverSocket.close();
                    System.out.println("[Info] Server shutdown successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Network thread to accept client connections
     */
    @Override
    public void run() {
        Socket socket;
        while (listening) {
            try {
                System.out.println(serverSocket.isClosed());
                if(!serverSocket.isClosed()) {
                    socket = serverSocket.accept();
                    NetworkThread nt = new NetworkThread(socket);
                    nt.start();
                    networkThreads.add(nt);
                }
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
        private Socket socket;

        /**
         * Opens a connection to and from specified client/socket.
         * 
         * @param socket The socket to accept input/output from.
         */
        public NetworkThread(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Get data from server
            out = new PrintWriter(socket.getOutputStream(), true); // send data from client
        }

        /**
         * Check if socket connection is open or not
         * 
         * @return true if socket connection is closed else false
         */
        public boolean isClosed() {
            return socket.isClosed();
        }

        /**
         * Close connection to client
         * 
         * @throws IOException
         */
        public void close() throws IOException {
            in.close();
            out.close();
            socket.close();
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
                        fileHandler.save(in.readLine(), "saves/", (username + ".save"), false);
                    }
                    if (message.equals("loadsave")) {
                        System.out.println("Username: " + username);
                        if (!fileHandler.load("saves/", (username + ".save")).isEmpty()) {
                            out.println(fileHandler.load("saves/", (username + ".save")).get(0));
                        } else {
                            out.println("error");
                        }
                    }
                    if (message.equals("sendlogininfo")) {
                        username = in.readLine();
                        int status = login(username, in.readLine());
                        if (status == 1) {
                            out.println("loginsuccessfull");
                            System.out.println("[Info] Sent back: loginsuccessfull");
                            loggedInUsers.add(username);
                        } else if (status == 2) {
                            out.println("alreadylogged");
                            System.out.println("[Info] Sent back: alreadylogged");
                            username = "";
                        } else {
                            out.println("error");
                            System.out.println("[Info] Sent back: error");
                            username = "";
                        }
                    }
                    if (message.equals("sendregdata")) {
                        if (register(in.readLine(), in.readLine())) {
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
            loggedInUsers.remove(username);
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
        boolean alreadyExist = false;
        while (itr.hasNext()) {
            String[] userData = itr.next().split(";");
            if (username.equals(userData[0])) { // if there is not username already
                alreadyExist = true;
            }
        }
        if(!alreadyExist) {
            if (fileHandler.save((username + ";" + password + "\n"), "", "users.dat", true)) {
                System.out.println("[Info] Registerd new user");
                return true;
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
     * 
     * @return -1 if username/password is invalid, 1 if everything was successfull and 2 if user is already logged in.
     */
    public int login(String username, String password) {
        System.out.println("[Info] " + username + " trying to login");
        ArrayList<String> loaded = fileHandler.load("", "users.dat");
        Iterator<String> itr = loaded.iterator();
        while (itr.hasNext()) {
            String[] userData = itr.next().split(";");
            if (username.equals(userData[0]) && password.equals(userData[1])) { // if there is not username already
                Iterator<String> itr2 = loggedInUsers.iterator();
                while (itr2.hasNext()) {
                    if (itr2.next().equals(username)) {
                        System.out.println("[Error] " + username + " already logged in");
                        return 2;
                    }
                }
                System.out.println("[Info] " + username + " logged in successfully");
                return 1;
            }
        }
        System.err.println("[Error] " + username + " tried to login with invalid username or password");
        return -1;
    }
}
