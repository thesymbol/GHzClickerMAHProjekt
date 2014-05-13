package ghzclicker;

import java.awt.Dimension;
import java.awt.Font;
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
    private JPasswordField tfPassword = new JPasswordField();

    /**
     * Constructor which puts sets upp the whole GUI with selected Dimension and adding all the variables to the JFrame.
     * 
     * @param listener ActionListener for the buttons.
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
        lblUsername.setFont(new Font("Arial", Font.BOLD, 12));
        add(lblUsername);
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
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

    /**
     * Getting the Username (in String format) from the textfield
     * 
     * @return username of the textfield
     */
    public String getUsername() {
        return tfUsername.getText();
    }

    /**
     * Getting the Password (in String format) from the textfield
     * 
     * @return password of the textfield
     */
    public String getPassword() {
        char[] pass = tfPassword.getPassword();
        String strPass = "";
        for (char value : pass) {
            strPass += value;
        }
        return strPass;
    }

    /**
     * Shows error message in a JOptionPane
     * 
     * @param message The message to be displayed.
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, "[Error] " + message);
    }
}
