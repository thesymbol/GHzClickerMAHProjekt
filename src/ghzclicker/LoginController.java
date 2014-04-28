package ghzclicker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
	private LoginGUI logGui;
	
	public LoginController(){
		LoginGUI logGui = new LoginGUI();
	}
	
	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == logGui.btnRegister()) {			
				
			}
			if (e.getSource() == logGui.btnLogin()) {			
				
			}
		}
	}
}
