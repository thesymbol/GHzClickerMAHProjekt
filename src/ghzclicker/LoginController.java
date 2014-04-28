package ghzclicker;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
	private LoginGUI logGui;
	
	public LoginController(){
		Listener listener = new Listener();
		logGui = new LoginGUI(listener);
	}
	
	
	
	
	
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (e.getSource() == logGui.getBtnRegister()) {			
			
			}
			if (e.getSource() == logGui.getBtnLogin()) {			
				
			}
			if (e.getSource() == logGui.getBtnExit()) {			
				
			}
		}		
	}
}
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
