package ghzclicker;

/**
 * Describe a base for Upgrades.
 * 
 * @author Mattias Holst
 */
public class Upgrade {
    private String name;
    private double baseCost;
    private double baseHPS;
    private double price;
    private int owned;
    private int requirement;
    private int maxOwned;

    /**
     * Construct an upgrade.
     * 
     * @param name The name of the upgrade.
     * @param baseCost The base cost of the upgrade.
     * @param baseHPS the baseHPS of the upgrade.
     * @param requirement The requirement to buy the upgrade
     * @param maxOwned The maximum amount of upgrades to own
     */
    public Upgrade(String name, double baseCost, double baseHPS, int requirement, int maxOwned) {
        this.name = name;
        this.baseCost = baseCost;
        this.price = baseCost;
        this.baseHPS = baseHPS;
        this.requirement = requirement;
        this.maxOwned = maxOwned;
    }

    /**
     * Get the upgrade name
     * 
     * @return name The name of the upgrade.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the BaseCost
     * 
     * @return baseCost The basecost of the upgrade.
     */
    public double getBaseCost() {
        return baseCost;
    }

    /**
     * Get the baseHPS
     * 
     * @return baseHPS The base hertz per second of the upgrade
     */
    public double getBaseHPS() {
        return baseHPS;
    }

    /**
     * Get maxemum upgrades to be owned
     * 
     * @return maxemum upgrades owned
     */
    public int getMaxOwned() {
        return maxOwned;
    }

    /**
     * Get base requred buildings owned
     * 
     * @return amount of buildings needed for the upgrade
     */
    public int getRequirement() {
        return requirement;
    }

    /**
     * Set the required buildings owned
     * 
     * @param requirement
     */
    public void setRequirement(int requirement) {
        this.requirement = requirement;
    }

    /**
     * Setting the price.
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

    /**
     * Calculate the cost for each upgrades.
     */
    public double calculateCosts() {
        if (owned != 0) {
            price = (baseCost * (Math.pow(10, owned)));
        }
        return price;
    }

    /**
     * Check if you can buy upgrade specified with its id (i)
     * 
     * @param hertz The total Hertz owned
     * @return true if you can buy upgrade, else false.
     */
    public boolean canBuyUpgrade(double hertz) {
        if (hertz >= price) {
            return true;
        }
        return false;
    }
}
