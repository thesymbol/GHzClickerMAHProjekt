package ghzclicker;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
    private JButton btnSave = new JButton("");
    private JButton btnLoad = new JButton("");
    private JButton btnHighScore = new JButton("");
    private JTextArea taStatistics = new JTextArea();
    private JPanel pnlBuilding;
    private JPanel pnlUpgrade = new JPanel();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private ArrayList<JButton> btnBuildings;
    private ArrayList<JButton> btnUpgrades;
    private CardLayout cl = new CardLayout();
    private JPanel pnlGame = new JPanel();
    private JPanel pnlCont = new JPanel();
    private JPanel pnlHS;
    private HighScoreGUI pnlHighScore;
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
    public GameGUI(ArrayList<JButton> btnBuildings, ArrayList<JButton> btnUpgrades, ActionListener listener) {
        this.btnBuildings = btnBuildings;
        this.btnUpgrades = btnUpgrades;
        pnlBuilding = new JPanel(new GridLayout(btnBuildings.size(), 1));
        pnlUpgrade = new JPanel(new GridLayout(btnUpgrades.size(), 1));
        pnlHighScore = new HighScoreGUI(listener);
        pnlHS = pnlHighScore;
        // main panel
        frame.setPreferredSize(new Dimension(800, 850));
        pnlCont.setLayout(cl);
        frame.add(pnlCont);
        pnlCont.add(pnlGame, "1");
        pnlCont.add(pnlHS, "2");
        cl.show(pnlCont, "1");

        // setting locations and size.
        lblText.setBounds(50, 50, 500, 50);
        btnHertz.setBounds(150, 300, 150, 50);
        lblHertzPerSecond.setBounds(50, 75, 200, 50);
        btnSave.setBounds(95, 750, 100, 50);
        btnLoad.setBounds(260, 750, 100, 50);
        btnHighScore.setBounds(105, 675, 230, 50);
        taStatistics.setBounds(500, 600, 300, 220);

        // pnlBuilding.setBounds(500, 0, 300, btnBuildings.size() * 75);
        // pnlUpgrade.setBounds(500,0,300,btnUpgrades.size() * 75);

        tabbedPane.setBounds(500, 0, 300, btnBuildings.size() * 75);

        // Button Icons
        btnHertz.setIcon(iconHertz);
        btnHertz.setPressedIcon(iconHertzPressed);
        btnHertz.setOpaque(false);
        btnHertz.setContentAreaFilled(false);
        btnHertz.setBorderPainted(false);
        btnHertz.setFocusPainted(false);

        btnSave.setIcon(iconSave);
        btnSave.setPressedIcon(iconSavePressed);
        btnSave.setOpaque(false);
        btnSave.setContentAreaFilled(false);
        btnSave.setBorderPainted(false);
        btnSave.setFocusPainted(false);

        btnLoad.setIcon(iconLoad);
        btnLoad.setPressedIcon(iconLoadPressed);
        btnLoad.setOpaque(false);
        btnLoad.setContentAreaFilled(false);
        btnLoad.setBorderPainted(false);
        btnLoad.setFocusPainted(false);

        btnHighScore.setIcon(iconHighScore);
        btnHighScore.setPressedIcon(iconHighScorePressed);
        btnHighScore.setOpaque(false);
        btnHighScore.setContentAreaFilled(false);
        btnHighScore.setBorderPainted(false);
        btnHighScore.setFocusPainted(false);

        // adding the button and label to the frame.
        pnlGame.add(btnHertz);
        pnlGame.add(lblHertzPerSecond);
        pnlGame.setLayout(null);
        for (JButton btn : btnBuildings) {

            // Set listener for button
            btn.addActionListener(listener);

            // set size of button
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
        for (JButton btnUpg : btnUpgrades) {
            btnUpg.addActionListener(listener);

            btnUpg.setSize(new Dimension(200, 75));

            btnUpg.setVerticalTextPosition(JButton.CENTER);
            btnUpg.setHorizontalTextPosition(JButton.CENTER);

            btnUpg.setEnabled(false);

            btnUpg.setFont(new Font("Araial", Font.BOLD, 16));
            btnUpg.setForeground(Color.black);

            pnlUpgrade.add(btnUpg);
        }
        // Continues adding the button and label to the frame.
        // pnlGame.add(pnlBuilding);
        // pnlGame.add(pnlUpgrade);
        tabbedPane.add(pnlBuilding, "Buildings");
        tabbedPane.add(pnlUpgrade, "Upgrades");
        pnlGame.add(tabbedPane);

        pnlGame.add(btnSave);
        pnlGame.add(btnLoad);
        pnlGame.add(btnHighScore);
        taStatistics.setFont(new Font("Arial", Font.BOLD, 12));
        pnlGame.add(taStatistics);
        taStatistics.setEditable(false);
        taStatistics.setHighlighter(null); // disable highlight

        lblText.setFont(new Font("Arial", Font.BOLD, 16));
        pnlGame.add(lblText);

        // Adding listeners
        btnHertz.addActionListener(listener);
        btnSave.addActionListener(listener);
        btnLoad.addActionListener(listener);
        btnHighScore.addActionListener(listener);

        // Setting instructions for the frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        frame.dispose();
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

    public ArrayList<JButton> getBtnUpgrades() {
        return btnUpgrades;
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
     * @param i , which upgrade.
     * @param cost , the price of the upgrade.
     */
    public void updateUpgradeCost(int i, String cost) {
        btnUpgrades.get(i).setText(btnUpgrades.get(i).getName() + " " + cost);
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
        taStatistics.setText("Total buildings :" + statistics);
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
}
