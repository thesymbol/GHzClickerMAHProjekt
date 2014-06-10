package ghzclicker;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FlyingGUI extends JPanel {
    private FlyingGame FG;    
    private JPanel pnlFlyingGame = new BGPanel("res/wallpaper.png");
    private ImageIcon iconBack = new ImageIcon("res/btnBack.png");
    private ImageIcon iconBackPressed = new ImageIcon("res/btnBackPressed.png");
    private JButton btnBack = new JButton("");
    private ImageIcon iconVehicle = new ImageIcon("res/vehicle.png");
    private JLabel lblVehicle = new JLabel(iconVehicle);
    
    public FlyingGUI(ActionListener listener) {
        

        setBackground(Color.white);
        pnlFlyingGame.setPreferredSize(new Dimension(800, 553));
        add(pnlFlyingGame);
        pnlFlyingGame.setBackground(Color.white);
        pnlFlyingGame.setLayout(null);
        // The back button
        btnBack.setBounds(675, 10, 108, 60);
        btnBack.setIcon(iconBack);
        btnBack.setOpaque(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setPressedIcon(iconBackPressed);
        btnBack.addActionListener(listener);
        // The Vehicle       
        lblVehicle.setSize(50,50);
        

        
        pnlFlyingGame.add(btnBack);
        pnlFlyingGame.add(lblVehicle);

    }
    
    public void moveVehicle(int x, int y){
        lblVehicle.setLocation(x, y);
    }
    

    /**
     * Returns the back button
     * 
     * @return the back button
     */
    public JButton getBtnBack() {
        return btnBack;
    }
    
//    public key getKey(){
//        
//    }

}
