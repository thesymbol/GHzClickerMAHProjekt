package ghzclickerserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
	 */
	public void save(String data, String location, String filename) {
		System.out.println("[Info] " + filename + " saved: " + data);
		try {
			File newTextFile = new File((location + filename));
			FileWriter fw = new FileWriter(newTextFile);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			System.err.println("[Error] Could not load " + filename);
		}
	}

	/**
	 * Loads a file with specified location and filename
	 * 
	 * @param location Location of file (always end with /)
	 * @param filename Filename of the file
	 */
	public String load(String location, String filename) {
		String ret = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File((location + filename))));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			ret = sb.toString();
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
		System.out.println("[Info] " + filename + " loaded: " + ret);
		return ret;
	}
}
