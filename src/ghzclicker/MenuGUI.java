package ghzclicker;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MenyGUI extends JPanel implements ActionListener {
	private String hertz;
	private Controller controller;
	
	//Making buttons with buildings and hertz button.
	private JButton btnHertz = new JButton("hertz");
	private JButton btnRam = new JButton("RAM Minne");
	private JButton btnGraficCard = new JButton("Grafic card");
	private JButton btnProcessor = new JButton("Processor");
	private JButton btnHarddrive = new JButton("Hard drive");
	private JButton btnMotherBoard = new JButton("MotherBoard");
	private JLabel lblText = new JLabel("");

	
	
	
	public MenyGUI(Controller controller){
		
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
		
		//adding the button and label to the frame.
		add(btnHertz);
		add(btnRam);
		add(btnGraficCard);
		add(btnProcessor);
		add(btnHarddrive);
		add(btnMotherBoard);
		add(lblText);
		
		btnHertz.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
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
