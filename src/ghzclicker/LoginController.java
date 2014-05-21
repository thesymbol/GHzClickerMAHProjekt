package ghzclicker;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * A class which handles all the logics to LoginGUI and RegisterGUI.
 * 
 * @author Mattias Holst, Michael Bergstrand , Marcus Orwén
 */
public class LoginController {
    private LoginGUI logGUI;
    private Listener listener;
    private NetworkClient network;
    private Controller controller;
    private RegisterGUI regGUI;
    private final static Logger logger = ClientLogger.getLogger();

    /**
     * A constructor that makes new instances.
     * 
     * @param network The network client instance
     * @param controller Working together with the Controller.
     */
    public LoginController(NetworkClient network, Controller controller) {
        listener = new Listener();
        logGUI = new LoginGUI(listener);
        regGUI = new RegisterGUI(listener);
        this.network = network;
        this.controller = controller;
    }

    /**
     * Logs user in to game with specified username and password.
     * 
     * @param username The username
     * @param password The password
     */
    private void login(String username, String password) {
        if (!network.isClosed()) {
            network.sendData("sendlogininfo");// send this first to notify that we will send the username and password next
            network.sendData(username);
            network.sendData(password);
            try {
                String ret = network.getData();
                if (ret.equals("loginsuccessfull")) {
                    JOptionPane.showMessageDialog(null, "Successfully logged in");
                    logGUI.setVisible(false);
                    logGUI.dispose();
                    controller.loadGameServer();
                    controller.guiSetVisibel(true);
                    controller.setUsernamePassword(username, password);
                } else if (ret.equals("alreadylogged")) {
                    JOptionPane.showMessageDialog(null, "User is already logged in to another session");
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong username or password, please try again");
                }
            } catch (HeadlessException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            logger.severe("Server is not online or you are not connected to the internet");
            logGUI.showErrorMessage("Server is not online or you are not connected to the internet");
        }
    }

    /**
     * A inner class which handles all the listeners to the buttons from the LoginGUI and RegisterGUI.
     * 
     * @author Mattias Holst, Marcus Orwén , Michael Bergstrand.
     */
    private class Listener implements ActionListener {
        /**
         * A method used to determine which button is pressed and what will happend next.
         * 
         * @param e The event
         */
        public void actionPerformed(ActionEvent e) {
            // Register button on login screen
            if (e.getSource() == logGUI.getBtnRegister()) {
                regGUI.setVisible(true);
            }
            // Login button
            if (e.getSource() == logGUI.getbtnLogin()) {
                login(logGUI.getUsername(), logGUI.getPassword());
            }
            // Exit button
            if (e.getSource() == logGUI.getBtnExit()) {
                System.exit(0);
            }
            // Register button on register screen
            if (e.getSource() == regGUI.getBtnRegister()) {
                if (!network.isClosed()) {
                    String username = regGUI.getUsername();
                    String password = regGUI.getPassword();
                    network.sendData("sendregdata");// send this first to notify that we will send the username and password next
                    network.sendData(username);
                    network.sendData(password);
                    try {
                        if (network.getData().equals("regsuccessfull")) {
                            JOptionPane.showMessageDialog(null, "Your account is now created!");
                            regGUI.setVisible(false);
                            regGUI.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "This username already exists. Please try another one.");
                        }
                    } catch (HeadlessException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    logGUI.showErrorMessage("Server is not online or you are not connected to the internet");
                }
            }
            // Cancel button
            if (e.getSource() == regGUI.getBtnCancel()) {
                regGUI.setVisible(false);
                regGUI.dispose();
            }
        }
    }
}
