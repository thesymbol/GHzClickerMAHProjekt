package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

public class LoginController {
	private LoginGUI logGUI;
	private LoginListener listener;

	private RegisterGUI regGUI;

	private String serverIp;

	public LoginController(String ip) {
		listener = new LoginListener();
		logGUI = new LoginGUI(listener);
		regGUI = new RegisterGUI(listener);

		this.serverIp = ip;

	}

	private class LoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == logGUI.getBtnRegister()) {
				// RegisterGUI regGUI = new RegisterGUI(listener);
				RegisterGUI regGUI = new RegisterGUI(listener);
			}
			if (e.getSource() == logGUI.getbtnLogin()) {

				network.sendData(username);
				network.senData(password);
				if (username && password == true) {
					JOptionPane.showMessageDialog(null, "Successfully logged in");
					logGUI.setVisible(false);
					logGUI.dispose();
				} else {
					JOptionPane.showMessageDialog(null,
							"Wrong username or password, please try again");

					try {
						String username = logGUI.getUsername();
						String password = logGUI.getPassword();
						NetworkClient network = new NetworkClient(serverIp);
						network.sendData("sendlogininfo");// send this first to
															// notify that we
															// will send the
															// username and
															// password next
						network.sendData(username);
						network.sendData(password);
						if (network.getData().equals("test")
								&& network.getData().equals("test2")) {
							JOptionPane.showMessageDialog(null,
									"Successfully logged in");
							logGUI.setVisible(false);
							logGUI.dispose();
						} else {
							JOptionPane
									.showMessageDialog(null,
											"Wrong username or password, please try again");
						}
						network.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (e.getSource() == logGUI.getBtnExit()) {
					System.exit(0);
				}
			}
		}
	}
}
