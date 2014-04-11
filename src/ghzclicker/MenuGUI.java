package ghzclicker;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MenuGUI extends JPanel implements ActionListener {
	private String hertz;
	private Controller controller;
	
	//Making buttons with buildings and hertz button and a Label for the hertz
	private JButton btnHertz = new JButton("hertz");
	private JLabel lblText = new JLabel("");
	private JButton btnSave = new JButton("Save");
	private JButton btnLoad = new JButton("Load");
	private JPanel pnlBuilding;

	public MenuGUI(Controller controller, ArrayList<String> buildingNames, ArrayList<String> buildingImages){
		
		JButton[] buildingBtnArray = new JButton[buildingNames.size()];
		for(int i = 0; i < buildingNames.size(); i++) {
			buildingBtnArray[i] = new JButton(buildingNames.get(i), new ImageIcon(buildingImages.get(i)));
			buildingBtnArray[i].setVerticalTextPosition(JButton.CENTER);
			buildingBtnArray[i].setHorizontalTextPosition(JButton.CENTER);
			buildingBtnArray[i].setForeground(Color.MAGENTA);
			buildingBtnArray[i].setToolTipText(buildingNames.get(i));
		}
		pnlBuilding = new JPanel(new GridLayout(buildingBtnArray.length, 1));
		
		this.controller=controller;
		
		//main panel
		setPreferredSize(new Dimension(800,800));
		setLayout(null);
		
		//setting locations and size.
		lblText.setBounds(50, 50, 200, 50);
		btnHertz.setBounds(50, 100, 200, 50);
		btnSave.setBounds(50,700,100,50);
		btnLoad.setBounds(200,700,100,50);
		
		pnlBuilding.setBounds(600, 0, 200, buildingBtnArray.length*75);
		
		//adding the button and label to the frame.
		add(btnHertz);
		for(JButton btn : buildingBtnArray) {
			btn.setSize(new Dimension(200, 75));
			pnlBuilding.add(btn);
		}
		add(pnlBuilding);
		add(btnSave);
		add(btnLoad);
		
		lblText.setFont(new Font("Serif", Font.BOLD, 16));
		add(lblText);
		
		btnHertz.addActionListener(this);
		btnSave.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		//Hertz button
		if (e.getSource() ==btnHertz) {
			controller.hertzClicked();
		}
		
		if(e.getSource() == btnSave){
			controller.saveGame();
		}
	}
		
	
	
	/** 
	 * MICHAEL TESTAR DETTA
	 */
	public void update(String hertz){		
		lblText.setText(hertz);
		
	}
	

	
}
