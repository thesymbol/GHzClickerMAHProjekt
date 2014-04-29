package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			}
			if (e.getSource() == logGUI.getbtnLogin()) {

			}
			if (e.getSource() == logGUI.getBtnExit()) {
				System.exit(0);
			}
		}
	}
}
