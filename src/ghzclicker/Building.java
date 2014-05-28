package ghzclicker;

/**
 * Describes a base for a building.
 * 
 * @author Marcus Orwén
 */
public class Building {
    private String name; // Name of building
    private double baseCost; // Base cost that will later be calculated with controller
    private double baseHPS = 0; // Base "hertz" per second later will be calculated with modifier
    private String imageLocation; // Building's image location for button.
    private int owned = 0; // Amount of this build that is owned.
    private double price; // The price of a bulding

    /**
     * Construct a building
     * 
     * @param name Name of building
     * @param baseCost Base cost of building
     * @param baseHPS base "Hertz" per Second
     * @param imageLocation Location for building's button image
     */
    public Building(String name, double baseCost, double baseHPS, String imageLocation) {
        this.name = name;
        this.baseCost = baseCost;
        this.baseHPS = baseHPS;
        this.imageLocation = imageLocation;
        this.price = baseCost;
    }

    /**
     * Get building's name
     * 
     * @return name of building
     */
    public String getName() {
        return name;
    }

    /**
     * Get building's base cost
     * 
     * @return base cost of building
     */
    public double getBaseCost() {
        return baseCost;
    }

    /**
     * Get building's base "Hertz" per second
     * 
     * @return base "Hertz" per second of building
     */
    public double getBaseHPS() {
        return baseHPS;
    }

    /**
     * Get location of image for the button
     * 
     * @return the location of the image
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * Set's how many of this building is owned
     * 
     * @param owned The amount owned
     */
    public void setOwned(int owned) {
        this.owned = owned;
    }

    /**
     * Get the amount of buildings owned
     * 
     * @return Amount of buildings owned
     */
    public int getOwned() {
        return owned;
    }

    /**
     * Sets the price of the building.
     * 
     * @param price The price of the building.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the price of the building.
     * 
     * @return the price of the building.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Calculate cost for each building
     */
    public double calculateCosts() {
        if (owned != 0) {
            price = (baseCost * (Math.pow(1.1, owned)));
        }
        return price;
    }

    /**
     * Check if you can buy building specified with its id (i)
     * 
     * @param hertz The owned Hertz
     * @return true if you can buy building else false.
     */
    public boolean canBuyBuilding(double hertz) {
        if (hertz >= price) {
            return true;
        }
        return false;
    }    
}
