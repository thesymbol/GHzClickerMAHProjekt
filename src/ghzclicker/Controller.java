package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Controller {
	private MenuGUI gui;
	private NetworkClient network;
	private long hertz = 0;
	private long kiloHertz = 0;
	private long megaHertz = 0;
	private long gigaHertz = 0;
	private long teraHertz = 0;
	private long petaHertz = 0;
	private long exaHertz = 0;
	private long baseValueClick = 1;
	private long clickModifier = 1;
	private long hertzPerSecond = 0;
	private String statistics;

	private ArrayList<Building> buildings;
	private ArrayList<Long> ALHertz;

	// Michaels test veribaler
	private String dog;
	private long modulus;
	private long remodulus;

	public Controller() {
		network = new NetworkClient("localhost");
		network.sendData("Test socket");
		network.close();
		buildings = new ArrayList<Building>();
		buildings.add(new Building("Hard drive", 50, 1, "res/NewHardDrive.png"));
		buildings.add(new Building("RAM", 300, 2, "res/NewRAM.png"));
		buildings.add(new Building("Power Supply", 1000, 100, "res/NewPowerSupply.png"));
		buildings.add(new Building("Hard Drive(SSD)", 7000, 10, "res/NewHardDrive(SSD).png"));
		buildings.add(new Building("Graphics card", 30000, 20, "res/NewGraphicsCard.png"));
		buildings.add(new Building("Processor", 150000, 30, "res/NewProcessor.png"));
		buildings.add(new Building("MotherBoard", 1000000, 400, "res/NewMotherboard.png"));

		Listener listener = new Listener();
		gui = new MenuGUI(createBuildingBtns(), listener);
		
		
		/**
		 * Michael försöker göra en göra en arraylist
		 * försöker
		 */		
		ALHertz = new ArrayList<Long>();
		ALHertz.add(new Long (hertz));
		ALHertz.add(new Long (kiloHertz));
		ALHertz.add(new Long (megaHertz));
		ALHertz.add(new Long (gigaHertz));
		ALHertz.add(new Long (teraHertz));
	}
	
	public void merge1() {
		for(int i=0; i<ALHertz.size();i++){
			
			
			modulus = hertz / 1000;
			hertz -= modulus * 1000;
			kiloHertz += modulus;
		
		}	
		
	}

	

	/**
	 * This is how much hertz we gona get per klick
	 */
	public void hertzClicked() {
		hertz += baseValueClick * clickModifier;
	}

	/**
	 * Calculate the transfer from Hertz -> KiloHertz
	 */
	public void merging() {
		if (hertz >= 1000) {
			modulus = hertz / 1000;
			hertz -= modulus * 1000;
			kiloHertz += modulus;
		}
		if (kiloHertz >= 1000) {
			modulus = kiloHertz / 10;
			kiloHertz -= modulus * 1000;
			megaHertz += modulus;
		}
		if (megaHertz >= 1000) {
			modulus = megaHertz / 1000;
			megaHertz -= modulus * 1000;
			gigaHertz += modulus;
		}
		if (gigaHertz >= 1000) {
			modulus = gigaHertz / 1000;
			gigaHertz -= modulus * 1000;
			teraHertz += modulus;
		}
	}
	
	/**
	 * Calculate the transfer from kiloHertz -> Hertz
	 */
	public void reMerge() {
		if (hertz < 0) {
			remodulus = Math.abs(hertz) / 1000;
			kiloHertz -= 1 + remodulus;
			hertz += remodulus * 1000 + 1000;
		}
		if (kiloHertz < 0) {
			remodulus = Math.abs(kiloHertz) / 1000;
			megaHertz -= 1 + remodulus;
			kiloHertz += remodulus * 1000 + 1000;
		}
		if (megaHertz < 0) {
			remodulus = Math.abs(megaHertz) / 1000;
			gigaHertz -= 1 + remodulus;
			megaHertz += remodulus * 1000 + 1000;
		}
		if (gigaHertz < 0) {
			remodulus = Math.abs(gigaHertz) / 1000;
			teraHertz -= 1 + remodulus;
			gigaHertz += remodulus * 1000 + 1000;
		}

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
		hertz += hertzPerSecond;
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
	 * Denna gör hertz till en lång string
	 */
	public String stringiFy() {
		dog = "";
		if (gigaHertz >= 0) {
			dog += Long.toString(gigaHertz) + "G   ";
		}
		if (megaHertz >= 0) {
			dog += Long.toString(megaHertz) + "M   ";
		}
		if (kiloHertz >= 0) {
			dog += Long.toString(kiloHertz) + "K   ";
		}
		if (hertz >= 0) {
			dog += Long.toString(hertz) + "Hz";
		}
		return dog;
	}

	/**
	 * Viktor testar Ser om jag kan spara spelet Ändra till rätt HDD på datorn, på mitt windows8 tillåts inte programmet att skapa och spara en fil på C:/
	 */
	public void saveGame() {
		try {
			String txt = gigaHertz + ":" + megaHertz + ":" + kiloHertz + ":" + hertz + ":";
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

			int ghz = Integer.parseInt(store[0]);
			int mhz = Integer.parseInt(store[1]);
			int khz = Integer.parseInt(store[2]);
			int hz = Integer.parseInt(store[3]);
			int hddCount = Integer.parseInt(store[4]);
			int ramCount = Integer.parseInt(store[5]);
			int pwrCount = Integer.parseInt(store[6]);
			int ssdCount = Integer.parseInt(store[7]);
			int graphicsCount = Integer.parseInt(store[8]);
			int processorCount = Integer.parseInt(store[9]);
			int motherboardCount = Integer.parseInt(store[10]);

			hertz = hz;
			kiloHertz = khz;
			megaHertz = mhz;
			gigaHertz = ghz;
			buildings.get(0).setOwned(hddCount);
			buildings.get(1).setOwned(ramCount);
			buildings.get(2).setOwned(pwrCount);
			buildings.get(3).setOwned(ssdCount);
			buildings.get(4).setOwned(graphicsCount);
			buildings.get(5).setOwned(processorCount);
			buildings.get(6).setOwned(motherboardCount);

			// Prints loaded data in console
			System.out.println(ghz + " " + mhz + " " + khz + " " + hz + " " + hddCount + " " + ramCount + " " + pwrCount + " " + ssdCount + " " + graphicsCount + " " + processorCount + " " + motherboardCount);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
		for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
			if (((gigaHertz * 1000000000) + (megaHertz * 1000000) + (kiloHertz * 1000) + hertz) < buildings.get(i).getPrice()) {
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
					building.setOwned(building.getOwned() + 1);
					hertz -= buildings.get(i).getPrice();
				}
			}
		}
	}
}
