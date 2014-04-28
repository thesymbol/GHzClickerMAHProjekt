package ghzclicker;

import java.awt.Dimension;

import javax.swing.*;

public class LoginGUI extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Making buttons to the panel.
	private JButton btnLogin = new JButton("Login");
	private JButton btnRegister = new JButton("Register");
	private JButton btnExit = new JButton("Exit");
	
	private JLabel lblUsername = new JLabel("Username :");
	private JLabel lblPassword = new JLabel("Password :");
	
	private JTextField tfUsername = new JTextField();
	private JTextField tfPassword = new JTextField();
	
	public LoginGUI(){
		setPreferredSize(new Dimension(500, 200));
		setLayout(null);
		setName("Login");
		
		btnLogin.setBounds(50, 100, 100, 50);
		btnRegister.setBounds(200, 100, 100, 50);
		btnExit.setBounds(350, 100 , 100, 50);
		lblUsername.setBounds(50, 30 , 75, 25);
		lblPassword.setBounds(250 , 30 , 75 , 25);
		tfUsername.setBounds(125 , 30 , 120, 25);
		tfPassword.setBounds(325 , 30 , 120, 25);
		
		add(btnLogin);
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
	
	public JButton btnLogin(){
		return btnLogin;
	}
	public JButton btnRegister(){
		return btnRegister;
	}
	public JButton btnExit(){
		return btnExit;
	}
	
}
