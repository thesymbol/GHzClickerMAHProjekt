package ghzclicker;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * A Class that makes up the whole GUI
 * 
 * @author Mattias Holst
 */
public class GameGUI {
    // Making buttons with buildings and hertz button and a Label for the hertz
    private JFrame frame = new JFrame("Ghz Clicker");
    private JButton btnHertz = new JButton("");
    private JLabel lblText = new JLabel("");
    private JLabel lblHertzPerSecond = new JLabel("Hertz per second : ");
    //private GraphicsText lblHertz = new GraphicsText("",45);
    //private GraphicsText lblHertzPerSecond = new GraphicsText("Hertz per second : ",25);
    private JButton btnSave = new JButton("");
    private JButton btnLoad = new JButton("");
    private JButton btnHighScore = new JButton("");
    private JButton stopMusic =  new JButton("Stop Music");
    private JButton btnAboutUs = new JButton("About US");
    private JTextArea taStatistics = new JTextArea();
    private JPanel pnlBuilding;
    private JPanel pnlUpgrade;
    private JPanel pnlStatistics = new JPanel();
    private JPanel pnlAchievements;    
    Insets oldInsets = UIManager.getInsets("TabbedPane.contentBorderInsets");
    private ArrayList<JButton> btnBuildings;
    private ArrayList<JButton> btnUpgrades;
    private ArrayList<JButton> btnAchievements;
    private CardLayout cl = new CardLayout();
    private JPanel pnlGame = new BGPanel("res/wallpaper.png");
    private JPanel pnlCont = new JPanel();
    private JPanel pnlHS;    
    private HighScoreGUI pnlHighScore;   
    private JPanel pnlAU;
    private AboutUs pnlAboutUs;
    private JPanel pnlMG; 
    private MiniGame pnlMiniGame;
    private JPanel pnlFG;
    private FlyingGUI pnlFlyingGame;
    private FlyingGUI flyingGUI;
    // Button icons
    private ImageIcon iconHertz = new ImageIcon("res/btnHertz.png");
    private ImageIcon iconHertzPressed = new ImageIcon("res/btnHertzPressed.png");
    private ImageIcon iconSave = new ImageIcon("res/btnSave.png");
    private ImageIcon iconSavePressed = new ImageIcon("res/btnSavePressed.png");
    private ImageIcon iconLoad = new ImageIcon("res/btnLoad.png");
    private ImageIcon iconLoadPressed = new ImageIcon("res/btnLoadPressed.png");
    private ImageIcon iconHighScore = new ImageIcon("res/btnHighScore.png");
    private ImageIcon iconHighScorePressed = new ImageIcon("res/btnHighScorePressed.png");

