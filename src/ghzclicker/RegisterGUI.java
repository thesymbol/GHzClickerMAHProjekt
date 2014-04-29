package ghzclicker;

import java.awt.Dimension;

import java.awt.event.ActionListener;

import javax.swing.*;

public class RegisterGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	//Making buttons to the panel.
	private JButton btnRegister = new JButton("Register");
	private JButton btnExit = new JButton("Exit");
	
	private JLabel lblUsername = new JLabel("Username :");
	private JLabel lblPassword = new JLabel("Password :");
	
	private JTextField tfUsername = new JTextField();
	private JTextField tfPassword = new JTextField();
	/**
	 * Constructor which puts sets upp the whole GUI with selected Dimension
	 * and adding all the variables to the JFrame.
	 */
	public RegisterGUI(ActionListener listener){
		setPreferredSize(new Dimension(500,200));
		setLayout(null);
		setName("Register");
		
		btnRegister.setBounds(75, 100, 100, 50);
		btnExit.setBounds(300, 100 , 100, 50);
		lblUsername.setBounds(50, 30 , 75, 25);
		lblPassword.setBounds(250 , 30 , 75 , 25);
		tfUsername.setBounds(125 , 30 , 120, 25);
		tfPassword.setBounds(325 , 30 , 120, 25);
		
		add(btnRegister);
		add(btnExit);
		add(lblUsername);
		add(lblPassword);
		add(tfUsername);
		add(tfPassword);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
