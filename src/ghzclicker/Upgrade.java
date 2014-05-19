package ghzclicker;
/**
 * Describe a base for Upgrades.
 * 
 * @author Mattias Holst
 *
 */
public class Upgrade {
    private String name;
    private double Cost;
    private double baseHPS;
    private double price;
    /**
     * Construct an upgrade.
     * 
     * @param name The name of the upgrade. 
     * @param baseCost The base cost of the upgrade.
     * @param baseHPS the baseHPS of the upgrade.
     */
    public Upgrade(String name, double Cost, double baseHPS){
        this.name = name;
        this.Cost = Cost;
        this.baseHPS = baseHPS;
    }
    /**
     * get the upgrade name
     * @return name The name of the upgrade.
     */
    public String getName(){
        return name;
    }
    /**
     * get the BaseCost
     * @return baseCost The basecost of the upgrade.
     */
    public double getBaseCost(){
        return Cost;
    }
    /**
     * setting the price.
     * @param price Inserted price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Getting the price.
     * @return price The price of the upgrade.
     */
    public double getPrice(){
        return price;
    }
}
