package ghzclicker;

import java.applet.Applet;import java.applet.AudioClip;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * A class that controlls the whole program. Having all the logics within the application
 * 
 * @author Marcus Orwén , Mattias Holst , Viktor Saltarski , Michael Bergstrand
 * 
 */
public class Controller {
    private GameGUI gui;
    private FlyingGame FG;
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
    private HighScoreManager hsManager = new HighScoreManager(this);
    private final static Logger logger = ClientLogger.getLogger();
    private int[] buildingHPSValue;
    private AudioClip ghzSound;
    private AudioClip backgroundMusic;
    private boolean stopMusic = false;
    private boolean dog = false;
    private Random rand = new Random();
    private int clock=0;
    

    private ArrayList<Achievements> achievements;

    /**
     * Constructor which adds the network and the building buttons Adding hertz to an ArrayList.
     * 
     * @param netowrk The servers IP adress
     */
    public Controller(NetworkClient network) {
        buildings = new ArrayList<Building>();
        buildings.add(new Building("Hard drive", 50, 2, "res/NewHardDrive.png"));
        buildings.add(new Building("RAM", 300, 10, "res/NewRAM.png"));
        buildings.add(new Building("Power Supply", 2100, 40, "res/NewPowerSupply.png"));
        buildings.add(new Building("Hard Drive(SSD)", 16800, 200, "res/NewHardDrive(SSD).png"));
        buildings.add(new Building("Graphics card", 134400, 1000, "res/NewGraphicsCard.png"));
        buildings.add(new Building("Processor", 1209600, 3000, "res/NewProcessor.png"));
        buildings.add(new Building("MotherBoard", 10886400, 12000, "res/NewMotherboard.png"));

        buildingHPSValue = new int[buildings.size()];

        upgrades = new ArrayList<Upgrade>();
        upgrades.add(new Upgrade("Hard drive upgrade", 1000, 200, 10, 3));
        upgrades.add(new Upgrade("RAM upgrade", 6000, 200, 10, 3));
        upgrades.add(new Upgrade("Power Supply upgrade", 20000, 200, 10, 3));
        upgrades.add(new Upgrade("Hard Drive(SSD) upgrade", 200000, 200, 10, 3));
        upgrades.add(new Upgrade("Graphics card upgrade", 1000000, 200, 10, 3));
        upgrades.add(new Upgrade("Processor upgrade", 4000000, 200, 10, 3));
        upgrades.add(new Upgrade("MotherBoard upgrade", 30000000, 200, 10, 3));

        achievements = new ArrayList<Achievements>();
        achievements.add(new Achievements("clicked 10 times", 10));
        achievements.add(new Achievements("clicked 500 times", 500));
        achievements.add(new Achievements("clicked 10000 times", 10000));
        achievements.add(new Achievements("generated 100", 100));
        achievements.add(new Achievements("generated 10000", 10000));
        achievements.add(new Achievements("generated 1000000", 1000000));
        achievements.add(new Achievements("generated 100000000", 100000000));
        achievements.add(new Achievements("You got 1 hardrive", 1));
        achievements.add(new Achievements("Not bad 10 HarDrives, You're getting somewhere", 10));
        achievements.add(new Achievements("100 HardDrives, thats alot of information.", 100));
        achievements.add(new Achievements("1 RAM not bad but i have OVER 9000", 1));
        achievements.add(new Achievements("10 RAM", 10));
        achievements.add(new Achievements("like you ever need 100 RAM", 100));
        achievements.add(new Achievements("1 PowerSupply, now you can run your computer", 1));
        achievements.add(new Achievements("Comeon man you no need 10 powerSupplys okej?", 10));
        achievements.add(new Achievements("Okej, This is ridiculous 100 PowerSupply realy?", 100));
        achievements.add(new Achievements("Nice an SSD", 1));
        achievements.add(new Achievements("10 SSD i quess its okej", 10));
        achievements.add(new Achievements("100SSD", 100));
        achievements.add(new Achievements("1 Graphic card, now you can play some games", 1));
        achievements.add(new Achievements("man what games do you need 10 Graphics card?", 10));
        achievements.add(new Achievements("Crysis 45 on ultra or why 100 Graphics card?", 100));
        achievements.add(new Achievements("1 Processor", 1));
        achievements.add(new Achievements("10 Processor", 10));
        achievements.add(new Achievements("100 Processor", 100));
        achievements.add(new Achievements("1 MotherBoard", 1));
        achievements.add(new Achievements("10 MotherBoard", 10));
        achievements.add(new Achievements("100 MotherBoard", 100));
        achievements.add(new Achievements("10 of all buildings", 10));
        achievements.add(new Achievements("100 of all buildings, You realy likes overkill", 100));

        Listener listener = new Listener();
        gui = new GameGUI(createBuildingBtns(), createUpgradeBtns(), createAchievementsBtns(), listener);
        loadSounds();
        
        Vehicle vehicle = new Vehicle("res/vehicle.png");
        FlyingGame FG = new FlyingGame(vehicle);

        this.network = network;
        netAutoRecon();
        
        
    }    
    

