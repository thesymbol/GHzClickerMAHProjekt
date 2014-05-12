package ghzclickerserver;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
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
    private Listener listener;
    private ServerGUI serverGUI;
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
        listener = new Listener();
        serverGUI = new ServerGUI(listener);
        serverSocket = new ServerSocket(port);
        fileHandler = new SaveFileHandler();
        networkThreads = new ArrayList<NetworkThread>();
        loggedInUsers = new ArrayList<String>();
        if (!fileHandler.createDir("", "saves")) { // if there was a major error (permissions problem on OS) we exit the server to stop crashes.
            logger.severe("Folder could not be created exiting (no premissions?)...");
            try {
                Thread.sleep(2000);
                System.exit(0);
            } catch (InterruptedException e) {
                ServerLogger.stacktrace(e);
            }
        }
        listening = true;
        this.start();

        logger.info("Server Started...");

        // safely close connection to server when game is closing.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ServerLogger.init(true); // since java's built in logger shutsdown before we can log our shutdown start a new logger here to workaround the problem
                Logger logger = ServerLogger.getLogger();
                try {
                    logger.info("Preparing to close server...");
                    listening = false;
                    logger.info("Closing active connections...");
                    Iterator<NetworkThread> itr = networkThreads.iterator();
                    while (itr.hasNext()) {
                        NetworkThread temp = itr.next();
                        if (!temp.isClosed()) {
                            temp.close();
                        }
                    }
                    logger.info("Closing server listener...");
                    serverSocket.close();
                    logger.info("Server shutdown successfully");
                } catch (IOException e) {
                    ServerLogger.stacktrace(e);
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
                if (!serverSocket.isClosed()) {
                    socket = serverSocket.accept();
                    NetworkThread nt = new NetworkThread(socket);
                    nt.start();
                    networkThreads.add(nt);
                }
            } catch (IOException e) {
                ServerLogger.stacktrace(e);
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
         */
        @Override
        public void run() {
            try {
                logger.info("Client connected");
                String message = null;
                while ((message = in.readLine()) != null) {
                    logger.info("Command: " + message);
                    if (message.equals("sendsave")) {
                        logger.info("Username: " + username);
                        fileHandler.save(in.readLine(), "saves/", (username + ".save"), false);
                    }
                    if (message.equals("loadsave")) {
                        logger.info("Username: " + username);
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
                            logger.info("Sent back: loginsuccessfull");
                            loggedInUsers.add(username);
                        } else if (status == 2) {
                            out.println("alreadylogged");
                            logger.info("Sent back: alreadylogged");
                            username = "";
                        } else {
                            out.println("error");
                            logger.info("Sent back: error");
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
                logger.info("Client disconnected");
            } catch (IOException e) {
                ServerLogger.stacktrace(e);
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
        logger.info("Trying to register new user");
        ArrayList<String> loaded = fileHandler.load("", "users.dat");
        Iterator<String> itr = loaded.iterator();
        boolean alreadyExist = false;
        while (itr.hasNext()) {
            String[] userData = itr.next().split(";");
            if (username.equals(userData[0])) { // if there is not username already
                alreadyExist = true;
            }
        }
        if (!alreadyExist) {
            if (fileHandler.save((username + ";" + password + "\n"), "", "users.dat", true)) {
                logger.info("Registerd new user");
                return true;
            }
        }
        logger.severe("User already exist");
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
        logger.info(username + " trying to login");
        ArrayList<String> loaded = fileHandler.load("", "users.dat");
        Iterator<String> itr = loaded.iterator();
        while (itr.hasNext()) {
            String[] userData = itr.next().split(";");
            if (username.equals(userData[0]) && password.equals(userData[1])) { // if there is not username already
                Iterator<String> itr2 = loggedInUsers.iterator();
                while (itr2.hasNext()) {
                    if (itr2.next().equals(username)) {
                        logger.severe(username + " already logged in");
                        return 2;
                    }
                }
                logger.info(username + " logged in successfully");
                return 1;
            }
        }
        logger.severe(username + " tried to login with invalid username or password");
        return -1;
    }
    private class Listener implements ActionListener {
        /**
         * A method used to determine which button is pressed and what will happend next.
         * 
         * @param e ,
         */
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == serverGUI.getBtnExit()){
                System.exit(0);
            }
        }
    }
}
