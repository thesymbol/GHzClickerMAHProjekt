package ghzclickerserver;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
/**
 * A GUI for the server to show the "logger", using a JFrame to build the GUI.
 * 
 * @author Mattias Holst
 *
 */
public class ServerGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton btnExit = new JButton("Exit");
    private JTextArea taLog = new JTextArea();
    
    /**
     * Constructor which builds up the whole GUI with sizes etc.
     */
    public ServerGUI(){
        setPreferredSize(new Dimension(800, 850));
        setName("ServerGUI");
        setLayout(null);
        btnExit.setBounds(0 , 750 , 800  , 62);
        taLog.setBounds(0, 0, 800 , 750);
        add(btnExit);
        taLog.setFont(new Font("Arail" , Font.BOLD , 12));
        add(taLog);
        taLog.setEditable(false);
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Getting the exit button.
     * @return btnExit
     */
    public JButton getBtnExit(){
        return btnExit;
    }
}