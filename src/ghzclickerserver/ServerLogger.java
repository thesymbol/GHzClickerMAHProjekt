package ghzclickerserver;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logging system for server
 * this is to see whats happening on the server in a log file.
 * 
 * @author Marcus Orwén
 */
public class ServerLogger {
    /**
     * Initiate logger with standard server.log file.
     */
    public static void init() {
        init("server.log");
    }

    /**
     * Initiate logger with specified filename
     * 
     * @param filename The file to save to.
     */
    public static void init(String filename) {
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(filename, false); // File to save to
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileHandler != null) { // eliminate crash if FileHandler cannot create a file
            Logger logger = Logger.getLogger("");
            fileHandler.setFormatter(new SimpleFormatter()); // TODO: Create my own formatter.
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        }
    }
}
