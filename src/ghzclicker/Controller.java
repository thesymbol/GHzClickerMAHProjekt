package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Taking care of all the logic within the application.
 * 
 * @author Marcus Orwén , Mattias Holst , Viktor Saltarski , Michael Bergstrand
 * 
 */
public class Controller {
	private MenuGUI gui;
	private NetworkClient network;
	private int baseValueClick = 1;
	private int clickModifier = 1;
	private int hertzPerSecond = 0;
	private String statistics;

	private ArrayList<Building> buildings;
	private ArrayList<Integer> hertz;

	/**
	 * Constructor which adds the network and the building buttons Adding hertz to an ArrayList.
	 */
	public Controller() {
		network = new NetworkClient("localhost");
		network.sendData("Test socket");
		network.close();
		buildings = new ArrayList<Building>();
		buildings.add(new Building("Hard drive", 50, 1, "res/NewHardDrive.png"));
		buildings.add(new Building("RAM", 300, 10, "res/NewRAM.png"));
		buildings.add(new Building("Power Supply", 1000, 40, "res/NewPowerSupply.png"));
		buildings.add(new Building("Hard Drive(SSD)", 10000, 200, "res/NewHardDrive(SSD).png"));
		buildings.add(new Building("Graphics card", 50000, 1000, "res/NewGraphicsCard.png"));
		buildings.add(new Building("Processor", 200000, 3000, "res/NewProcessor.png"));
		buildings.add(new Building("MotherBoard", 1500000, 12000, "res/NewMotherboard.png"));

		Listener listener = new Listener();
		gui = new MenuGUI(createBuildingBtns(), listener);

		hertz = new ArrayList<Integer>();
		hertz.add(new Integer(999));
		hertz.add(new Integer(0));
		hertz.add(new Integer(0));
		hertz.add(new Integer(0));
		hertz.add(new Integer(0));
		hertz.add(new Integer(0));
		hertz.add(new Integer(0));
	}

	/**
	 * This dose so if hertz=1000, we will get 1Khz and 0 Hertz
	 */
	public void merging() {
		int diff;
		for (int i = 0; i < hertz.size(); i++) {
			if (hertz.get(i) >= 1000) {
				diff = hertz.get(i) / 1000;
				hertz.set(i, (hertz.get(i) - diff * 1000));
				hertz.set(i + 1, (hertz.get(i + 1) + diff));
			}
		}
	}

	/**
	 * This dose so if hertz gets under 0 we will take from KHz and give to hertz
	 */
	public void reMerge() {
		int diff;
		for (int i = 0; i < hertz.size() - 1; i++) {
			if (hertz.get(i) < 0) {
				diff = Math.abs(hertz.get(i)) / 1000;
				hertz.set(i + 1, (hertz.get(i + 1) - (1 + diff)));
				hertz.set(i, (hertz.get(i) + diff * 1000 + 1000));
			}
		}
	}

	/**
	 * TODO: make the letters not into an array and not to rely on the hertz arraylist for refference. (aka not using the i in splitted[i] from the arraylist).
	 */
	public String stringiFy() {
		String letters = "Hz;K;M;G;T;P;E";
		String[] splitted = letters.split(";");
		String ret = "";
		for (int i = 0; i < hertz.size(); i++) {
			if (hertz.get(i) >= 0) {
				ret += Integer.toString(hertz.get(i)) + splitted[i] + " ";
			}
		}
		return ret;
	}

	/**
	 * This is how much hertz we gona get per klick
	 */
	public void hertzClicked() {
		hertz.set(0, hertz.get(0) + baseValueClick * clickModifier);
		// hertz += baseValueClick * clickModifier;
	}

	/**
	 * Game Loop calls this metod to update the game ~30 time a second
	 */
	public void update() {
		merging();
		reMerge();
		String hertz = stringiFy();
		gui.update(hertz);
		calculateBuildingCosts();
		grayiFy();
		uppdateHertzPerSecond();
	}

	/**
	 * This gets updated by the gameloop every second (used for the timing on building generating "Hertz"
	 */
	public void updateEverySecond() {
		hertz.set(0, hertz.get(0) + hertzPerSecond);
		// hertz += hertzPerSecond;
	}

