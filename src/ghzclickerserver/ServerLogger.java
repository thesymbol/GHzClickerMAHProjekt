package ghzclickerserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Logging system for server
 * this is to see whats happening on the server in a log file.
 * 
 * @author Marcus Orwén
 */
public class ServerLogger {
    private static Logger logger;
    
    /**
     * Initiate logger with standard server.log file.
     */
    public static void init() {
        init("server", "server.log");
    }

    /**
     * Initiate logger with specified filename
     * 
     * @param name The name of the logger to identify it.
     * @param filename The file to save to.
     */
    public static void init(String name, String filename) {
        FileHandler fileHandler = null;
        LoggerFormatter formatter = new LoggerFormatter();
        try {
            fileHandler = new FileHandler(filename, false); // File to save to
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(fileHandler != null) { // eliminate crash if FileHandler cannot create a file
            logger = Logger.getLogger(name);
            logger.setUseParentHandlers(false);
            Handler[] handlers = logger.getHandlers();
            for(Handler handler : handlers) {
                logger.removeHandler(handler);
            }
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
            
        }
    }
    
    /**
     * Log stacktrace.
     * 
     * @param e Exception to log
     */
    public static void stacktrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        logger.severe(sw.toString());
    }
    
    public static Logger getLogger() {
        return logger;
    }
    
    /**
     * Custom formatter to format log output corretly
     * 
     * @author Marcus Orwén
     */
    private static class LoggerFormatter extends Formatter {
        /**
         * Called whenever i do a log entry.
         */
        @Override
        public String format(LogRecord rec) {
            StringBuffer buf = new StringBuffer(1000); // to store the log output for later use
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            buf.append(dateFormat.format(new Date(rec.getMillis())));
            buf.append(" [" + rec.getLevel() + "] ");
            buf.append(formatMessage(rec));
            buf.append("\n");
            
            if(rec.getLevel() == Level.SEVERE) {
                System.err.print(buf.toString());
            } else {
                System.out.print(buf.toString());
            }
            
            return buf.toString();
        }
    }
}