package ghzclicker;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Making a GUI for logins using a JFrame.
 * 
 * @author Mattias Holst
 */
public class LoginGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    // Making buttons to the panel.
    private JButton btnLogin = new JButton("");
    private JButton btnRegister = new JButton("");
    private JButton btnExit = new JButton("");
    private JLabel lblUsername = new JLabel("Username :");
    private JLabel lblPassword = new JLabel("Password :");
    private JTextField tfUsername = new JTextField();
    private JPasswordField tfPassword = new JPasswordField();
    // Button icons
    private ImageIcon iconLogin = new ImageIcon("res/btnLogin.png");
    private ImageIcon iconLoginPressed = new ImageIcon("res/btnLoginPressed.png");
    private ImageIcon iconRegister = new ImageIcon("res/btnRegister.png");
    private ImageIcon iconRegisterPressed = new ImageIcon("res/btnRegisterPressed.png");
    private ImageIcon iconExit = new ImageIcon("res/btnExit.png");
    private ImageIcon iconExitPressed = new ImageIcon("res/btnExitPressed.png");
    private JPanel pnlLogin = new BGPanel("res/loginRegister.png");

    /**
     * Constructor which puts sets upp the whole GUI with selected Dimension and adding all the variables to the JFrame.
     * 
     * @param listener ActionListener for the buttons.
     */
    public LoginGUI(ActionListener listener) {
        setName("Login");
        setResizable(false);

        pnlLogin.setLayout(null);
        pnlLogin.setPreferredSize(new Dimension(500, 200));

        btnLogin.setBounds(50, 100, 100, 50);
        btnRegister.setBounds(180, 100, 150, 50);
        btnExit.setBounds(350, 100, 100, 50);
        lblUsername.setBounds(50, 30, 75, 25);
        lblPassword.setBounds(250, 30, 75, 25);
        tfUsername.setBounds(125, 30, 120, 25);
        tfPassword.setBounds(325, 30, 120, 25);

        pnlLogin.add(btnLogin);
        pnlLogin.add(btnRegister);
        pnlLogin.add(btnExit);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 12));
        pnlLogin.add(lblUsername);
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        pnlLogin.add(lblPassword);
        pnlLogin.add(tfUsername);
        pnlLogin.add(tfPassword);

        // For icons
        setButtonSettings(btnLogin, iconLogin, iconLoginPressed);
        setButtonSettings(btnRegister, iconRegister, iconRegisterPressed);
        setButtonSettings(btnExit, iconExit, iconExitPressed);

        add(pnlLogin);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        btnLogin.addActionListener(listener);
        btnRegister.addActionListener(listener);
        btnExit.addActionListener(listener);
    }

    /**
     * Sets button settings and image when not pressed and when pressed.
     * 
     * @param button The JButton to be modified
     * @param icon The ImageIcon to use for non-pressed state
     * @param pressedIcon The ImageIcon to be used for pressed state
     */
    private void setButtonSettings(JButton button, ImageIcon icon, ImageIcon pressedIcon) {
        button.setIcon(icon);
        button.setPressedIcon(pressedIcon);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
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
