package ghzclicker;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MiniGame extends JPanel {
    private JPanel pnlMiniGame = new BGPanel("res/wallpaper.png");
    private JButton btnOption1 = new JButton("Option 1");
    private JButton btnOption2 = new JButton("Option 2");
    private JButton btnOption3 = new JButton("Option 3");
    private JLabel lblText1 = new JLabel("Pick one on of the options and gain a free somthing");

    public MiniGame(ActionListener listener) {
        setBackground(Color.white);
        pnlMiniGame.setPreferredSize(new Dimension(800, 553));
        add(pnlMiniGame);
        pnlMiniGame.setBackground(Color.white);
        pnlMiniGame.setLayout(null);

        lblText1.setBounds(100, 100, 700, 50);
        btnOption1.setBounds(200, 200, 100, 100);
        btnOption2.setBounds(350, 200, 100, 100);
        btnOption3.setBounds(500, 200, 100, 100);

        pnlMiniGame.add(lblText1);
        pnlMiniGame.add(btnOption1);
        pnlMiniGame.add(btnOption2);
        pnlMiniGame.add(btnOption3);

        btnOption1.addActionListener(listener);
        btnOption2.addActionListener(listener);
        btnOption3.addActionListener(listener);

    }

    public JButton getBtnOption1() {
        return btnOption1;
    }

    public JButton getBtnOption2() {
        return btnOption2;
    }

    public JButton getBtnOption3() {
        return btnOption3;
    }

   
}
