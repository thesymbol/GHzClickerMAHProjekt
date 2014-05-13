package ghzclicker;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A class that builds the GUI used for the High Score
 * 
 * @author Viktor Saltarski
 * 
 */
public class HighScoreGUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel pnlHighScoreWindow = new JPanel();
    private JTextArea txtHighScore = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(txtHighScore);
    private JButton btnBack = new JButton("");
    private JLabel lblHighScore = new JLabel("HIGH SCORE");
    private ImageIcon iconBack = new ImageIcon("res/btnBack.png");
    private ImageIcon iconBackPressed = new ImageIcon("res/btnBackPressed.png");
    private ImageIcon iconHighScore = new ImageIcon("res/highscore.png");

    public HighScoreGUI(ActionListener listener) {
        setBackground(Color.lightGray);
        pnlHighScoreWindow.setPreferredSize(new Dimension(800, 850));
        add(pnlHighScoreWindow);
        // the panel
        pnlHighScoreWindow.setBackground(Color.lightGray);
        pnlHighScoreWindow.setLayout(null);

        // Back button
        btnBack.setBounds(675, 10, 108, 60);
        btnBack.setIcon(iconBack);
        btnBack.setOpaque(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setPressedIcon(iconBackPressed);
        btnBack.addActionListener(listener);

        // Highscore icon
        lblHighScore.setBounds(240, 10, 320, 60);
        lblHighScore.setIcon(iconHighScore);
        // mainPanel
        pnlHighScoreWindow.add(lblHighScore);
        pnlHighScoreWindow.add(btnBack);
        pnlHighScoreWindow.add(scrollPane);

        // Txt
        scrollPane.setBounds(10, 90, 770, 700);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setViewportBorder(null);

        txtHighScore.setEditable(false);
        txtHighScore.setBackground(Color.darkGray);
        txtHighScore.setFont(new Font("Arial", Font.BOLD, 30));
        txtHighScore.setForeground(Color.WHITE);

        setVisible(true);
    }

    /**
     * Returns the back button
     * 
     * @return the back button
     */
    public JButton getBtnBack() {
        return btnBack;
    }

    /**
     * Used to set the high score(update high score)
     */
    public void setHighScore(String highScore) {
        txtHighScore.setText(highScore);
    }

}
