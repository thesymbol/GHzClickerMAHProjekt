package ghzclicker;

/**
 * Describes a base for a building.
 * 
 * @author Marcus Orwén
 */
public class Building {
	private String name; // name of building
	private int baseCost; // base cost that will later be calculated with controller
	private double baseHPS = 0; // base "hertz" per second later will be calculated with modifier
	private String imageLocation; // building's image location for button.
	private int owned = 0; // Amount of this build that is owned.
	private int price; // The price of a bulding

	/**
	 * Construct a building
	 * 
	 * @param name Name of building
	 * @param cost Base cost of building
	 * @param baseHPS base "Hertz" per Second
	 * @param image location for building's button
	 */
	public Building(String name, int baseCost, double baseHPS, String imageLocation) {
		this.name = name;
		this.baseCost = baseCost;
		this.baseHPS = baseHPS;
		this.imageLocation = imageLocation;
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
	public int getBaseCost() {
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
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Get the price of the building.
	 * 
	 * @return the price of the building.
	 */
	public int getPrice() {
		return price;
	}
}
