package ghzclicker;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MenyGUI extends JPanel implements ActionListener {
	private String hertz;
	private Controller controller;
	private JButton btnHertz = new JButton("hertz");
	private JLabel lblText = new JLabel("");
	
	
	public MenyGUI(Controller controller){
		
		this.controller=controller;
		
		//main panel
		setPreferredSize(new Dimension(800,800));
		setLayout(null);
		
		//setting bounds.
		lblText.setBounds(50, 50, 200, 50);
		btnHertz.setBounds(50, 100, 200, 50);
		
		//adding the button and label to the frame.
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