    /**
     * This checks if you have any new achievements
     */
    public void unlock() {
        int achievementsID;
        int i;
        int diff = 0;
        for (achievementsID = 0; achievementsID < 3; achievementsID++) {
            achievements.get(achievementsID).setOwned(achievements.get(achievementsID).requirement((int) hertzClicked));
        }
        for (; achievementsID < 7; achievementsID++) {
            achievements.get(achievementsID).setOwned(achievements.get(achievementsID).requirement((int) hertzGenerated));
        }
        for (i = 0; i < buildings.size(); i++) {
            achievements.get(achievementsID).setOwned(achievements.get(achievementsID).requirement(buildings.get(i).getOwned()));
            achievementsID++;
            achievements.get(achievementsID).setOwned(achievements.get(achievementsID).requirement(buildings.get(i).getOwned()));
            achievementsID++;
            achievements.get(achievementsID).setOwned(achievements.get(achievementsID).requirement(buildings.get(i).getOwned()));
            achievementsID++;
        }
        for (i = 0; i < buildings.size() && diff == 0; i++) {
            if (buildings.get(i).getOwned() < 10) {
                diff = 1;
            }
        }
        if (diff == 0) {
            achievements.get(achievementsID).setOwned(true);
        }

        achievementsID++;
        for (i = 0; i < buildings.size() && diff == 0; i++) {
            if (buildings.get(i).getOwned() < 100) {
                diff = 1;
            }
        }
        if (diff == 0) {
            achievements.get(achievementsID).setOwned(true);
        }
        achievementsID++;
    }

    /**
     * A miniGame where you get to choose a thing from 3 difrent boxes and this method randoms whats in it.           
     */      
    public void randomBonous() {
        // 30% nothing
        // 10% 1000 free hertz
        // 20% harddrive
        // 10% ram
        // 20% SSD
        // 10% graphics card
        // 1% motherboard
        // =101%

        int randomID = rand.nextInt(101);
        if (randomID < 10) {
            JOptionPane.showMessageDialog(null, "A free MOTHERBOARD OMG SO GOOD BEST RNG EVER");
            JOptionPane.showMessageDialog(null, "jkjk you get nothing");
        } else if (randomID < 20) {
            JOptionPane.showMessageDialog(null, "you get 1000 free Hertz");
            hertz += 1000;
        } else if (randomID < 30) {
            JOptionPane.showMessageDialog(null, "You win a room full of nothing");
        } else if (randomID < 40) {
            JOptionPane.showMessageDialog(null, "A free SSD not bad");
            buildings.get(3).setOwned(buildings.get(3).getOwned() + 1);
        } else if (randomID < 50) {
            JOptionPane.showMessageDialog(null, "A free harddrive, well i could have been better");
            buildings.get(0).setOwned(buildings.get(0).getOwned() + 1);
        } else if (randomID < 60) {
            JOptionPane.showMessageDialog(null, "Not even close");
        } else if (randomID < 70) {
            JOptionPane.showMessageDialog(null, "A free harddrive, hats prety shit");
            buildings.get(0).setOwned(buildings.get(0).getOwned() + 1);
        } else if (randomID < 80) {
            JOptionPane.showMessageDialog(null, "A free RAM, well it could be worse right?");
            buildings.get(1).setOwned(buildings.get(1).getOwned() + 1);
        } else if (randomID < 90) {
            JOptionPane.showMessageDialog(null, "A free Graphics card not bad");
            buildings.get(4).setOwned(buildings.get(4).getOwned() + 1);
        } else if (randomID < 100) {
            JOptionPane.showMessageDialog(null, "YOU GET TWO SSD jkjk only 1");
            buildings.get(3).setOwned(buildings.get(3).getOwned() + 1);
        } else {
            JOptionPane.showMessageDialog(null, "A free MOTHERBOARD OMG SO GOOD BEST RNG EVER");
            buildings.get(6).setOwned(buildings.get(6).getOwned() + 1);
        }
    }

