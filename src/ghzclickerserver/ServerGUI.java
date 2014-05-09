package ghzclickerserver;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
/**
 * A GUI for the server to show the "logger", using a JFrame to build the GUI.
 * 
 * @author Mattias Holst
 *
 */
public class ServerGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton btnBrowse = new JButton("Browse");
    private JTextArea taLog = new JTextArea();
    File file = null;
    BufferedReader reader = null;

    private Timer timer = null;
    
    /**
     * Constructor which builds up the whole GUI with sizes etc.
     */
    public ServerGUI(){
        setPreferredSize(new Dimension(800, 850));
        setName("ServerGUI");
        setLayout(null);
        btnBrowse.setBounds(550 , 50 , 90  , 30);
        taLog.setBounds(0, 1, 800 , 650);
        add(btnBrowse);
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
        return btnBrowse;
    }
}
