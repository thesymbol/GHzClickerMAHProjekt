package ghzclicker;

import java.awt.event.*;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FlyingGame {
    private Vehicle vehicle;
    private Random rand = new Random();
    private boolean gameRunning=true;
    private JPanel pnlFlyingGUI;
    private FlyingGUI flyingGUI;
    private double vlX;
    private double vlY;

    public FlyingGame(Vehicle vehicle, FlyingGUI flyingGUI) {
        this.vehicle=vehicle;
        this.flyingGUI=flyingGUI;
        

    }

    public void action() {
        while (gameRunning) {
            System.out.println("BALÖREHKSJDAFLJKDa");
            JOptionPane.showMessageDialog(null, "DOGDOG");
            flyingGUI.moveVehicle(vehicle.getX(), vehicle.getY());
            JOptionPane.showMessageDialog(null, "DOGDOG");
            
        }
    }

    public void actionUpp() {        
        vehicle.setY(vehicle.getY() -(int) vlY);
        vlY+=0.5;
    }

    public void actionDown() {
        vehicle.setY(vehicle.getY() +(int) vlY);
        vlY+=0.5;
    }

    public void actionRight() {
        vehicle.setX(vehicle.getX() + (int) vlX);
        vlX+=0.5;
    }

    public void actionLeft() {
        vehicle.setY(vehicle.getX() - (int) vlX);
        vlX+=0.5;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            vlY=0;
        } 
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            vlY=0;
        } 
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            vlX=0;
        } 
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            vlX=0;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            actionRight();
        } 
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            actionLeft();
        } 
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            actionUpp();
        } 
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            actionDown();
        }
    }

}