	/**
	 * This gets updated by the gameloop and calculate what your Hertz Per Second.
	 */
	public void uppdateHertzPerSecond() {
		hertzPerSecond = 0;
		for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
			hertzPerSecond += buildings.get(i).getOwned() * buildings.get(i).getBaseHPS();
		}
		gui.updateHertzPerSecond(Long.toString(hertzPerSecond));
	}

	/**
	 * this will update the statistics all the time.
	 */
	public void uppdateStatistics() {

		for (int i = 0; i < buildings.size(); i++) {
			statistics += buildings.get(i).getOwned() + "\n";
		}
		gui.updateStatistics(statistics);
	}

	/**
	 * Viktor testar Ser om jag kan spara spelet Ändra till rätt HDD på datorn, på mitt windows8 tillåts inte programmet att skapa och spara en fil på C:/
	 */
	public void saveGame() {
		try {
			String txt = "";
			for (int i = 0; i < hertz.size(); i++) {
				txt += hertz.get(i) + ":";
			}
			for (int i = 0; i < buildings.size(); i++) {
				txt += buildings.get(i).getOwned() + ":";
			}
			File newTextFile = new File("res/GhzSaveGame.save");
			FileWriter fw = new FileWriter(newTextFile);
			fw.write(txt);
			fw.close();
		} catch (IOException iox) {
			iox.printStackTrace();
		}
	}

	/**
	 * Loading the file from the selected location.
	 */
	public void loadGame() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("res/GhzSaveGame.save")));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			String[] store = sb.toString().split(":");

			int hddCount = Integer.parseInt(store[7]);
			int ramCount = Integer.parseInt(store[8]);
			int pwrCount = Integer.parseInt(store[9]);
			int ssdCount = Integer.parseInt(store[10]);
			int graphicsCount = Integer.parseInt(store[11]);
			int processorCount = Integer.parseInt(store[12]);
			int motherboardCount = Integer.parseInt(store[13]);

			// TODO: Not rely on the hertz size (i) for the store array.
			for (int i = 0; i < hertz.size(); i++) {
				hertz.set(i, Integer.parseInt(store[i]));
			}

			buildings.get(0).setOwned(hddCount);
			buildings.get(1).setOwned(ramCount);
			buildings.get(2).setOwned(pwrCount);
			buildings.get(3).setOwned(ssdCount);
			buildings.get(4).setOwned(graphicsCount);
			buildings.get(5).setOwned(processorCount);
			buildings.get(6).setOwned(motherboardCount);

			// Prints loaded data in console
			for (int i = 0; i < store.length; i++) {
				System.out.print(store[i] + " ");
			}
			System.out.println();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * An ArrayList to create the buttons for the buildings.
	 * 
	 * @return the building buttons.
	 */
	public ArrayList<JButton> createBuildingBtns() {
		ArrayList<JButton> btnBuildings = new ArrayList<JButton>();
		for (Building building : buildings) {
			JButton btn = new JButton(building.getName(), new ImageIcon(building.getImageLocation()));
			btn.setName(building.getName()); // set the name of the button
			btn.setToolTipText(building.getName());
			btnBuildings.add(btn);
		}
		return btnBuildings;
	}

	/**
	 * Calculate cost for each building
	 */
	public void calculateBuildingCosts() {
		for (int i = 0; i < buildings.size(); i++) {
			int cost = (int) (buildings.get(i).getBaseCost() * (Math.pow(1.1, buildings.get(i).getOwned()))); // cost algorithm
			if (buildings.get(i).getOwned() == 0) {
				cost = buildings.get(i).getBaseCost();
			}
			buildings.get(i).setPrice(cost);
			gui.updateJButtonCost(i, cost);
		}
	}

	/**
	 * Gray out buttons
	 */
	public void grayiFy() {
		int currTotalHertz = 0;
		int n = 1;
		for (int i = 0; i < hertz.size(); i++) {
			currTotalHertz += hertz.get(i) * n;
			n *= 1000;
		}
		for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
			if (currTotalHertz < buildings.get(i).getPrice()) {
				gui.getBtnBuildings().get(i).setEnabled(false);
			} else {
				gui.getBtnBuildings().get(i).setEnabled(true);
			}
		}
	}

	/**
	 * Action listener for button presses
	 */
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int currTotalHertz = 0;
			int n = 1;
			// Hertz button
			if (e.getSource() == gui.getBtnHertz()) {
				hertzClicked();
			}

			// Save button
			if (e.getSource() == gui.getBtnSave()) {
				saveGame();
			}

			// Load button
			if (e.getSource() == gui.getBtnLoad()) {
				loadGame();
			}

			// Building purcheses.
			for (int i = 0; i < gui.getBtnBuildings().size(); i++) {

				if (e.getSource() == gui.getBtnBuildings().get(i)) {
					Building building = buildings.get(i);
					for (int j = 0; j < hertz.size(); j++) {
						currTotalHertz += hertz.get(j) * n;
						n *= 1000;
					}
					if (currTotalHertz >= buildings.get(i).getPrice()) {
						building.setOwned(building.getOwned() + 1);
						hertz.set(0, hertz.get(0) - buildings.get(i).getPrice());
					}
				}
			}
		}
	}
}
