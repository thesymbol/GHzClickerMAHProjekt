package ghzclickerserver;

import java.io.File;
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
		System.out.println(data);
		try {
			File newTextFile = new File((location + filename));
			FileWriter fw = new FileWriter(newTextFile);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
