package ghzclicker;

import javax.swing.JFrame;


public class Main {
	public static void main(String[] args) {		
		Controller controller = new Controller();	
		MenuGUI button = new MenuGUI(controller);
//		Michaelssaker MS = new Michaelssaker(controller,button);
		
		JFrame frame1 = new JFrame("GHz Clicker");
		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.add(button);
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
}
