package ghzclicker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author Matte
 * 
 *         A Class that makes up the whole GUI
 */
public class GameGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	// Making buttons with buildings and hertz button and a Label for the hertz
	private JButton btnHertz = new JButton("hertz");
	private JLabel lblText = new JLabel("");
	private JLabel lblHertzPerSecond = new JLabel("Hertz per second : ");
	private JButton btnSave = new JButton("Save");
	private JButton btnLoad = new JButton("Load");
	private JTextArea taStatistics = new JTextArea();
	private JPanel pnlBuilding;
	private ArrayList<JButton> btnBuildings;

	/**
	 * A Constructor that is putting all the buttons into the GUI and sets the size of the labels, buttons etc.
	 * 
	 * @param btnBuildings , adding the buildings to the GUI
	 * @param listener , adding listeners to the buttons.
	 */
	public GameGUI(ArrayList<JButton> btnBuildings, ActionListener listener) {
		this.btnBuildings = btnBuildings;
		pnlBuilding = new JPanel(new GridLayout(btnBuildings.size(), 1));

		// main panel
		setPreferredSize(new Dimension(800, 800));
		setLayout(null);

		setName("GHz Clicker");

		// setting locations and size.
		lblText.setBounds(50, 50, 500, 50);
		btnHertz.setBounds(50, 125, 200, 50);
		lblHertzPerSecond.setBounds(50, 75, 200, 50);
		btnSave.setBounds(50, 700, 100, 50);
		btnLoad.setBounds(200, 700, 100, 50);
		taStatistics.setBounds(500, 600, 300, 200);

		pnlBuilding.setBounds(500, 0, 300, btnBuildings.size() * 75);

		// adding the button and label to the frame.
		add(btnHertz);
		add(lblHertzPerSecond);
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

			btn.setFont(new Font("helvetica", Font.BOLD, 16));
			btn.setForeground(Color.black);

			pnlBuilding.add(btn);
		}
		add(pnlBuilding);
		add(btnSave);
		add(btnLoad);
		add(taStatistics);
		taStatistics.setEditable(false);

		lblText.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblText);

		btnHertz.addActionListener(listener);
		btnSave.addActionListener(listener);
		btnLoad.addActionListener(listener);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * get hertz button
	 * 
	 * @return the Hertz button.
	 */
	public JButton getBtnHertz() {
		return btnHertz;
	}

	/**
	 * get save btn
	 * 
	 * @return the save button.
	 */
	public JButton getBtnSave() {
		return btnSave;
	}

	/**
	 * get load btn
	 * 
	 * @return the load button.
	 */
	public JButton getBtnLoad() {
		return btnLoad;
	}

	/**
	 * get arraylist with buildings buttons
	 * 
	 * @return the buildings arraylist button.
	 */
	public ArrayList<JButton> getBtnBuildings() {
		return btnBuildings;
	}

	/**
	 * Update the cost of the buildings.
	 * 
	 * @param i , which building.
	 * @param cost , the price of the builing.
	 */
	public void updateJButtonCost(int i, long cost) {
		btnBuildings.get(i).setText(btnBuildings.get(i).getName() + " " + cost);
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

}