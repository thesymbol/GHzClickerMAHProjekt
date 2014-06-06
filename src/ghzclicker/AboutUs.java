package ghzclicker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AboutUs extends JPanel {
    private JPanel pnlAboutUs = new BGPanel("res/wallpaper.png");
    private ImageIcon iconBack = new ImageIcon("res/btnBack.png");
    private ImageIcon iconBackPressed = new ImageIcon("res/btnBackPressed.png");
    private ImageIcon iconMichael =new ImageIcon("res/michael bild.png");
    private ImageIcon iconMattias =new ImageIcon("res/Mattias bild.png");
    private ImageIcon iconViktor =new ImageIcon("res/Viktor bild.png");
    private ImageIcon iconMarcus =new ImageIcon("res/Marcus bild.png");
    private ImageIcon iconWung =new ImageIcon("res/Wung bild.png");
    private ImageIcon iconEhsan =new ImageIcon("res/Ehsan bild.png");
    //the text
    private JLabel lblMichael = new JLabel("Michael Bergstrand");
    private JLabel lblMattias = new JLabel("Mattias Holst");
    private JLabel lblViktor= new JLabel("Viktor Saltarski");
    private JLabel lblMarcus = new JLabel("Marcus Orwen");
    private JLabel lblWung = new JLabel("Wung Lung Chung");
    private JLabel lblEhsan = new JLabel("Ehsan");
    private JLabel lblText = new JLabel("We are a bunch of students who decided to make a game for our first project at Malmö University.");
    private JLabel lblText2 = new JLabel("All pictures are made by Michael in paint. Support his dream to beacome a paintist at *Kickstartar.com/MichaelThePaintist* thx");
    //the pictures
    private JLabel lblBildMichael = new JLabel(iconMichael);
    private JLabel lblBildMattias = new JLabel(iconMattias);
    private JLabel lblBildViktor= new JLabel(iconViktor);
    private JLabel lblBildMarcus = new JLabel(iconMarcus);
    private JLabel lblBildWung = new JLabel(iconWung);
    private JLabel lblBildEhsan = new JLabel(iconEhsan);
    
    private JButton btnBack = new JButton("");
    
    /**
     * Constructor which will build the AboutUs GUI 
     *
     * @param listener Adding ActionListeners to the buttons 
     */    
    public AboutUs(ActionListener listener){
        setBackground(Color.white);
        pnlAboutUs.setPreferredSize(new Dimension(800, 553));
        add(pnlAboutUs);
        pnlAboutUs.setBackground(Color.white);
        pnlAboutUs.setLayout(null);
        lblText2.setForeground(Color.green); 
        //The back button       
        btnBack.setBounds(675, 10, 108, 60);
        btnBack.setIcon(iconBack);
        btnBack.setOpaque(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setPressedIcon(iconBackPressed);
        btnBack.addActionListener(listener);
        //where the picture are placed
        lblBildMichael.setBounds(50,100,120,150);
        lblBildMattias.setBounds(280,100,120,150);
        lblBildViktor.setBounds(510,100,120,150);
        lblBildMarcus.setBounds(50,300,120,150);
        lblBildWung.setBounds(280,300,120,150);
        lblBildEhsan.setBounds(510,300,120,150);
        //where the names and text are placed 
        lblMichael.setBounds(50,250,200,50);
        lblMattias.setBounds(280,250,200,50);
        lblViktor.setBounds(510,250,200,50);
        lblMarcus.setBounds(50,450,200,50);
        lblWung.setBounds(280,450,200,50);
        lblEhsan.setBounds(510,450,200,50);
        lblText.setBounds(50,50,700,50);
        lblText2.setBounds(50,470,1000,50);
        
        
        //adds everything to the panel
        pnlAboutUs.add(btnBack);
        pnlAboutUs.add(lblBildMichael);
        pnlAboutUs.add(lblBildMattias);
        pnlAboutUs.add(lblBildViktor);
        pnlAboutUs.add(lblBildMarcus);
        pnlAboutUs.add(lblBildWung);
        pnlAboutUs.add(lblBildEhsan);
        pnlAboutUs.add(lblMichael);
        pnlAboutUs.add(lblMattias);
        pnlAboutUs.add(lblViktor);
        pnlAboutUs.add(lblMarcus);
        pnlAboutUs.add(lblWung);
        pnlAboutUs.add(lblEhsan);
        pnlAboutUs.add(lblText);
        pnlAboutUs.add(lblText2);
        
        
        
    }
    
    /**
     * Returns the back button
     * 
     * @return the back button
     */    
    public JButton getBtnBack() {
        return btnBack;
    }

}

