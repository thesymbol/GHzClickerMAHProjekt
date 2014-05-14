package ghzclickerserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Handles servers file's, you can save a file with specified data, filename and location.
 * 
 * @author Marcus Orwén
 */
public class SaveFileHandler {
    private final static Logger logger = ServerLogger.getLogger();

    /**
     * saves a file with specified data and location.
     * 
     * @param data The data to be saved
     * @param location The location to save the file to. (always end with /)
     * @param filename The filename to save to.
     * @param append Append to existing file.
     * 
     * @return true if file is saved else false.
     */
    public boolean save(String data, String location, String filename, Boolean append) {
        logger.info("Trying to save " + filename);
        try {
            File newTextFile = new File((location + filename));
            FileWriter fw = new FileWriter(newTextFile, append);
            fw.write(data);
            fw.close();
            logger.info("Saved " + filename + " with data: " + data);
        } catch (IOException e) {
            logger.severe("Could not save " + filename);
            return false;
        }
        return true;
    }

    /**
     * Loads a file with specified location and filename
     * 
     * @param location Location of file (always end with /)
     * @param filename Filename of the file
     */
    public ArrayList<String> load(String location, String filename) {
        logger.info("Trying to load " + filename);
        ArrayList<String> fileInfo = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File((location + filename))));
            String line;
            while ((line = reader.readLine()) != null) {
                fileInfo.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) { // if file is not found create it.
            logger.severe(filename + " not found");
            logger.info("Creating new " + filename + "...");
            if (save("", location, filename, false)) {
                logger.info("Created new " + filename);
            } else {
                logger.severe("Could not create " + filename);
            }
        } catch (IOException e) {
            ServerLogger.stacktrace(e);
        }
        logger.info(filename + " loaded");
        return fileInfo;
    }

    /**
     * create directory
     * 
     * @param location Location of the directory (always end with /)
     * @param dirname Directory name
     */
    public boolean createDir(String location, String dirname) {
        File folder = new File(location + dirname);
        boolean ret = false;
        if (folder.exists() && folder.isDirectory()) {
            logger.info("Save folder found");
            ret = true;
        } else {
            logger.severe("Save folder not found");
            logger.info("Creating save folder");
            if (folder.mkdirs()) {
                logger.info("Save folder created");
                ret = true;
            } else {
                logger.severe("Save folder could not be created");
                ret = false;
            }
        }
        return ret;
    }
    
    public void saveHighScore(String data){
    	String filename = "highscore.dat";
    	logger.info("Trying to save " + filename);
        try {
            File newTextFile = new File(filename);
            FileWriter fw = new FileWriter(newTextFile, false);
            fw.write(data);
            fw.close();
            logger.info("Saved " + filename + " with data: " + data);
        } catch (IOException e) {
            logger.severe("Could not save " + filename);
        }
    }
    
    public ArrayList<String> loadHighScore(){
    	String filename = "highscore.dat";
    	logger.info("Trying to load " + filename);
        ArrayList<String> fileInfo = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while ((line = reader.readLine()) != null) {
                fileInfo.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) { 
            logger.severe(filename + " not found");
        } catch (IOException e) {
            ServerLogger.stacktrace(e);
        }
        logger.info(filename + " loaded");
        return fileInfo;
    }
}
