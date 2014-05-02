package ghzclicker;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Making a GUI for logins using a JFrame.
 * 
 * @author Mattias Holst
 * 
 */
public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	// Making buttons to the panel.
	private JButton btnLogin = new JButton("Login");
	private JButton btnRegister = new JButton("Register");
	private JButton btnExit = new JButton("Exit");

	private JLabel lblUsername = new JLabel("Username :");
	private JLabel lblPassword = new JLabel("Password :");

	private JTextField tfUsername = new JTextField();
	private JTextField tfPassword = new JTextField();

	/**
	 * Constructor which puts sets upp the whole GUI with selected Dimension and adding all the variables to the JFrame.
	 */
	public LoginGUI(ActionListener listener) {
		setPreferredSize(new Dimension(500, 200));
		setLayout(null);
		setName("Login");

		btnLogin.setBounds(50, 100, 100, 50);
		btnRegister.setBounds(200, 100, 100, 50);
		btnExit.setBounds(350, 100, 100, 50);
		lblUsername.setBounds(50, 30, 75, 25);
		lblPassword.setBounds(250, 30, 75, 25);
		tfUsername.setBounds(125, 30, 120, 25);
		tfPassword.setBounds(325, 30, 120, 25);

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

		btnLogin.addActionListener(listener);
		btnRegister.addActionListener(listener);
		btnExit.addActionListener(listener);

	}

	/**
	 * getting the Login Button
	 * 
	 * @return btnLogin
	 */
	public JButton getbtnLogin() {
		return btnLogin;
	}

	/**
	 * Getting the Register Button.
	 * 
	 * @return btnRegister
	 */
	public JButton getBtnRegister() {
		return btnRegister;
	}

	/**
	 * Getting the Exit Button.
	 * 
	 * @return btnExit
	 */
	public JButton getBtnExit() {
		return btnExit;
	}

	public String getUsername() {
		return tfUsername.getText();
	}

	public String getPassword() {
		return tfPassword.getText();
	}
}
