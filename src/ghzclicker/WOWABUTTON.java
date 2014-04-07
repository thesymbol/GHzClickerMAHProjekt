package ghzclicker;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class WOWABUTTON extends JPanel implements ActionListener {
	private Controller controller;
	private JButton btnHertz = new JButton("hertz");
	
	
	public WOWABUTTON(Controller controller){
		this.controller=controller;
		setPreferredSize(new Dimension(300,300));
		setLayout(null);
		
		btnHertz.setBounds(50, 50, 200, 50);
		
		
		add(btnHertz);
		
		
		
		btnHertz.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() ==btnHertz) {
			controller.hertzClicked();
		}
	}
	

	
}
