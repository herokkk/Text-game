package ie.gmit.computing;

/**
 * This class serves as a model for all kinds of characters
 * @author Siyi_Kong
 *
 */
public abstract class AbstractGameCharacter implements GameChacter{
	 private String type;
	  private String name;
	  private Thing inventory;
	  private String behaviour;

   /**
    * Non-params constructor
    */
	public AbstractGameCharacter() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructor with params
	 * @param type
	 * @param name
	 * @param inventory
	 * @param behaviour
	 */
	public AbstractGameCharacter(String type, String name, Thing inventory,
			String behaviour) {
		super();
		this.type = type;
		this.name = name;
		this.inventory = inventory;
		this.behaviour = behaviour;
	}

	/**
	 * Get the name of this character
	 */
	public String getName() {
		return name;
	}


	/**
	 * Set the name of this character
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the type of this character
	 */
	public String getType() {
		return type;
	}
	/**
	 * Set the type of this character
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Get the inventory of this character
	 */
	public Thing getInventory() {
		return inventory;
	}
	/**
	 * Set the inventory of this character
	 */
	public void setInventory(Thing inventory) {
		this.inventory = inventory;
	}
	/**
	 * Get the behaviour of this character
	 */
	public String getBehaviour() {
		return behaviour;
	}
	/**
	 * Set the behaviour of this character
	 */
	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}
	
	
}
