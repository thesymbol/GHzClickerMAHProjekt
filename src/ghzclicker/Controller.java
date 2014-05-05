package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A class that controlls the whole program. Having all the logics within the application
 * 
 * @author Marcus Orwén , Mattias Holst , Viktor Saltarski , Michael Bergstrand
 * 
 */
public class Controller {
    private GameGUI gui;
    private int baseValueClick = 1;
    private double clickModifier = 1;
    private double hertzPerSecond = 0;
    private int clickCounter = 0;
    private double hertzClicked;
    private double hertzPerClick;
    private double hertzGenerated;

    private ArrayList<Building> buildings;
    private double hertz = 999;
    private DecimalFormat hertzFormat = new DecimalFormat("#");
    private DecimalFormat hpsFormat = new DecimalFormat("#.#");

    private NetworkClient network;

    /**
     * Constructor which adds the network and the building buttons Adding hertz to an ArrayList.
     * 
     * @param ip The servers IP adress
     */
    public Controller(NetworkClient network) {
        buildings = new ArrayList<Building>();
        buildings.add(new Building("Hard drive", 50, 1, "res/NewHardDrive.png"));
        buildings.add(new Building("RAM", 300, 10, "res/NewRAM.png"));
        buildings.add(new Building("Power Supply", 1000, 40, "res/NewPowerSupply.png"));
        buildings.add(new Building("Hard Drive(SSD)", 10000, 200, "res/NewHardDrive(SSD).png"));
        buildings.add(new Building("Graphics card", 50000, 1000, "res/NewGraphicsCard.png"));
        buildings.add(new Building("Processor", 200000, 3000, "res/NewProcessor.png"));
        buildings.add(new Building("MotherBoard", 1500000, 12000, "res/NewMotherboard.png"));

        Listener listener = new Listener();
        gui = new GameGUI(createBuildingBtns(), listener);

        this.network = network;
    }

    /**
     * Changes the visual of costs and hertz
     * 
     * @param value The value that is going to be used to create a prefix'ed string.
     * @return prefixed string with M B T or something else at the end.
     */
    public String stringify(double value) {
        String[] format = { "", " M", " B", " T", " Qa", " Qi", " Sx", " Sp", "Oc", "No", "Dc" };
        double temp = value;
        int order = 0;
        while (temp > 1000.0) {
            temp /= 1000.0;
            order += 1;
        }
        while (temp < 1.0) {
            temp *= 1000.0;
            order -= 1;
        }
        DecimalFormat formatter = new DecimalFormat("#.###");
        return formatter.format(temp) + format[order] + "Hz";
    }

    /**
     * This is how much hertz we gona get per klick
     */
    public void hertzClicked() {
        hertz += hertzPerClick;
        hertzClicked += hertzPerClick;
    }

    /**
     * This calculates how much you get per click
     */
    public void updateHertzPerClick() {
        hertzPerClick = baseValueClick + (clickModifier * hertzPerSecond * 0.05);
    }

    /**
     * Game Loop calls this metod to update the game ~30 time a second
     */
    public void update() {
        String hertz = stringify(this.hertz);
        gui.update(hertz);
        calculateBuildingCosts();
        grayiFy();
        updateHertzPerClick();
        uppdateHertzPerSecond();
        uppdateStatistics();
    }

    /**
     * This gets updated by the gameloop every second (used for the timing on building generating "Hertz"
     */
    public void updateEverySecond() {
        hertzEverySecond();
    }

    /**
     * This dose so if you get 4.040 HPS you get 4 in KH and 40 in hertz.
     */
    public void hertzEverySecond() {
        hertzGenerated += hertzPerSecond;
        hertz += hertzPerSecond;
    }

    /**
     * This dose so if a building cost 4.040 you will take 4 from KH and 40 from hertz
     * 
     * @param i, keeps record which building that was bought.
     */
    public void payingBuilding(int i) {
        double buildingPrice = buildings.get(i).getPrice();
        if (canBuyBuilding(i)) {
            hertz -= buildingPrice;
        }
    }

    /**
     * Check if you cna buy building specified with its id (i)
     * 
     * @param i Id of building
     * @return true if you can buy building else false.
     */
    public boolean canBuyBuilding(int i) {
        double buildingPrice = buildings.get(i).getPrice();
        if (hertz >= buildingPrice) {
            return true;
        }
        return false;
    }

