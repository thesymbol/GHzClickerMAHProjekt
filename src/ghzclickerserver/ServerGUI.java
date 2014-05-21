package ghzclickerserver;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 * A GUI for the server to show the "logger", using a JFrame to build the GUI.
 * 
 * @author Mattias Holst
 */
public class ServerGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton btnExit = new JButton("Exit");
    private static JTextArea taLog = new JTextArea();
    private JScrollPane sp = new JScrollPane(taLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    /**
     * Constructor which builds up the whole GUI with sizes etc.
     * 
     * @param listener The ActionListener for the GUI
     */
    public ServerGUI(ActionListener listener) {
        setPreferredSize(new Dimension(800, 850));
        setName("ServerGUI");
        setLayout(null);
        btnExit.setBounds(0, 750, 800, 62);
        add(sp);
        sp.setBounds(0, 0, 785, 750);
        add(btnExit);
        taLog.setFont(new Font("Arail", Font.BOLD, 12));
        DefaultCaret caret = (DefaultCaret) taLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        taLog.setEditable(false);

        btnExit.addActionListener(listener);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Getting the exit button.
     * 
     * @return The exit button
     */
    public JButton getBtnExit() {
        return btnExit;
    }

    /**
     * Append to the Textarea that is representing the console output.
     * 
     * @param print The text to add to the console textarea.
     */
    public static void appendTaLog(String print) {
        taLog.append(print);
    }
}
