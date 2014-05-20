package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

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

    private String username = "";
    private String password = "";

    private ArrayList<Building> buildings;
    private ArrayList<Upgrade> upgrades;
    private double hertz = 0;
    private DecimalFormat hertzFormat = new DecimalFormat("#");
    private DecimalFormat hpsFormat = new DecimalFormat("#.#");

    private NetworkClient network;
    private HighScoreManager hsManager = new HighScoreManager();
    private final static Logger logger = ClientLogger.getLogger();

    /**
     * Constructor which adds the network and the building buttons Adding hertz to an ArrayList.
     * 
     * @param netowrk The servers IP adress
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

        upgrades = new ArrayList<Upgrade>();
        upgrades.add(new Upgrade("Hard drive upgrade 1", 1000, 200));
        upgrades.add(new Upgrade("RAM upgrade 1", 6000, 200));
        upgrades.add(new Upgrade("Power Supply upgrade 1", 20000, 200));
        upgrades.add(new Upgrade("Hard Drive(SSD) upgrade 1", 200000, 200));
        upgrades.add(new Upgrade("Graphics card upgrade 1", 1000000, 200));
        upgrades.add(new Upgrade("Processor upgrade 1", 4000000, 200));
        upgrades.add(new Upgrade("MotherBoard upgrade 1", 30000000, 200));

        Listener listener = new Listener();
        gui = new GameGUI(createBuildingBtns(), createUpgradeBtns(), listener);

        this.network = network;
        netAutoRecon();
    }

    /**
     * sets username and password
     * 
     * @param username Inserted username
     * @param password Inserted password
     */
    public void setUsernamePassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Show GUI
     * 
     * @param status True to show GUI and false to hide it.
     */
    public void guiSetVisibel(boolean status) {
        gui.setVisible(status);
    }

    /**
     * Changes the visual of costs and hertz
     * 
     * @param value The value that is going to be used to create a prefix'ed string.
     * @return prefixed string with M B T or something else at the end.
     */
    public String stringify(double value) {
        String[] format = { "", " K", " M", " G", " T", " Qa", " Qi", " Sx", " Sp", "Oc", "No", "Dc" };
        double temp = value;
        int order = 0;
        while (temp > 1000.0) {
            temp /= 1000.0;
            order += 1;
        }
        while (temp < 1.0 && temp > 0) {
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
     * Game Loop calls this metod to update the game ~30 time a second
     */
    public void update() {
        String hertz = stringify(this.hertz);
        gui.update(hertz);
        calculateBuildingCosts();
        calculateUpgradeCosts();
        grayiFy();
        upgradeGrayiFy();
        updateHertzPerClick();
        uppdateHertzPerSecond();
        uppdateStatistics();
    }

    /**
     * This gets updated by the gameloop every second (used for the timing on building generating "Hertz"
     */
    public void updateEverySecond() {
        hertzGenerated += hertzPerSecond;
        hertz += hertzPerSecond;
    }

    /**
     * Automaticly reconnect to the server with timer tasks.
     */
    public void netAutoRecon() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (network.isClosed()) {
                    try {
                        network.connect();
                        if (username != "" && password != "") {
                            network.sendData("sendlogininfo");// send this first to notify that we will send the username and password next
                            network.sendData(username);
                            network.sendData(password);
                            if (network.getData().equals("loginsuccessfull")) {
                                logger.info("relogged");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 5000, 5000);
    }

    /**
     * This dose so if a building cost 4.040 you will take 4 from KH and 40 from hertz
     * 
     * @param i , keeps record which building that was bought.
     */
    public void payingBuilding(int i) {
        double buildingPrice = buildings.get(i).getPrice();
        if (canBuyBuilding(i)) {
            hertz -= buildingPrice;
        }
    }

    /**
     * Checks if you can buy the upgrade, if you can you will lose that amount of hertz.
     * 
     * @param i , which upgrade that was bought.
     */
    public void payingUpgrade(int i) {
        double upgradePrice = upgrades.get(i).getPrice();
        if (canBuyUpgrade(i)) {
            hertz -= upgradePrice;
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
     * Check if you can buy upgrade specified with its id (i)
     * 
     * @param i ID of the upgrade.
     * @return true if you can buy upgrade, else false.
     */
    public boolean canBuyUpgrade(int i) {
        double upgradePrice = upgrades.get(i).getPrice();
        if (hertz >= upgradePrice) {
            return true;
        }
        return false;
    }

    /**
     * This calculates how much you get per click
     */
    public void updateHertzPerClick() {
        hertzPerClick = baseValueClick + (clickModifier * hertzPerSecond * 0.05);
    }

    /**
     * This gets updated by the gameloop and calculate what your Hertz Per Second.
     */
    public void uppdateHertzPerSecond() {
        hertzPerSecond = 0;
        for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
            hertzPerSecond += (buildings.get(i).getOwned() * buildings.get(i).getBaseHPS()) * (upgrades.get(i).getOwned() * 1.5);
            if (upgrades.get(i).getOwned() == 0) {
                hertzPerSecond += buildings.get(i).getOwned() * buildings.get(i).getBaseHPS();
            }
        }
        gui.updateHertzPerSecond(stringify(hertzPerSecond));
    }

    /**
     * This will update the statistics all the time.
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
        statistics += "\n Hertz Generated : " + hertzFormat.format(hertzGenerated);
        statistics += "\n Hertz Generated : " + hertzFormat.format(hertzClicked + hertzGenerated);

        gui.updateStatistics(statistics);
    }

    /**
     * Viktor testar Ser om jag kan spara spelet Ändra till rätt HDD på datorn, på mitt windows8 tillåts inte programmet att skapa och spara en fil på C:/ Saving the game into a .save file in the
     * selected location.
     */
    public void saveGame() {
        if (!network.isClosed()) {
            String data = "";
            data += hertz + ":";
            for (int i = 0; i < buildings.size(); i++) {
                data += buildings.get(i).getOwned() + ":";
            }
            for (int i = 0; i < upgrades.size(); i++) {
                data += upgrades.get(i).getOwned() + ":";
            }
            data += clickCounter + ":" + hertzClicked + ":" + hertzGenerated + ":";

            logger.info("Save data sent: " + data);
            network.sendData("sendsave"); // Notify that the next message is a save file.
            network.sendData(data);
        } else {
            logger.severe("Server is not online or you are not connected to the internet");
            gui.showErrorMessage("Server is not online or you are not connected to the internet");
        }

    }

    /**
     * Loading the game from server (falls back to local if no server online).
     * 
     * 
     */
    public void loadGameServer() {
        if (!network.isClosed()) {
            try {
                network.sendData("loadsave");
                String saveData = network.getData();
                logger.info("Save data loaded: " + saveData); // Prints loaded data in console
                if (saveData.contains(":")) { // If we cannot find splitters then its not savedata.
                    String[] store = saveData.split(":");
                    hertz = Double.parseDouble(store[0]);
                    int n = 1;
                    for (int i = 0; i < buildings.size(); i++) {
                        buildings.get(i).setOwned(Integer.parseInt(store[n]));
                        n++;
                    }
                    for (int i = 0; i < upgrades.size(); i++) {
                        upgrades.get(i).setOwned(Integer.parseInt(store[n]));
                        n++;
                    }
                    clickCounter = Integer.parseInt(store[n]);
                    n++;
                    hertzClicked = Double.parseDouble(store[n]);
                    n++;
                    hertzGenerated = Double.parseDouble(store[n]);
                    n++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.severe("Server is not online or you are not connected to the internet");
            gui.showErrorMessage("Server is not online or you are not connected to the internet");
        }
    }

    /**
     * Updates the highscore
     */
    public void updateHighScore() {
        if (!(network.isClosed())) {
            try {
                String highscoreData;
                network.sendData("loadhighscore");
                highscoreData = network.getData();
                hsManager.clear();
                logger.info(highscoreData);
                if (!highscoreData.equals("error")) {
                    String[] tempList = highscoreData.split(";");
                    for (String tempValue : tempList) {
                        String[] nameAndScore = tempValue.split(":");
                        hsManager.addScore(nameAndScore[0], Double.parseDouble(nameAndScore[1]));
                    }
                }

                // TODO: save the new highscore here.
                hsManager.addScore(username, hertzGenerated + hertzClicked);
                String saveToBeSent = hsManager.getHighScoresToSave();
                System.out.println(saveToBeSent);
                network.sendData("savehighscore");
                network.sendData(saveToBeSent);

                String txt = hsManager.getHighScoreString();
                gui.setHighScore(txt);
            } catch (IOException e) {
            }
        } else {
            logger.severe("Server is not online or you are not connected to the internet");
            gui.showErrorMessage("Server is not online or you are not connected to the internet");
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
            btn.setName(building.getName()); // Set the name of the button
            btn.setToolTipText(building.getName());
            btnBuildings.add(btn);
        }
        return btnBuildings;
    }

    /**
     * An ArrayList to create the buttons for the upgrades.
     * 
     * @return the upgrade buttons.
     */
    public ArrayList<JButton> createUpgradeBtns() {
        ArrayList<JButton> btnUpgrades = new ArrayList<JButton>();
        for (Upgrade upgrade : upgrades) {
            JButton btn = new JButton(upgrade.getName());
            btn.setName(upgrade.getName());
            btn.setToolTipText(upgrade.getName());
            btnUpgrades.add(btn);
        }
        return btnUpgrades;
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
     * Calculate the cost for each upgrades.
     */
    public void calculateUpgradeCosts() {
        for (int i = 0; i < upgrades.size(); i++) {
            double cost = upgrades.get(i).getCost() * (Math.pow(10, upgrades.get(i).getOwned()));
            if (upgrades.get(i).getOwned() == 0) {
                cost = upgrades.get(i).getCost();
            }
            upgrades.get(i).setPrice(cost);
            gui.updateUpgradeCost(i, stringify(cost));
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
     * Gray out the buttons when a player doesnt have enough money to buy upgrades.
     */
    public void upgradeGrayiFy() {

        for (int i = 0; i < gui.getBtnUpgrades().size(); i++) {
            int step = upgrades.get(i).getOwned();
            int owned = buildings.get(i).getOwned();
            if (canBuyUpgrade(i) && step == 0 && owned >= 10) {
                gui.getBtnUpgrades().get(i).setEnabled(true);

            } else if (canBuyUpgrade(i) && step == 1 && owned >= 50) {
                gui.getBtnUpgrades().get(i).setEnabled(true);

            } else if (canBuyUpgrade(i) && step == 2 && owned >= 100) {
                gui.getBtnUpgrades().get(i).setEnabled(true);

            } else if (canBuyUpgrade(i) && step == 2 && owned >= 200) {
                gui.getBtnUpgrades().get(i).setEnabled(true);

            } else {
                gui.getBtnUpgrades().get(i).setEnabled(false);
            }
        }
    }

    /**
     * Action listener for button presses
     */
    private class Listener implements ActionListener {
        /**
         * getting the listeners with actionPerformed
         * 
         * @param e ActionEvent
         */
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

            // High Score button
            if (e.getSource() == gui.getBtnHighScore()) {
                gui.setCard("2");
                updateHighScore();
            }

            // High Score button back
            if (e.getSource() == gui.getBtnBackHighScore()) {
                gui.setCard("1");
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
            // Upgrade purchases.
            for (int i = 0; i < gui.getBtnUpgrades().size(); i++) {
                if (e.getSource() == gui.getBtnUpgrades().get(i)) {
                    Upgrade upgrade = upgrades.get(i);
                    if (hertz >= upgrade.getPrice()) {
                        upgrade.setOwned(upgrade.getOwned() + 1);
                        payingUpgrade(i);
                    }
                }
            }
        }
    }
}