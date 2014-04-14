package ghzclicker;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * 
 * @author Matte
 *
 *A Class that makes up the whole GUI
 */
public class MenuGUI extends JPanel{
	//Making buttons with buildings and hertz button and a Label for the hertz
	private JButton btnHertz = new JButton("hertz");
	private JLabel lblText = new JLabel("");
	private JLabel lblHertzPerSecond = new JLabel("Hertz per second : ");
	private JButton btnSave = new JButton("Save");
	private JButton btnLoad = new JButton("Load");
	private JPanel pnlBuilding;
	private ArrayList<JButton> btnBuildings;
/**
 *  A Constructor that is putting all the buttons into the GUI and sets the size of the labels, buttons etc.
 * @param btnBuildings , adding the buildings to the GUI
 * @param listener , adding listeners to the buttons.
 */
	public MenuGUI(ArrayList<JButton> btnBuildings, ActionListener listener){
		this.btnBuildings = btnBuildings;
		pnlBuilding = new JPanel(new GridLayout(btnBuildings.size(), 1));
		
		//main panel
		setPreferredSize(new Dimension(800,800));
		setLayout(null);
		
		//setting locations and size.
		lblText.setBounds(50, 50, 200, 50);
		btnHertz.setBounds(50, 125, 200, 50);
		lblHertzPerSecond.setBounds(50, 75, 200, 50);
		btnSave.setBounds(50,700,100,50);
		btnLoad.setBounds(200,700,100,50);
		
		pnlBuilding.setBounds(600, 0, 200, btnBuildings.size()*75);
		
		//adding the button and label to the frame.
		add(btnHertz);
		add(lblHertzPerSecond);
		for(JButton btn : btnBuildings) {
			btn.setSize(new Dimension(200, 75));
			pnlBuilding.add(btn);
		}
		add(pnlBuilding);
		add(btnSave);
		add(btnLoad);
		
		lblText.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblText);
		
		btnHertz.addActionListener(listener);
		btnSave.addActionListener(listener);
		btnLoad.addActionListener(listener);
	}
	
	/**
	 * get hertz button
	 */
	public JButton getBtnHertz() {
		return btnHertz;
	}
	
	/**
	 * get save btn
	 */
	public JButton getBtnSave() {
		return btnSave;
	}
	/**
	 * get load btn
	 */
	public JButton getBtnLoad() {
		return btnLoad;
	}
	
	/**
	 * get arraylist with buildings buttons
	 */
	public ArrayList<JButton> getBtnBuildings() {
		return btnBuildings;
	}
	
	public void updateJButtonCost(int i, int cost) {
		btnBuildings.get(i).setText(btnBuildings.get(i).getName() + " " + cost);
	}
	
	/** 
	 * MICHAEL TESTAR DETTA
	 */
	public void update(String hertz){		
		lblText.setText(hertz);
	}
	public void updateHertzPerSecond(String hertzPerSecond){
		lblHertzPerSecond.setText(hertzPerSecond);
	}
}
