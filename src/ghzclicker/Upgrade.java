package ghzclicker;

/**
 * Describe a base for Upgrades.
 * 
 * @author Mattias Holst
 */
public class Upgrade {
    private String name;
    private double Cost;
    private double baseHPS;
    private double price;
    private int owned;

    /**
     * Construct an upgrade.
     * 
     * @param name The name of the upgrade.
     * @param baseCost The base cost of the upgrade.
     * @param baseHPS the baseHPS of the upgrade.
     */
    public Upgrade(String name, double Cost, double baseHPS) {
        this.name = name;
        this.Cost = Cost;
        this.baseHPS = baseHPS;
    }

    /**
     * get the upgrade name
     * 
     * @return name The name of the upgrade.
     */
    public String getName() {
        return name;
    }

    /**
     * get the BaseCost
     * 
     * @return baseCost The basecost of the upgrade.
     */
    public double getCost() {
        return Cost;
    }

    /**
     * get the baseHPS
     * 
     * @return baseHPS The base hertz per second of the upgrade
     */
    public double getBaseHPS() {
        return baseHPS;
    }

    /**
     * setting the price.
     * 
     * @param price Inserted price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getting the price.
     * 
     * @return price The price of the upgrade.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the amount upgrades owned
     * 
     * @param owned The amount owned
     */
    public void setOwned(int owned) {
        this.owned = owned;
    }

    /**
     * Getting the owned upgrades.
     * 
     * @return The amount owned upgrades.
     */
    public int getOwned() {
        return owned;
    }
}
