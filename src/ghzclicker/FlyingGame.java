package ghzclicker;

import java.awt.event.*;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FlyingGame {
    private Vehicle vehicle;
    private Random rand = new Random();
    private boolean gameRunning = true;
    private JPanel pnlFlyingGUI;
    private FlyingGUI flyingGUI;
    private double vlX;
    private double vlY;
    private boolean upp;
    private boolean down;
    private boolean right;
    private boolean left;

    public FlyingGame(Vehicle vehicle, FlyingGUI flyingGUI) {
        this.vehicle = vehicle;
        this.flyingGUI = flyingGUI;

    }

    public void action() {
        while (gameRunning) {
            flyingGUI.moveVehicle(vehicle.getX(), vehicle.getY());
            actionUpp();
            actionDown();
            actionRight();
            actionLeft();
        }
    }

    public void actionUpp() {
        if (upp) {
            vehicle.setY(vehicle.getY() - (int) vlY);
            vlY += 0.5;
        }
    }

    public void actionDown() {
        if (down) {
            vehicle.setY(vehicle.getY() + (int) vlY);
            vlY += 0.5;
        }
    }

    public void actionRight() {
        if (right) {
            vehicle.setX(vehicle.getX() + (int) vlX);
            vlX += 0.5;
        }
    }

    public void actionLeft() {
        if (left) {
            vehicle.setY(vehicle.getX() - (int) vlX);
            vlX += 0.5;
        }

    }

    private class Listener implements KeyListener {
        
        private Listener (){
           JOptionPane.showMessageDialog(null, "fukcignbullshit");
        }
           
        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("Entered Listener");
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = true;
                System.out.println("dog");
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                left = true;
                System.out.println("dog");
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upp = true;
                System.out.println("dog");
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                down = true;
                System.out.println("dog");
            }
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                vlY = 0;
                right = false;
                System.out.println("dog");
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                vlY = 0;
                left = false;
                System.out.println("dog");
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upp = false;
                System.out.println("dog");
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                down = false;
                System.out.println("dog");
            }
        }

       
        public void keyPressed(KeyEvent e) {         
            
            
            
        }
    }
}
