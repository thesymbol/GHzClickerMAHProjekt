package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;


/**
 * 
 * @author Mattias Holst, Michael Bergstrand , Marcus Orwén
 * 
 *  A class which handles all the logics to LoginGUI and RegisterGUI.
 *
 */
public class LoginController {
    private LoginGUI logGUI;
    private Listener listener;

    private RegisterGUI regGUI;

    private String serverIp;
    /**
     * A constructor that makes new instances.
     * @param ip , insert server ip.
     */
    public LoginController(String ip) {
        listener = new Listener();
        logGUI = new LoginGUI(listener);
        regGUI = new RegisterGUI(listener);

        this.serverIp = ip;

    }
    /**
     * 
     * @author Mattias Holst, Marcus Orwén , Michael Bergstrand.
     * 
     * A inner class which handles all the listeners to the buttons from the LoginGUI and RegisterGUI.
     *
     */
    private class Listener implements ActionListener {
        @Override
        /**
         * A method used to determine which button is pressed and what will happend next.
         * @param e , 
         */
        public void actionPerformed(ActionEvent e) {
            //LoginGUI listeners.
            if (e.getSource() == logGUI.getBtnRegister()) {
                regGUI.setVisible(true);
            }
            if (e.getSource() == logGUI.getbtnLogin()) {
                try {
                    String username = logGUI.getUsername();
                    String password = logGUI.getPassword();
                    NetworkClient network = new NetworkClient(serverIp);
                    network.sendData("sendlogininfo");// send this first to notify that we will send the username and password next
                    network.sendData(username);
                    network.sendData(password);
                    if (network.getData().equals("loginsuccessfull")) {
                        JOptionPane.showMessageDialog(null, "Successfully logged in");
                        logGUI.setVisible(false);
                        logGUI.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong username or password, please try again");
                    }
                    network.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == logGUI.getBtnExit()) {
                System.exit(0);
            }
            //RegisterGUI listeners.
            if (e.getSource() == regGUI.getBtnRegister()) {
                try {
                    String username = regGUI.getUsername();
                    String password = regGUI.getPassword();
                    NetworkClient network = new NetworkClient(serverIp);
                    network.sendData("sendregdata");// send this first to notify that we will send the username and password next
                    network.sendData(username);
                    network.sendData(password);
                    if (network.getData().equals("regsuccessfull")) {
                        JOptionPane.showMessageDialog(null, "Your account is now created!");
                        regGUI.setVisible(false);
                        regGUI.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "This username already exists. Please try another one.");
                    }
                    network.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == regGUI.getBtnCancel()) {
                regGUI.setVisible(false);
                regGUI.dispose();
            }
        }
    }
}
