package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
	private LoginGUI logGui;

	public LoginController() {
		Listener listener = new Listener();
		LoginGUI logGui = new LoginGUI(listener);
	}

	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == logGui.getBtnRegister()) {

			}
			if (e.getSource() == logGui.getbtnLogin()) {

			}
			if(e.getSource() == logGui.getBtnExit()){
				
			}
		}
	}
}
