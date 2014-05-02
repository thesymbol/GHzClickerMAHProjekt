package ghzclickerserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles servers file's, you can save a file with specified data, filename and location.
 * 
 * @author Marcus Orwén
 */
public class FileHandler {
	
	

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
		System.out.println("[Info] Trying to save " + filename);
		try {
			File newTextFile = new File((location + filename));
			FileWriter fw = new FileWriter(newTextFile, append);
			fw.write(data);
			fw.close();
			System.out.println("[Info] Saved " + filename + " with data: " + data);
		} catch (IOException e) {
			System.err.println("[Error] Could not save " + filename);
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
		System.out.println("[Info] Trying to load " + filename);
		ArrayList<String> fileInfo = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File((location + filename))));
			String line;
			while ((line = reader.readLine()) != null) {
				fileInfo.add(line);
			}
			reader.close();
		} catch (FileNotFoundException e) { // if file is not found create it.
			System.err.println("[Error] " + filename + " not found");
			System.out.println("[Info] Creating new " + filename + "...");
			try {
				File newTextFile = new File((location + filename));
				FileWriter fw = new FileWriter(newTextFile);
				fw.write("");
				fw.close();
				System.out.println("[Info] Created new " + filename);
			} catch (IOException e1) {
				System.err.println("[Error] Could not create " + filename);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("[Info] " + filename + " loaded");
		return fileInfo;
	}
}
