package ghzclicker;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class WOWABUTTON extends JPanel implements ActionListener {
	private String hertz;
	private Controller controller;
	private JButton btnHertz = new JButton("hertz");
	private JLabel lblText = new JLabel("");
	
	
	public WOWABUTTON(Controller controller){
		this.controller=controller;
		setPreferredSize(new Dimension(300,300));
		setLayout(null);
		
		lblText.setBounds(50, 50, 200, 50);
		btnHertz.setBounds(50, 100, 200, 50);
		
		
		add(btnHertz);
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