    /**
     * Loading all the game sounds
     */
    public void loadSounds() {
        try {
            this.ghzSound = Applet.newAudioClip(new URL("file:res/GhzSound.wav"));
            this.backgroundMusic = Applet.newAudioClip(new URL("file:res/BackgroundMusic.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starting the background music
     */
    public void startBackgroundMusic() {
        backgroundMusic.loop();
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
            btn.setToolTipText("<html>" + building.getName() + "<br>" + " This will cost you : " + building.getBaseCost() + "hz<br>" + "This building will give you : " + building.getBaseHPS() + "hz</html>");
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
        for (int i = 0; i < upgrades.size(); i++) {
            JButton btn = new JButton(upgrades.get(i).getName());
            btn.setName(upgrades.get(i).getName());
            btn.setToolTipText("<html>" + upgrades.get(i).getName() + "<br>" + " This will make your " + buildings.get(i).getName() + " building 2 times better." + "<br>" + "To buy this upgrade you must have " + upgrades.get(i).getRequirement() + " of : " + buildings.get(i).getName() + "</html>");
            btnUpgrades.add(btn);
        }
        return btnUpgrades;
    }

    /**
     * An ArrayList to crate the achievements for in the game.
     * 
     * @return the achievements buttons.
     */
    public ArrayList<JButton> createAchievementsBtns() {
        ArrayList<JButton> btnAchievements = new ArrayList<JButton>();
        for (Achievements achievement : achievements) {
            JButton btn = new JButton(achievement.getName());
            btn.setName(achievement.getName()); // Set the name of the button
            btn.setToolTipText("<html>" + achievement.getName() + "<br>" + "</html>");
            btnAchievements.add(btn);
        }
        return btnAchievements;
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
     * Send the save to the server to be saved, no local saves.
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
            for (int i = 0; i < upgrades.size(); i++) {
                data += upgrades.get(i).getRequirement() + ":";
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
     * Loading the game from server.
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
                    for (int i = 0; i < upgrades.size(); i++) {
                        upgrades.get(i).setRequirement(Integer.parseInt(store[n]));
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
     * Updates the highscore on the server and the visible part on the client
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

                hsManager.addScore(username, hertzGenerated + hertzClicked);
                String saveToBeSent = hsManager.getHighScores(true);
                System.out.println(saveToBeSent);
                network.sendData("savehighscore");
                network.sendData(saveToBeSent);

                String txt = hsManager.getHighScores(false);
                gui.setHighScore(txt);
            } catch (IOException e) {
            }
        } else {
            logger.severe("Server is not online or you are not connected to the internet");
            gui.showErrorMessage("Server is not online or you are not connected to the internet");
        }
    }

    /**
     * Set if the GUI should be visible or not
     * 
     * @param status True to show GUI and false to hide it.
     */
    public void guiSetVisibel(boolean status) {
        gui.setVisible(status);
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
     * Game Loop calls this metod to update the game ~30 time a second
     */
    public void update() {
        String hertz = stringify(this.hertz);
        gui.update(hertz);
        calculateCosts();

        grayify();
        unlock();

        updateHertzPerClick();
        uppdateHertzPerSecond();
        uppdateBuildingHPSValue();
        uppdateToolTip();
        uppdateStatistics();
        FG.action();
        
    }

    /**
     * This gets updated by the gameloop every second (used for the timing on building generating "Hertz"
     */
    public void updateEverySecond() {
        hertzGenerated += hertzPerSecond;
        hertz += hertzPerSecond;
        clock++;
    }

    /**
     * This gets updated by the gameloop every minute (used for the timing on building generating "Hertz"
     */
    public void updateEveryMinute() {
        if (username != "") {
            saveGame();
            updateHighScore();
            int dog = rand.nextInt(2);
            if (dog == 0 && clock<=10) {
                gui.setCard("4");
            } else {
                doYouLikeTheGame();
            }
        }

    }

    /**
     * This will check if you like the game or nor but if u dont say yes you will be in a neverending loop
     */
    public void doYouLikeTheGame() {
        String awnser = "";
        if (dog == true) {
            gui.setCard("4");
        }
        while (dog == false) {
            if (dog == false) {
                awnser = JOptionPane.showInputDialog(null, "Do You like the game? thx for the awnser");
                JOptionPane.showMessageDialog(null, "thx for the yes");

            }
            if (awnser.equals("yes")) {
                dog = true;
            }
        }        
    }

    /**
     * This updates how much your HPS from a building
     */
    public void uppdateBuildingHPSValue() {
        for (int i = 0; i < buildings.size(); i++) {
            buildingHPSValue[i] = (int) buildings.get(i).getBaseHPS() * buildings.get(i).getOwned() * (upgrades.get(i).getOwned() + 1);
            if (upgrades.get(i).getOwned() == 0) {
                buildingHPSValue[i] = (int) buildings.get(i).getBaseHPS() * buildings.get(i).getOwned();
            }
        }
    }

    /**
     * This updates the tooltip on the gameGUI
     */
    public void uppdateToolTip() {
        for (int i = 0; i < buildings.size(); i++) {
            gui.setToolTipBuildings(("<html>" + buildings.get(i).getName() + "<br>" + "You have " + buildings.get(i).getOwned() + " " + buildings.get(i).getName() + "<br>" + " This will cost you : " + stringify(buildings.get(i).getPrice()) + "<br>" + "This building will give you : " + stringify((buildings.get(i).getBaseHPS()) * (upgrades.get(i).getOwned() + 1)) + "<br>" + "You are geting " + stringify(buildingHPSValue[i]) + " from all your " + buildings.get(i).getName() + "s</html>"), i);

            gui.setToolTipUpgrades("<html>" + upgrades.get(i).getName() + "<br>" + " This will make your " + buildings.get(i).getName() + " building 2 times better." + "<br>" + "To buy this upgrade you must have " + upgrades.get(i).getRequirement() + " of : " + buildings.get(i).getName() + "</html>", i);
        }
    }

    /**
     * This is how much hertz we gona get per click
     */
    public void hertzClicked() {
        hertz += hertzPerClick;
        hertzClicked += hertzPerClick;
        clock=0;
        
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
            hertzPerSecond += (buildings.get(i).getOwned() * buildings.get(i).getBaseHPS()) * ((upgrades.get(i).getOwned() + 1));
        }
        gui.updateHertzPerSecond(stringify(hertzPerSecond));
    }

    /**
     * This will update the statistics all the time.
     */
    public void uppdateStatistics() {
        String statistics = "";
        int totalBuildings = 0;
        for (int i = 0; i < buildings.size(); i++) {
            totalBuildings += buildings.get(i).getOwned();
        }
        statistics += "Total Buildings : " + totalBuildings;
        for (int i = 0; i < buildings.size(); i++) {
            statistics += "\nTotal" + buildings.get(i).getName() + " : " + buildings.get(i).getOwned();
        }
        statistics += "\nTotal Clicks : " + clickCounter;
        statistics += "\nHertz Per click : " + hpsFormat.format(hertzPerClick);
        statistics += "\nPoints By Clicks : " + hertzFormat.format(hertzClicked);
        statistics += "\nHertz Generated : " + hertzFormat.format(hertzGenerated);
        statistics += "\nTotal Hertz Generated : " + hertzFormat.format(hertzClicked + hertzGenerated);

        gui.updateStatistics(statistics);
    }

    /**
     * Calculate cost for upgrades and buildings
     */
    public void calculateCosts() {
        for (int i = 0; i < buildings.size(); i++) {
            buildings.get(i).calculateCosts();
            gui.updateJButtonCost(i, stringify(buildings.get(i).getPrice()));
        }

        for (int i = 0; i < upgrades.size(); i++) {
            if (upgrades.get(i).getOwned() == upgrades.get(i).getMaxOwned()) {
                gui.upgradeMaxedOut(i);
            } else {
                upgrades.get(i).calculateCosts();
                gui.updateUpgradeCost(i, stringify(upgrades.get(i).getPrice()));
            }
        }
    }

    /**
     * Gray out buttons when player dont have enough money
     */
    public void grayify() {
        for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
            if (buildings.get(i).canBuyBuilding(hertz)) {
                gui.getBtnBuildings().get(i).setEnabled(true);
            } else {
                gui.getBtnBuildings().get(i).setEnabled(false);
            }
        }

        for (int i = 0; i < gui.getBtnUpgrades().size(); i++) {
            int upgOwned = upgrades.get(i).getOwned();
            int max = upgrades.get(i).getMaxOwned();
            int buildOwned = buildings.get(i).getOwned();

            if (upgrades.get(i).canBuyUpgrade(hertz) && buildOwned >= upgrades.get(i).getRequirement() && upgOwned < max) {
                gui.getBtnUpgrades().get(i).setEnabled(true);
            } else if (max == upgOwned) {
                gui.getBtnUpgrades().get(i).setEnabled(false);
            } else {
                gui.getBtnUpgrades().get(i).setEnabled(false);
            }
        }
        for (int i = 0; i < gui.getBtnAchievements().size(); i++) {
            if (achievements.get(i).getOwned()) {
                gui.getBtnAchievements().get(i).setEnabled(true);
            } else {
                gui.getBtnAchievements().get(i).setEnabled(false);
            }
        }
    }

    /**
     * Get the username of the currently logged in user.
     * 
     * @return The username of the currently logged in user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Action listener for button presses
     */
    private class Listener implements ActionListener {
        /**
         * Getting the listeners with actionPerformed
         * 
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e) {
            // Hertz button
            if (e.getSource() == gui.getBtnHertz()) {
                clickCounter++;
                hertzClicked();
                ghzSound.play();
            }

            // Save button
            if (e.getSource() == gui.getBtnSave()) {
                saveGame();
                updateHighScore();
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

            // AboutUs button
            if (e.getSource() == gui.getBtnAboutUs()) {
                gui.setCard("3");
            }

            // AboutUs back button
            if (e.getSource() == gui.getBtnBackAboutUs()) {
                gui.setCard("1");
            }
            
            // FlyingGame back button
            if (e.getSource() == gui.getBtnBackFlyingGame()){
                gui.setCard("1");                
            } 

            // Stop Music button
            if (e.getSource() == gui.getBtnStopMusic()) {
                if (stopMusic) {
                    stopMusic = false;
                    backgroundMusic.loop();
                    gui.getBtnStopMusic().setText("Stop Music");

                } else {
                    stopMusic = true;
                    backgroundMusic.stop();
                    gui.getBtnStopMusic().setText("Play Music");
                }
            }  
                       

            // Building purcheses.
            for (int i = 0; i < gui.getBtnBuildings().size(); i++) {
                if (e.getSource() == gui.getBtnBuildings().get(i)) {
                    Building building = buildings.get(i);
                    if (hertz >= building.getPrice()) {
                        building.setOwned(building.getOwned() + 1);
                        if (buildings.get(i).canBuyBuilding(hertz)) {
                            hertz -= buildings.get(i).getPrice();
                        }
                    }
                }
            }
            // Upgrade purchases.
            for (int i = 0; i < gui.getBtnUpgrades().size(); i++) {
                if (e.getSource() == gui.getBtnUpgrades().get(i)) {
                    Upgrade upgrade = upgrades.get(i);
                    if (hertz >= upgrade.getPrice()) {
                        upgrade.setOwned(upgrade.getOwned() + 1);
                        upgrade.setRequirement(upgrade.getRequirement() * 10);
                        if (upgrades.get(i).canBuyUpgrade(hertz)) {
                            hertz -= upgrades.get(i).getPrice();
                        }
                    }
                }
            }

            if (e.getSource() == gui.getBtnOption1() || e.getSource() == gui.getBtnOption2() || e.getSource() == gui.getBtnOption3()) {
                randomBonous();
                gui.setCard("1");
            }
        }
    }
}
