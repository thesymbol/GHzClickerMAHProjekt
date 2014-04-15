package ghzclicker;

import java.awt.Color;
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
	private long hertz = 300;
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

	// Michaels test veribaler
	private String dog;
	private long modulus;
	private long remodulus;

	public Controller() {
		network = new NetworkClient("localhost");
		network.sendData("Test socket");
		network.close();
		buildings = new ArrayList<Building>();
		buildings.add(new Building("Hard drive", 50, 1, "res/Hard drive.png"));
		buildings.add(new Building("RAM", 300, 2, "res/NewRAM.png"));
		buildings.add(new Building("Power Supply", 1000, 100, "res/PowerSupply.png"));
		buildings.add(new Building("Hard Drive(SSD)", 7000, 10, "res/HardDrive(SSD).png"));
		buildings.add(new Building("Graphics card", 30000, 20, "res/GraphicsCard.png"));
		buildings.add(new Building("Processor", 150000, 30, "res/Processor.png"));
		buildings.add(new Building("MotherBoard", 1000000,400, "res/Motherboard.png"));

		Listener listener = new Listener();
		gui = new MenuGUI(createBuildingBtns(listener), listener);
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

	public void uppdateHertzPerSecond() {
		hertzPerSecond=0;
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
	 * MICHAEL TESTAR DETTA Denna gör hertz till en lång string
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
			File newTextFile = new File("res/GhzSaveGame.txt");
			FileWriter fw = new FileWriter(newTextFile);
			fw.write(txt);
			fw.close();
		} catch (IOException iox) {
			iox.printStackTrace();
		}
	}

	public void loadGame() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("res/GhzSaveGame.txt")));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			String[] store = sb.toString().split(":");

			for (int i = 0; i < store.length; i++) {
				System.out.print(store[i] + " ");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<JButton> createBuildingBtns(ActionListener listener) {
		ArrayList<JButton> btnBuildings = new ArrayList<JButton>();
		for (Building building : buildings) {
			JButton btn = new JButton(building.getName(), new ImageIcon(building.getImageLocation()));
			btn.setName(building.getName());
			btn.setVerticalTextPosition(JButton.CENTER);
			btn.setHorizontalTextPosition(JButton.CENTER);
			btn.setForeground(Color.MAGENTA);
			btn.setToolTipText(building.getName());
			btn.addActionListener(listener);
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
