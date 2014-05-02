package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

public class LoginController {
    private LoginGUI logGUI;
    private Listener listener;

    private RegisterGUI regGUI;

    private String serverIp;

    public LoginController(String ip) {
        listener = new Listener();
        logGUI = new LoginGUI(listener);
        regGUI = new RegisterGUI(listener);

        this.serverIp = ip;

    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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
            }
        }
    }
}
