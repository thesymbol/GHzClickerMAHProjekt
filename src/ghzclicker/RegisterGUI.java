package ghzclicker;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * A class that will make up the RegisterGUI with the help of a Jframe as a window.
 * 
 * @author Mattias Holst
 */
public class RegisterGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    // Making buttons to the JFrame.
    private JButton btnRegister = new JButton("");
    private JButton btnCancel = new JButton("");
    // Labels to the textfields.
    private JLabel lblUsername = new JLabel("Username :");
    private JLabel lblPassword = new JLabel("Password :");
    // The textfields
    private JTextField tfUsername = new JTextField();
    private JPasswordField tfPassword = new JPasswordField();
    // Button icons
    private ImageIcon iconRegister = new ImageIcon("res/btnRegister.png");
    private ImageIcon iconRegisterPressed = new ImageIcon("res/btnRegisterPressed.png");
    private ImageIcon iconCancel = new ImageIcon("res/btnCancel.png");
    private ImageIcon iconCancelPressed = new ImageIcon("res/btnCancelPressed.png");
    private JPanel pnlRegister = new BGPanel("res/loginRegister.png");

    /**
     * Constructor which puts sets upp the whole GUI with selected Dimension and adding all the variables to the JFrame.
     * 
     * @param listener ActionListener to all the buttons.
     */
    public RegisterGUI(ActionListener listener) {
        pnlRegister.setPreferredSize(new Dimension(500, 200));
        pnlRegister.setLayout(null);
        setName("Register");
        setResizable(false);

        btnRegister.setBounds(75, 100, 150, 50);
        btnCancel.setBounds(300, 100, 120, 50);
        lblUsername.setBounds(50, 30, 75, 25);
        lblPassword.setBounds(250, 30, 75, 25);
        tfUsername.setBounds(125, 30, 120, 25);
        tfPassword.setBounds(325, 30, 120, 25);

        pnlRegister.add(btnRegister);
        pnlRegister.add(btnCancel);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 12));
        pnlRegister.add(lblUsername);
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        pnlRegister.add(lblPassword);
        pnlRegister.add(tfUsername);
        pnlRegister.add(tfPassword);

        // For icons
        setButtonSettings(btnRegister, iconRegister, iconRegisterPressed);
        setButtonSettings(btnCancel, iconCancel, iconCancelPressed);

        add(pnlRegister);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        btnRegister.addActionListener(listener);
        btnCancel.addActionListener(listener);
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
     * Getting the Register Button.
     * 
     * @return btnRegister
     */
    public JButton getBtnRegister() {
        return btnRegister;
    }

    /**
     * Getting the Exit button.
     * 
     * @return btnExit
     */
    public JButton getBtnCancel() {
        return btnCancel;
    }

    /**
     * Getting the Username.
     * 
     * @return tfUsername, the text in the username text field.
     */
    public String getUsername() {
        return tfUsername.getText();
    }

    /**
     * Getting the password.
     * 
     * @return tfPassword , the text in the password text field.
     */
    public String getPassword() {
        char[] pass = tfPassword.getPassword();
        String strPass = "";
        for (char value : pass) {
            strPass += value;
        }
        return strPass;
    }
}