    /**
     * This gets updated by the gameloop and calculate what your Hertz Per Second.
     */
    public void uppdateHertzPerSecond() {
        hertzPerSecond = 0;
        for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
            hertzPerSecond += buildings.get(i).getOwned() * buildings.get(i).getBaseHPS();
        }
        gui.updateHertzPerSecond(Double.toString(hertzPerSecond));
    }

    /**
     * this will update the statistics all the time.
     */
    public void uppdateStatistics() {
        String statistics = "";
        int TotalBuildings = 0;
        for (int i = 0; i < buildings.size(); i++) {
            TotalBuildings += buildings.get(i).getOwned();
        }
        statistics += TotalBuildings;
        statistics += "\n Total Harddrives : " + buildings.get(0).getOwned();
        statistics += "\n Total RAM : " + buildings.get(1).getOwned();
        statistics += "\n Total PowerSupplies : " + buildings.get(2).getOwned();
        statistics += "\n Total Harddrives(SSD) : " + buildings.get(3).getOwned();
        statistics += "\n Total Graphics Cards : " + buildings.get(4).getOwned();
        statistics += "\n Total Processors : " + buildings.get(5).getOwned();
        statistics += "\n Total Motherboards : " + buildings.get(6).getOwned();
        statistics += "\n Total Clicks : " + clickCounter;
        statistics += "\n Hertz Per click : " + hpsFormat.format(hertzPerClick);
        statistics += "\n Points By Clicks ; " + hertzFormat.format(hertzClicked);
        statistics += "\n Hertz Generated : " + hertzGenerated;
        statistics += "\n Hertz Generated : " + hertzFormat.format(hertzClicked + hertzGenerated);

        gui.updateStatistics(statistics);
    }

    /**
     * Viktor testar Ser om jag kan spara spelet Ändra till rätt HDD på datorn, på mitt windows8 tillåts inte programmet att skapa och spara en fil på C:/ Saving the game into a .save file in the
     * selected location.
     */
    public void saveGame() {
        String data = "";
        data += hertz + ":";
        for (int i = 0; i < buildings.size(); i++) {
            data += buildings.get(i).getOwned() + ":";
        }

        System.out.println("[Info] Save data sent: " + data);
        // TODO: Check if we are connected to the server or not.
        network.sendData("sendsave"); // notify that the next message is a save file.
        network.sendData(data);
    }

    /**
     * Loading the game from server (falls back to local if no server online).
     * 
     * @param loadString, The save file to load (in string format).
     */
    public void loadGameServer() {
        try {
            // TODO: Check if we are connected to the server or not.
            network.sendData("loadsave");
            String saveData = network.getData();
            System.out.println("[Info] Save data loaded: " + saveData); // Prints loaded data in console
            if (saveData.contains(":")) { // if we cannot find splitters then its not savedata.
                String[] store = saveData.split(":");
                hertz = Double.parseDouble(store[0]);
                int n = 1;
                for (int i = 0; i < buildings.size(); i++) {
                    buildings.get(i).setOwned(Integer.parseInt(store[n]));
                    n++;
                }
            }
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
            double cost = (buildings.get(i).getBaseCost() * (Math.pow(1.1, buildings.get(i).getOwned()))); // cost algorithm
            if (buildings.get(i).getOwned() == 0) {
                cost = buildings.get(i).getBaseCost();
            }
            buildings.get(i).setPrice(cost);
            gui.updateJButtonCost(i, stringify(cost));
        }
    }

    /**
     * Gray out buttons when player dont have enough money
     */
    public void grayiFy() {
        for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
            if (canBuyBuilding(i)) {
                gui.getBtnBuildings().get(i).setEnabled(true);
            } else {
                gui.getBtnBuildings().get(i).setEnabled(false);
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
                clickCounter++;
                hertzClicked();
            }

            // Save button
            if (e.getSource() == gui.getBtnSave()) {
                saveGame();
            }

            // Load button
            if (e.getSource() == gui.getBtnLoad()) {
                loadGameServer();
            }

            // Building purcheses.
            for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
                if (e.getSource() == gui.getBtnBuildings().get(i)) {
                    Building building = buildings.get(i);
                    if (hertz >= building.getPrice()) {
                        building.setOwned(building.getOwned() + 1);
                        payingBuilding(i);
                    }
                }
            }
        }
    }
}
