package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class LoginController {
	private LoginGUI logGUI;
	private LoginListener listener;

	public LoginController() {
		listener = new LoginListener();
		logGUI = new LoginGUI(listener);
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
				if(username && password == true){
					JOptionPane.showMessageDialog(null, "Successfully logged in");
					logGUI.setVisible(false);
					logGUI.dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, "Wrong username or password, please try again");
				}
			}
			if (e.getSource() == logGUI.getBtnExit()) {
				System.exit(0);
			}
		}
	}
}
