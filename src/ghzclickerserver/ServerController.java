package ghzclickerserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
    private ServerSocket serverArduinoSocket;
    private BufferedReader arduinoIn = null;
    private PrintWriter arduinoOut = null;
    private ArrayList<NetworkThread> networkThreads;
    private boolean listening = false;
    private ArrayList<String> loggedInUsers;
    private ArrayList<String> usersdata = new ArrayList<String>();
    private Listener listener;
    private ServerGUI serverGUI;
    private final static Logger logger = ServerLogger.getLogger();

    /**
     * Constructs a server with default port 13337
     * 
     * @throws IOException
     */
    public ServerController() throws IOException {
        this(13337, 13338);
    }

    /**
     * Constructs a server with specified port
     * 
     * @param port The port to listen on
     * @param port2 The arduino port to listen to
     * @throws IOException
     */
    public ServerController(int port, final int port2) throws IOException {
        listener = new Listener();
        serverGUI = new ServerGUI(listener);
        serverSocket = new ServerSocket(port);
        fileHandler = new SaveFileHandler();
        networkThreads = new ArrayList<NetworkThread>();
        loggedInUsers = new ArrayList<String>();
        if (!fileHandler.createDir("", "saves")) { // If there was a major error (permissions problem on OS) we exit the server to stop crashes.
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

        // Safely close connection to server when game is closing.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ServerLogger.init(true); // since java's built in logger shutsdown before we can log our shutdown start a new logger here to workaround the problem
                Logger logger = ServerLogger.getLogger();
                try {
                    logger.info("Preparing to close server...");
                    listening = false;
                    logger.info("Closing server listener...");
                    serverSocket.close();
                    if (arduinoIn != null) {
                        arduinoIn.close();
                    }
                    if (arduinoOut != null) {
                        arduinoOut.close();
                    }
                    serverArduinoSocket.close();
                    logger.info("Closing active connections...");
                    Iterator<NetworkThread> itr = networkThreads.iterator();
                    while (itr.hasNext()) {
                        NetworkThread temp = itr.next();
                        temp.close();
                    }
                    logger.info("Server shutdown successfully");
                } catch (IOException e) {
                    ServerLogger.stacktrace(e);
                }
            }
        });

        // To connect to arduino on a new Thread to not get the server stuck during bootup
        new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    Socket arduinoSocket;
                    try {
                        serverArduinoSocket = new ServerSocket(port2);
                        while (listening) {
                            if (isClosed()) {
                                try {
                                    logger.info("Listening for arduino connection");
                                    arduinoSocket = serverArduinoSocket.accept();
                                    logger.info("Got arduino connection");
                                    arduinoIn = new BufferedReader(new InputStreamReader(arduinoSocket.getInputStream())); // Get data from server
                                    arduinoOut = new PrintWriter(arduinoSocket.getOutputStream(), true); // send data from client
                                } catch (IOException e) {
                                    // ServerLogger.stacktrace(e);
                                }
                            }
                            sleep(5000); // Wait 5 seconds before reconnecting.
                        }
                    } catch (InterruptedException | IOException e) {
                        // ServerLogger.stacktrace(e);
                    }
                }
            }

            /**
             * Check if we have connection to the arduino
             * 
             * @return true if socket is closed else false
             */
            public boolean isClosed() {
                try {
                    arduinoOut.println("ping");
                    if (arduinoIn.readLine().equals("pong")) {
                        return false;
                    }
                } catch (Exception e) {
                }
                return true;
            }
        }.start();
    }

    /**
     * Network thread to accept client connections
     */
    @Override
    public void run() {
        Socket socket;
        synchronized (this) {
            try {
                while (listening) {
                    socket = serverSocket.accept();
                    socket.setSoTimeout(10000);
                    NetworkThread nt = new NetworkThread(socket);
                    nt.start();
                    networkThreads.add(nt);
                    wait(10);
                }
            } catch (IOException | InterruptedException e) {
                // ServerLogger.stacktrace(e);
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
        private boolean connected = true;
        private volatile long lastReadTime;
        private int maxTimeout = 10000;

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
         * Close connection to client
         * 
         * @throws IOException
         */
        public void close() throws IOException {
            logger.info("Attempting to close client connection");
            in.close();
            out.close();
            socket.close();
            connected = false;
        }

        /**
         * Check if the timeout has been reached
         * 
         * @return true if timeout has not been reached, else it will return false
         */
        public boolean isConnectionAlive() {
            return System.currentTimeMillis() - lastReadTime < maxTimeout;
        }

        /**
         * Always recieve messages from client
         */
        @Override
        public void run() {
            synchronized (this) {
                logger.info("Client connected");
                String message = null;
                while (connected) {
                    try {
                        message = in.readLine();
                        if (message != null) {
                            if (!message.equals("ping")) {
                                logger.info("Command: " + message);
                            }
                            if (message.equals("sendsave")) {
                                logger.info("Username: " + username);
                                fileHandler.save(in.readLine(), "saves/", (username + ".save"), false);
                            }
                            if (message.equals("loadsave")) {
                                logger.info("Username: " + username);
                                ArrayList<String> tempFile = fileHandler.load("saves/", (username + ".save"));
                                if (!tempFile.isEmpty()) {
                                    out.println(tempFile.get(0));
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
                                out.println(register(in.readLine(), in.readLine()));
                            }
                            if (message.equals("sendusername")) {
                                out.println(username);
                            }
                            if (message.equals("ping")) {
                                out.println("pong");
                                lastReadTime = System.currentTimeMillis();
                            }
                            if (message.equals("closeconnection")) {
                                close();
                            }
                            if (message.equals("savehighscore")) {
                                fileHandler.save(in.readLine(), "", "highscore.dat", false);
                            }
                            if (message.equals("loadhighscore")) {
                                ArrayList<String> tempFile = fileHandler.load("", "highscore.dat");
                                if (!tempFile.isEmpty()) {
                                    out.println(tempFile.get(0));
                                } else {
                                    out.println("error");
                                }
                            }
                        }
                        wait(10);
                    } catch (SocketTimeoutException e) {
                    } catch (IOException | InterruptedException e) {
                    }

                    // Check if connection is still alive.
                    if (!isConnectionAlive()) {
                        logger.severe("Connection lost");
                        try {
                            close();
                        } catch (IOException e) {
                            // ServerLogger.stacktrace(e);
                        }
                    }
                }
                logger.info("Client disconnected");
            }
            loggedInUsers.remove(username);
        }
    }

    /**
     * Gets the userdata from the arduino
     * 
     * @return if the arduino cannot send or is not avaiable it will return null, but if it is available it will return the ArrayList data of users.dat on the arduino.
     * @throws NumberFormatException
     * @throws IOException
     */
    public ArrayList<String> updateUsersData() throws NumberFormatException, IOException {
        ArrayList<String> ret = new ArrayList<String>();
        if (arduinoOut != null || arduinoIn != null) {
            arduinoOut.println("arduinodata");
            if (arduinoIn.readLine().equals("arduinodata")) {
                int length = Integer.parseInt(arduinoIn.readLine());
                for (int i = 0; i < length; i++) {
                    ret.add(arduinoIn.readLine());
                }
                return ret;
            }
        }
        return null;
    }

    /**
     * Register new user to file, if user already exists then do not insert new user.
     * 
     * @param username The username
     * @param password The password
     * @return A status message as a String like regtooshort, regsuccessfull
     * @throws IOException
     */
    public String register(String username, String password) throws IOException {
        logger.info("Trying to register new user");
        usersdata = updateUsersData();
        if (usersdata == null) {
            logger.severe("Arduino users.dat not avaiable");
            usersdata = fileHandler.load("", "users.dat");
        }
        if (username.length() <= 2 || password.length() <= 2) { //username or password too short.
            return "regtooshort";
        }
        Iterator<String> itr = usersdata.iterator();
        boolean alreadyExist = false;
        while (itr.hasNext()) {
            String[] userData = itr.next().split(";");
            if (username.equals(userData[0])) { // if there is not username already
                alreadyExist = true;
            }
        }
        if (!alreadyExist) {
            if (arduinoOut != null || arduinoIn != null) {
                arduinoOut.println("sendusersdata");
                arduinoOut.println(username + ";" + password + "\n");
                if (arduinoIn.readLine().equals("usersdataok")) {
                    logger.info("Registerd new user");
                    return "regsuccessfull";
                }
            } else {
                if (fileHandler.save((username + ";" + password + "\n"), "", "users.dat", true)) {
                    logger.info("Registerd new user");
                    return "regsuccessfull";
                }
            }
        }
        logger.severe("User already exist");
        return "error";
    }

    /**
     * Login a user
     * 
     * @param username The username
     * @param password The password
     * @return -1 if username/password is invalid, 1 if everything was successfull and 2 if user is already logged in.
     * @throws IOException
     * @throws NumberFormatException
     */
    public int login(String username, String password) throws NumberFormatException, IOException {
        logger.info(username + " trying to login");
        usersdata = updateUsersData();
        if (usersdata == null) {
            logger.severe("Arduino users.dat not avaiable");
            usersdata = fileHandler.load("", "users.dat");
        }
        Iterator<String> itr = usersdata.iterator();
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

    /**
     * Server GUI listener
     * 
     * @author Mattias Holst
     */
    private class Listener implements ActionListener {
        /**
         * A method used to determine which button is pressed and what will happend next.
         * 
         * @param e The event triggered
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == serverGUI.getBtnExit()) {
                System.exit(0);
            }
        }
    }
}
