package ghzclicker;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * 
 * @author Mattias Holst, Michael Bergstrand , Marcus Orwén
 * 
 *         A class which handles all the logics to LoginGUI and RegisterGUI.
 * 
 */
public class LoginController {
    private LoginGUI logGUI;
    private Listener listener;
    private NetworkClient network;

    private RegisterGUI regGUI;

    /**
     * A constructor that makes new instances.
     * 
     * @param ip , insert server ip.
     */
    public LoginController(NetworkClient network) {
        listener = new Listener();
        logGUI = new LoginGUI(listener);
        regGUI = new RegisterGUI(listener);
        this.network = network;
    }

    /**
     * 
     * @author Mattias Holst, Marcus Orwén , Michael Bergstrand.
     * 
     *         A inner class which handles all the listeners to the buttons from the LoginGUI and RegisterGUI.
     * 
     */
    private class Listener implements ActionListener {
        @Override
        /**
         * A method used to determine which button is pressed and what will happend next.
         * @param e , 
         */
        public void actionPerformed(ActionEvent e) {
            // LoginGUI listeners.
            if (e.getSource() == logGUI.getBtnRegister()) {
                regGUI.setVisible(true);
            }
            if (e.getSource() == logGUI.getbtnLogin()) {
                if(!network.isClosed()) {
                    String username = logGUI.getUsername();
                    String password = logGUI.getPassword();
                    network.sendData("sendlogininfo");// send this first to notify that we will send the username and password next
                    network.sendData(username);
                    network.sendData(password);
                    try {
                        if (network.getData().equals("loginsuccessfull")) {
                            JOptionPane.showMessageDialog(null, "Successfully logged in");
                            logGUI.setVisible(false);
                            logGUI.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong username or password, please try again");
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
            if (e.getSource() == logGUI.getBtnExit()) {
                System.exit(0);
            }
            // RegisterGUI listeners.
            if (e.getSource() == regGUI.getBtnRegister()) {
                if(!network.isClosed()) {
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
            if (e.getSource() == regGUI.getBtnCancel()) {
                regGUI.setVisible(false);
                regGUI.dispose();
            }
        }
    }
}
