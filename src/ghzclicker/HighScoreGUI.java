package ghzclicker;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HighScoreGUI extends JPanel {
	private JPanel pnlHighScoreWindow = new JPanel();
	private JPanel pnlHighScoreText = new JPanel();
	private JScrollPane scrollPane = new JScrollPane(pnlHighScoreText);
	private JButton btnBack = new JButton("");
	private JLabel lblHighScore = new JLabel("HIGH SCORE");
	private ImageIcon iconBack = new ImageIcon("res/btnBack.png");
	private ImageIcon iconBackPressed = new ImageIcon("res/btnBackPressed.png");
	private ImageIcon iconHighScore = new ImageIcon("res/highscore.png");
	public HighScoreGUI(ActionListener listener){
		setBackground(Color.lightGray);
		pnlHighScoreWindow.setPreferredSize(new Dimension(800, 850));
		add(pnlHighScoreWindow);
		//the panel
		pnlHighScoreWindow.setBackground(Color.lightGray);
		pnlHighScoreWindow.setLayout(null);
		
		//Back button
		btnBack.setBounds(675, 10, 108, 60);
		btnBack.setIcon(iconBack);
		btnBack.setOpaque(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		btnBack.setFocusPainted(false);
		btnBack.setPressedIcon(iconBackPressed);
		btnBack.addActionListener(listener);
		
		lblHighScore.setBounds(240, 10, 320, 60);
		lblHighScore.setIcon(iconHighScore);
		pnlHighScoreWindow.add(lblHighScore);
		pnlHighScoreWindow.add(btnBack);
		pnlHighScoreWindow.add(scrollPane);
		scrollPane.setBounds(10, 90, 770, 700);
		pnlHighScoreText.setBackground(Color.DARK_GRAY);
		pnlHighScoreText.setBounds(0,0,660,800);
		pnlHighScoreText.setLayout(new GridLayout(50, 1));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//for testing
		for (int i = 1; i <= 50; i++) {
			pnlHighScoreText.add(createLabel("Test " + i, 20, Color.WHITE));
		}
		
		setVisible(true);
	}
	/*
	 * Returns a Label with specified information
	 * @return the created label
	 */
	private JLabel createLabel(String text, int size, Color color){
		JLabel label = new JLabel(text);
		Font font = new Font("Arial", Font.BOLD, size);
		label.setFont(font);
		label.setForeground(color);
		
		return label;
	}
	
	public JButton getBtnBack(){
		return btnBack;
	}
	
	public void setHighScore(){
		
	}
	
}
