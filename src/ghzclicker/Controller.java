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
	private long hertz = 0;
	private long kiloHertz = 0;
	private long megaHertz = 0;
	private long gigaHertz = 0;
	private long teraHertz = 0;
	private long petaHertz = 0;
	private long exaHertz = 0;
	private long baseValueClick = 1;
	private long clickModifier = 1;
	private long hertzPerSecond = 300;

	private ArrayList<Building> buildings;

	// Michaels test veribaler
	private String dog;
	private String statistics;
	private long modulus;
	private long remodulus;

	public Controller() {
		buildings = new ArrayList<Building>();
		buildings.add(new Building("Hard drive", 10, 0.2, ""));
		buildings.add(new Building("RAM", 50, 0.5, "res/RAM.png"));
		buildings.add(new Building("Power Supply", 0, 0, ""));
		buildings.add(new Building("Hard Drive(SSD)", 0, 0, ""));
		buildings.add(new Building("Graphics card", 0, 0, ""));
		buildings.add(new Building("Processor", 0, 0, ""));
		buildings.add(new Building("MotherBoard", 0, 0, ""));

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
		if (hertz<0){
			remodulus = (int) (Math.pow(hertz, 2.0)/2) / 1000;
			kiloHertz -= 1+(remodulus/1000);
			hertz=remodulus+1000;
		}
		if (kiloHertz<0){
			remodulus = (int) (Math.pow(kiloHertz, 2.0)/2) / 1000;
			megaHertz -= 1+(remodulus/1000);
			kiloHertz=remodulus+1000;
		}
		if (megaHertz<0){
			remodulus = (int) (Math.pow(megaHertz, 2.0)/2) / 1000;
			gigaHertz -= 1+(remodulus/1000);
			megaHertz=remodulus+1000;
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
		
	}

	
	public void uppdateHertzPerSecond() {
		for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
			hertzPerSecond += buildings.get(i).getOwned()
					* buildings.get(i).getBaseHPS();
		for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
			hertzPerSecond += buildings.get(i).getOwned()
					* buildings.get(i).getHPS();
		}
		gui.updateHertzPerSecond(Long.toString(hertzPerSecond));
	}
	
	public void uppdateStatistics(){
		
		for(int i = 0; i< gui.getBtnBuildings().size(); i++){
			statistics = "Total buildings : " + buildings.get(i).getOwned();
		}
		gui.updateStatistics(statistics);
	}

	/**
	 * This gets updated by the gameloop every second (used for the timing on
	 * building generating "Hertz"
	 */
	public void updateEverySecond() {
		hertz+=hertzPerSecond;
		network = new NetworkClient("localhost");
		network.sendData("Every 1 second");
		network.close();
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
	 * Viktor testar Ser om jag kan spara spelet Ändra till rätt HDD på datorn,
	 * på mitt windows8 tillåts inte programmet att skapa och spara en fil på
	 * C:/
	 */
	public void saveGame() {
		try {
			String txt = gigaHertz + ";" + megaHertz + ";" + kiloHertz + ";"
					+ hertz + ";";
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
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					"res/GhzSaveGame.txt")));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			String[] store = sb.toString().split(":");

			for (int i = 0; i < store.length - 1; i++) {
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
			JButton btn = new JButton(building.getName(), new ImageIcon(
					building.getImageLocation()));
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
			int cost = (int) (buildings.get(i).getBaseCost() * (Math.pow(1.1,
					buildings.get(i).getOwned()))); // cost algorithm
			if (buildings.get(i).getOwned() == 0) {
				cost = buildings.get(i).getBaseCost();
			}
			buildings.get(i).setPrice(cost);
			gui.updateJButtonCost(i, cost);
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
					hertz-=building.get(i).getPrice();
					uppdateHertzPerSecond(); // Michael testar, denna gör så
												// varje gång en byggnad köps så
												// uppdateras HertzPersecond
												// Value
				}
			}

		}
	}
}
