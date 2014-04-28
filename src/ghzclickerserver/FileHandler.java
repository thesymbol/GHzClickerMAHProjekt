package ghzclickerserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
	public void save(String data) {
		System.out.println(data);
		try {
			File newTextFile = new File("res/GhzSaveGame.save");
			FileWriter fw = new FileWriter(newTextFile);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
