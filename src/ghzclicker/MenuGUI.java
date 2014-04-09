package ghzclicker;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MenuGUI extends JPanel implements ActionListener {
	private String hertz;
	private Controller controller;
	
	//Making buttons with buildings and hertz button.
	private JButton btnHertz = new JButton("hertz");
	private JLabel lblText = new JLabel("");	
	private JButton btnRam = new JButton("RAM Minne");
	private JButton btnGraficCard = new JButton("Grafic card");
	private JButton btnProcessor = new JButton("Processor");
	private JButton btnHarddrive = new JButton("Hard drive");
	private JButton btnMotherBoard = new JButton("MotherBoard");
<<<<<<< HEAD
	private JButton btnPowerSupply = new JButton("Power Supply");
	private JLabel lblText = new JLabel("");
=======

>>>>>>> branch 'master' of ssh://git@github.com/thesymbol/GHzClickerMAHProjekt.git

	
	
	
	public MenuGUI(Controller controller){
		
		this.controller=controller;
		
		//main panel
		setPreferredSize(new Dimension(800,800));
		setLayout(null);
		
		//setting locations and size.
		
		lblText.setBounds(50, 50, 200, 50);
		btnHertz.setBounds(50, 100, 200, 50);
		btnRam.setBounds(600, 0, 200, 75);
		btnGraficCard.setBounds(600, 75, 200, 75);
		btnProcessor.setBounds(600, 150, 200, 75);
		btnHarddrive.setBounds(600 , 225, 200, 75);
		btnMotherBoard.setBounds(600, 300 , 200, 75);
		btnPowerSupply.setBounds(600 , 375 , 200 , 75);
		
		//adding the button and label to the frame.
		add(btnHertz);
		add(btnRam);
		add(btnHarddrive);
		add(btnPowerSupply);
		add(btnProcessor);
		add(btnGraficCard);
		add(btnMotherBoard);
		
		add(lblText);
		
		btnHertz.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		//Hertz button
		if (e.getSource() ==btnHertz) {
			controller.hertzClicked();
		}
		}
		
	
	
	/** 
	 * MICHAEL TESTAR DETTA
	 */
	public void uppdate(String hertz){		
		lblText.setText(hertz);
		
	}
	

	
}
