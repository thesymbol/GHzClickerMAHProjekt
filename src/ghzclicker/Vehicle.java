package ghzclicker;

import javax.swing.ImageIcon;

public class Vehicle {
    private String imageLocation;
    private int x=1;
    private int y=1;

    public Vehicle(String imageLocation) {
        this.imageLocation = imageLocation;
        
    }

    

    public int getX() {
        return x;
    }
    
    public void setX(int x){
        this.x=x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y){
        this.y=y;
    }
    
    public String getImageLocation() {
        return imageLocation;
    }
    
    public void moveTo(int x, int y){
        this.x=x;
        this.y=y;
    }

}
