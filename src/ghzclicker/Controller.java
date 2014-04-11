package ghzclicker;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Controller {
	private MenuGUI gui;
	private long hertz=20010;
	private long kiloHertz=110;
	private long megaHertz=11111;
	private long gigaHertz=115;
	private long teraHertz=116;
	private long petaHertz=0;
	private long exaHertz=0;
	private long baseValueClick=1;
	private long clickModifier=1;
	
	private ArrayList<Building> buildings;
	
	//Michaels test veribaler
	private String dog;
	private long modulus;
	
	public Controller() {
		buildings = new ArrayList<Building>();
		buildings.add(new Building("RAM", 10, 0.2, "res/RAM.png"));
		buildings.add(new Building("Graphics card", 0, 0, ""));
		buildings.add(new Building("Processor", 0, 0, ""));
		buildings.add(new Building("Hard drive", 0, 0, ""));
		buildings.add(new Building("MotherBoard", 0, 0, ""));
		buildings.add(new Building("Power Supply", 0, 0, ""));
		
		Listener listener = new Listener();
		gui = new MenuGUI(createBuildingBtns(listener), listener);
		
		JFrame frame1 = new JFrame("GHz Clicker");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.add(gui);
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
		
		
	}
	
	/**
	 * This is how much hertz we gona get per klick
	 */
	public void hertzClicked(){
		
		hertz+=baseValueClick*clickModifier;
	}
	
	public void merging() {
		if(hertz>=1000){
			modulus=hertz/1000;			
			hertz-=modulus*1000;
			kiloHertz+=modulus;
		}
		if(kiloHertz>=1000){
			modulus=kiloHertz/10;	
			kiloHertz-=modulus*1000;
			megaHertz+=modulus;
		}
		if(megaHertz>=1000){
			modulus=megaHertz/1000;	
			megaHertz-=modulus*1000;
			gigaHertz+=modulus;
		}
		if(gigaHertz>=1000){
			modulus=gigaHertz/1000;	
			gigaHertz-=modulus*1000;
			teraHertz+=modulus;
		}			
	}
	
	/**
	 * Game Loop calls this metod to update the game ~30 time a second
	 */
	public void update() {
		merging();
		String hertz = stringiFy();
		gui.update(hertz);
		calculateBuildingCosts();
	}
	
	/**
	 * This gets updated by the gameloop every second (used for the timing on building generating "Hertz"
	 */
	public void updateEverySecond() {
		System.out.println("Every 1 second update");
	}

	
	/** 
	 * MICHAEL TESTAR DETTA
	 * Denna gör hertz till en lång string
	 */
	public String stringiFy(){
		dog="";
		if(gigaHertz>=0){
			dog+=Long.toString(gigaHertz)+"G   "; 			 
		}
		if(megaHertz>=0) {
			dog+=Long.toString(megaHertz)+"M   ";
		}	
		if(kiloHertz>=0){
			dog+=Long.toString(kiloHertz)+"K   ";
		}	
		if(hertz>=0){
			dog+=Long.toString(hertz) + "Hz";
		}
		return dog;
	}
	/**
	 * Viktor testar
	 * Ser om jag kan spara spelet
	 * Ändra till rätt HDD på datorn, på mitt windows8 tillåts inte programmet att skapa och spara en fil på C:/ 
	 */
	public void saveGame(){
		try{
			String txt = gigaHertz + ";" + megaHertz + ";" + kiloHertz + ";" + hertz + ";";
			File newTextFile = new File("res/GhzSaveGame.txt");
			FileWriter fw = new FileWriter(newTextFile);
			fw.write(txt);
			fw.close();
		}catch(IOException iox){
			iox.printStackTrace();
		}
	}
 
	public ArrayList<JButton> createBuildingBtns(ActionListener listener) {
		ArrayList<JButton> btnBuildings = new ArrayList<JButton>();
		for(Building building : buildings) {
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
		for(int i = 0; i < buildings.size(); i++) {
			int cost = (int)(buildings.get(i).getBaseCost()*(buildings.get(i).getOwned()*1.1)); // cost algorithm
			if(buildings.get(i).getOwned() == 0) {
				cost = buildings.get(i).getBaseCost();
			}
			gui.updateJButtonCost(i, cost);
		}
	}

	/**
	 * Action listener for button presses
	 */
	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Hertz button
			if (e.getSource() == gui.getBtnHertz()) {
				hertzClicked();
			}

			// Save button
			if(e.getSource() == gui.getBtnSave()){
				saveGame();
			}

			//Building purcheses.
			for(int i = 0; i < gui.getBtnBuildings().size(); i++) {
				if(e.getSource() == gui.getBtnBuildings().get(i)) {
					Building building = buildings.get(i);
					building.setOwned(building.getOwned() + 1);
				}
			}
		}
	}
}