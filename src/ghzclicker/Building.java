package ghzclicker;

/**
 * Describes a base for a building.
 * 
 * @author Marcus Orwén
 */
public class Building {
	private String name; 		// name of building
	private int baseCost;		// base cost that will later be calculated with controller
	private double baseHPS;		// base "hertz" per second later will be calculated with modifier
	
	/**
	 * Construct a building
	 * 
	 * @param name Name of building
	 * @param cost Base cost of building
	 * @param baseHPS base "Hertz" per Second
	 */
	public Building(String name, int baseCost, double baseHPS) {
		this.name = name;
		this.baseCost = baseCost;
		this.baseHPS = baseHPS;
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
}
