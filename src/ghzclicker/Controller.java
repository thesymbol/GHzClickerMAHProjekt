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
    private String serverIp;
    private double hertzClicked;
    private double hertzPerClick;
    private long hertzGenerated;

    private ArrayList<Building> buildings;
    private ArrayList<Double> hertz;
    DecimalFormat hertzFormat = new DecimalFormat("#");
    DecimalFormat hpsFormat = new DecimalFormat("#.#");

    /**
     * Constructor which adds the network and the building buttons Adding hertz to an ArrayList.
     * 
     * @param ip The servers IP adress
     */
    public Controller(String ip) {
        this.serverIp = ip;
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

        hertz = new ArrayList<Double>();
        hertz.add(new Double(999));
        hertz.add(new Double(000));
        hertz.add(new Double(000));
        hertz.add(new Double(000));
        hertz.add(new Double(000));
        hertz.add(new Double(000));
        hertz.add(new Double(000));

    }

    /**
     * This dose so if hertz=1000, we will get 1Khz and 0 Hertz
     */
    public void merging() {
        int diff;
        for (int i = 0; i < hertz.size(); i++) {
            if (hertz.get(i) >= 1000) {
                diff = (int) (hertz.get(i) / 1000);
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
                diff = (int) Math.abs(hertz.get(i)) / 1000;
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
                ret += (hertzFormat.format(hertz.get(i))) + splitted[i] + " ";
            }
        }
        return ret;
    }

    /**
     * This is how much hertz we gona get per klick
     */
    public void hertzClicked() {
        hertz.set(0, hertz.get(0) + hertzPerClick);
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
        merging();
        reMerge();
        String hertz = stringiFy();
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
        // System.out.println("KHz: " + hertz.get(1));
    }

    /**
     * This dose so if you get 4.040 HPS you get 4 in KH and 40 in hertz.
     */
    public void hertzEverySecond() {
        hertzGenerated += hertzPerSecond;
        int dog;
        dog = (int) hertzPerSecond;
        for (int i = 0; i < hertz.size() && dog >= 1; i++) {
            hertz.set(i, (double) hertz.get(i) + (dog % 1000));
            dog /= 1000;
        }
    }

    /**
     * This dose so if a building cost 4.040 you will take 4 from KH and 40 from hertz
     * 
     * @param i, keeps record which building that was bought.
     */
    public void payingBuilding(int i) {
        long buildingPrice = buildings.get(i).getPrice();
        for (i = 0; i < hertz.size() && buildingPrice >= 1; i++) {
            hertz.set(i, (double) hertz.get(i) - (buildingPrice % 1000));
            buildingPrice /= 1000;
        }
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
        for (int i = 0; i < hertz.size(); i++) {
            data += hertz.get(i) + ":";
        }
        for (int i = 0; i < buildings.size(); i++) {
            data += buildings.get(i).getOwned() + ":";
        }

        System.out.println("[Info] Save data sent: " + data);

        try {
            NetworkClient client = new NetworkClient(serverIp);
            client.sendData("sendsave"); // notify that the next message is a save file.
            client.sendData(data);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loading the game from server (falls back to local if no server online).
     * 
     * @param loadString, The save file to load (in string format).
     */
    public void loadGameServer() {
        try {
            NetworkClient client = new NetworkClient(serverIp);
            client.sendData("loadsave");
            String saveData = client.getData();
            System.out.println("[Info] Save data loaded: " + saveData); // Prints loaded data in console
            String[] store = saveData.split(":");

            // TODO: Not rely on the hertz size (i) for the store array.
            for (int i = 0; i < hertz.size(); i++) {
                hertz.set(i, Double.parseDouble(store[i]));
            }

            // TODO: Not rely on the hertz size (i) for the store array.
            int hertzSize = hertz.size();
            for (int i = 0; i < store.length - hertz.size(); i++) {
                buildings.get(i).setOwned(Integer.parseInt(store[(i + hertzSize)]));
            }
            client.close();
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
            long cost = (long) (buildings.get(i).getBaseCost() * (Math.pow(1.1, buildings.get(i).getOwned()))); // cost algorithm
            if (buildings.get(i).getOwned() == 0) {
                cost = buildings.get(i).getBaseCost();
            }
            buildings.get(i).setPrice(cost);
            gui.updateJButtonCost(i, cost);
        }
    }

    /**
     * Gray out buttons when player dont have enough money
     */
    public void grayiFy() {
        double currTotalHertz = 0;
        double n = 1;

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
            double currTotalHertz = 0;
            double n = 1;
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
                    for (int j = 0; j < hertz.size(); j++) {
                        currTotalHertz += hertz.get(j) * n;
                        n *= 1000;
                    }
                    if (currTotalHertz >= buildings.get(i).getPrice()) {
                        building.setOwned(building.getOwned() + 1);
                        payingBuilding(i);
                        // hertz.set(0, hertz.get(0) -
                        // buildings.get(i).getPrice());
                    }
                }
            }
        }
    }
}