    /**
     * A Constructor that is putting all the buttons into the GUI and sets the size of the labels, buttons etc.
     * 
     * @param btnBuildings , adding the buildings to the GUI
     * @param listener , adding listeners to the buttons.
     */
    public GameGUI(ArrayList<JButton> btnBuildings, ArrayList<JButton> btnUpgrades, ArrayList<JButton> btnAchievements, ActionListener listener) {
        this.btnBuildings = btnBuildings;
        this.btnUpgrades = btnUpgrades;
        this.btnAchievements = btnAchievements;
        pnlBuilding = new JPanel(new GridLayout(btnBuildings.size(), 1));
        pnlUpgrade = new JPanel(new GridLayout(btnUpgrades.size(), 1));
        pnlAchievements = new JPanel(new GridLayout(10, 10));         
        taStatistics.setPreferredSize(new Dimension(280, 250));
        taStatistics.setOpaque(false);
        Insets oldInsets = UIManager.getInsets("TabbedPane.contentBorderInsets");
        // Bottom insets is 1 because the tabs are bottom aligned
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 1, 0));
        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setOpaque(false);
        UIManager.put("TabbedPane.contentBorderInsets", oldInsets);
        pnlStatistics.setOpaque(false);
        pnlAchievements.setOpaque(false);                
        pnlHighScore = new HighScoreGUI(listener);
        pnlMiniGame = new MiniGame(listener);
        pnlAboutUs = new AboutUs(listener);
        flyingGUI = new FlyingGUI(listener);
        pnlFlyingGame = flyingGUI;
        pnlHS = pnlHighScore;
        pnlAU = pnlAboutUs;
        pnlMG = pnlMiniGame;
        pnlFG = pnlFlyingGame;
        // Main panel
        frame.setPreferredSize(new Dimension(800, 553));
        frame.setResizable(false);
        pnlCont.setLayout(cl);
        frame.add(pnlCont);
        pnlCont.add(pnlGame, "1");
        pnlCont.add(pnlHS, "2");
        pnlCont.add(pnlAU, "3");
        pnlCont.add(pnlMG, "4");
        pnlCont.add(pnlFG, "5");
        cl.show(pnlCont, "1");
        

        // Setting locations and size.
        lblText.setBounds(50, 50, 500, 50);
        btnHertz.setBounds(175, 200, 150, 50);
        lblHertzPerSecond.setBounds(50, 75, 200, 50);
        btnSave.setBounds(125, 455, 100, 50);
        btnLoad.setBounds(280, 455, 100, 50);
        btnHighScore.setBounds(135, 380, 230, 50);
        pnlStatistics.setBounds(250, 0, 400, 220);
        stopMusic.setBounds(175, 300, 150, 50);
        btnAboutUs.setBounds(0, 455, 100, 50);

        tabbedPane.setBounds(500, 0, 300, btnBuildings.size() * 75);

        // Button Icons
        setButtonSettings(btnHertz, iconHertz, iconHertzPressed);
        setButtonSettings(btnSave, iconSave, iconSavePressed);
        setButtonSettings(btnLoad, iconLoad, iconLoadPressed);
        setButtonSettings(btnHighScore, iconHighScore, iconHighScorePressed);

        // Adding the button and label to the frame.
        pnlGame.add(btnHertz);
        pnlGame.add(lblHertzPerSecond);
        pnlGame.add(stopMusic);
        pnlGame.add(btnAboutUs);
        pnlGame.setLayout(null);

        // Adding btnUpgrades to btnUpg.
        for (JButton btn : btnUpgrades) {
            // Set listener for button
            btn.addActionListener(listener);

            // Set size of button
            btn.setSize(new Dimension(200, 75));

            // Position text over image
            btn.setVerticalTextPosition(JButton.CENTER);
            btn.setHorizontalTextPosition(JButton.CENTER);

            // Set to disabled in begining.
            btn.setEnabled(false);

            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setForeground(Color.black);

            pnlUpgrade.add(btn);
        }

        // Adding btnBuildings to btn.
        for (JButton btn : btnBuildings) {

            // Set listener for button
            btn.addActionListener(listener);

            // Set size of button
            btn.setSize(new Dimension(200, 75));

            // Position text over image
            btn.setVerticalTextPosition(JButton.CENTER);
            btn.setHorizontalTextPosition(JButton.CENTER);

            // Set to disabled in begining.
            btn.setEnabled(false);

            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setForeground(Color.black);

            pnlBuilding.add(btn);
        }
        for (JButton btn : btnAchievements) {

            // Set size of button
            btn.setSize(new Dimension(20, 20));

            // Position text over image
            btn.setVerticalTextPosition(JButton.CENTER);
            btn.setHorizontalTextPosition(JButton.CENTER);

            // Set to disabled in begining.
            btn.setEnabled(false);

            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setForeground(Color.black);

            pnlAchievements.add(btn);
        }
        

        // Continues adding the button and label to the frame.
        pnlStatistics.add(taStatistics);
        taStatistics.setFont(new Font("Arial", Font.BOLD, 12));
        taStatistics.setEditable(false);
        taStatistics.setHighlighter(null);
        
        tabbedPane.add(pnlBuilding, "Buildings");
        tabbedPane.add(pnlUpgrade, "Upgrades");
        tabbedPane.add(pnlStatistics, "Statistics");
        tabbedPane.add(pnlAchievements, "Achievements");
        pnlGame.add(tabbedPane);
        
        

        pnlGame.add(btnSave);
        pnlGame.add(btnLoad);
        pnlGame.add(btnHighScore);

        lblText.setFont(new Font("Arial", Font.BOLD, 16));
        pnlGame.add(lblText);

        // Adding listeners
        btnHertz.addActionListener(listener);
        btnSave.addActionListener(listener);
        btnLoad.addActionListener(listener);
        btnHighScore.addActionListener(listener);
        stopMusic.addActionListener(listener);
        btnAboutUs.addActionListener(listener);

        // Setting instructions for the frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Sets button settings and image when not pressed and when pressed.
     * 
     * @param button The JButton to be modified
     * @param icon The ImageIcon to use for non-pressed state
     * @param pressedIcon The ImageIcon to be used for pressed state
     */
    private void setButtonSettings(JButton button, ImageIcon icon, ImageIcon pressedIcon) {
        button.setIcon(icon);
        button.setPressedIcon(pressedIcon);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }
    
    /**
     * Get hertz button
     * 
     * @return the Hertz button.
     */
    public JButton getBtnHertz() {
        return btnHertz;
    }

    /**
     * Get save btn
     * 
     * @return the save button.
     */
    public JButton getBtnSave() {
        return btnSave;
    }

    /**
     * Get load btn
     * 
     * @return the load button.
     */
    public JButton getBtnLoad() {
        return btnLoad;
    }

    /**
     * Get arraylist with buildings buttons
     * 
     * @return the buildings arraylist button.
     */
    public ArrayList<JButton> getBtnBuildings() {
        return btnBuildings;
    }

    /**
     * Get the arraylist with the upgrades buttons.
     * 
     * @return the upgrades arraylist button.
     */
    public ArrayList<JButton> getBtnUpgrades() {
        return btnUpgrades;
    }
    public ArrayList<JButton> getBtnAchievements() {
        return btnAchievements;
    }

    /**
     * Returns the high score button
     * 
     * @return the high score button
     */
    public JButton getBtnHighScore() {
        return btnHighScore;
    }

    /**
     * Returns the high score gui's back button
     * 
     * @return the high score back button
     */
    public JButton getBtnBackHighScore() {
        return pnlHighScore.getBtnBack();
    }
    
    /**
     * Get AboutUs button
     * 
     * @return the AboutUs button.
     */    
    public JButton getBtnAboutUs(){
        return btnAboutUs;
    }
    
    /**
    * Returns the AboutUs gui's back button
    * 
    * @return the AboutUs back button
    */
    public JButton getBtnBackAboutUs() {
        return  pnlAboutUs.getBtnBack();
    }
    
    /**
     * Returns the FlyingGame gui's back button
     * 
     * @return the FlyingGame back button
     */
     public JButton getBtnBackFlyingGame() {
         return  pnlFlyingGame.getBtnBack();
     }
    
    /**
     * Return option button 1 on MiniGame GUI
     * 
     * @return Option button 1
     */
    public JButton getBtnOption1(){
        return pnlMiniGame.getBtnOption1();
    }
    
    /**
     * Return option button 2 on MiniGame GUI
     * 
     * @return Option button 2
     */
    public JButton getBtnOption2(){
        return pnlMiniGame.getBtnOption2();
    }
    
    /**
     * Return option button 3 on MiniGame GUI
     * 
     * @return Option button 3
     */
    public JButton getBtnOption3(){
        return pnlMiniGame.getBtnOption3();
    }
    
    /**
     * Returns the stop music button
     * 
     * @return the stop music button
     */
    public JButton getBtnStopMusic(){
        return stopMusic;
    }

    /**
     * Used to set the high score in the high score gui
     * 
     * @param highScore the arraylist holding the scores
     */
    public void setHighScore(String highScore) {
        pnlHighScore.setHighScore(highScore);
    }

    /**
     * Update the cost of the buildings.
     * 
     * @param i , which building.
     * @param cost , the price of the builing.
     */
    public void updateJButtonCost(int i, String cost) {
        btnBuildings.get(i).setText(btnBuildings.get(i).getName() + " " + cost);
    }

    /**
     * "Update" the cost of the upgrades (its the same all the time)
     * 
     * @param i , which upgrade.1
     * @param cost , the price of the upgrade.
     */
    public void updateUpgradeCost(int i, String cost) {
        btnUpgrades.get(i).setText(btnUpgrades.get(i).getName() + " " + cost);
    }
    
    public void upgradeMaxedOut(int upgradeId){
        btnUpgrades.get(upgradeId).setText(btnUpgrades.get(upgradeId).getName() + " is maxed");
    }

    /**
     * Update and sets your new hertz value all the time
     * 
     * @param hertz
     */
    public void update(String hertz) {
        lblText.setText(hertz);
    }

    /**
     * Update and sets your new HertzPerSecond all the time.
     * 
     * @param hertzPerSecond , variable with your hertz/sec.
     */
    public void updateHertzPerSecond(String hertzPerSecond) {
        lblHertzPerSecond.setText("Hertz per second: " + hertzPerSecond);
    }

    /**
     * Showing statistic of your current game.
     * 
     * @param statistics , shows the text from statistics variable
     */
    public void updateStatistics(String statistics) {
        taStatistics.setText(statistics);
    } 

    /**
     * Decide which GUI to show using cardlayout.
     * 
     * @param number Determine which window to show.
     */
    public void setCard(String number) {
        cl.show(pnlCont, number);
    }

    /**
     * Sets the frame's(window) visibility
     * 
     * @param status , true or false
     */
    public void setVisible(boolean status) {
        frame.setVisible(status);
    }

    /**
     * Shows error message in a JOptionPane
     * 
     * @param message The message to be displayed.
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, "[Error] " + message);
    }

    /**
     * sets the tooltip for the buildings
     * 
     * @param tooltipText The text to display in the tooltip
     * @param buildingId The id of the building in the btnBuildings array.
     */
    public void setToolTipBuildings(String tooltipText, int buildingId) {
        btnBuildings.get(buildingId).setToolTipText(tooltipText);
    }

    /**
     * sets the tooltip for the upgrades
     * 
     * @param tooltipText The text to display in the tooltip
     * @param upgradeId The id of the upgrade in the btnUpgrades array.
     */
    public void setToolTipUpgrades(String tooltipText, int upgradeId) {
        btnUpgrades.get(upgradeId).setToolTipText(tooltipText);
    }   
    
    public FlyingGUI getFlyingGUI(){        
        return flyingGUI;        
    }

    
}
