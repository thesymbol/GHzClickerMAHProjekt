package ghzclicker;


public class Achievements {
    private String name;
    private boolean owned=false;    
    private int requierment;    
    
    /**
     * Construct a Achievement
     * 
     * @param name Name of building
     * @param requierment The requierment to buy this building.   
     */
    public Achievements(String name, int requirement) {
        this.name=name;        
        this.requierment=requirement;                      
    }
    
    /**
     * Get requierment for the achievement
     * 
     * @param req
     * @return if you have the requiermtns 
     */
    public boolean requirement(int req){
        if(requierment<=req){
            return true;
        }
        return false;        
    }

    /**
     * Get name of achievements
     * 
     * @return Name of achievements
     */
    public String getName(){
        return name;        
    }
    
    /**
     * Get if a achievements is owned
     * 
     * @return If achievement is owned
     */
    public boolean getOwned(){
        return owned;        
    }
    
    /**
     * Set if achievement is owned
     * 
     * @param owned Owned or not.
     */    
    public void setOwned(boolean owned){
       this.owned=owned;        
    } 
}
