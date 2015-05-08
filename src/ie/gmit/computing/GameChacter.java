package ie.gmit.computing;
/**
 * This is interface for the implementation of other characters
 * @author Siyi_Kong
 *
 */
public interface GameChacter extends Runnable{

	/**
	 * Get the name of the character
	 * @return
	 */
	public abstract String getName();
	/**
	 * Set the name of the character
	 * @return
	 */
	public abstract void setName(String name);
	/**
	 * Get the type of the character
	 * @return
	 */
	public abstract String getType() ;
	/**
	 * Set the type of the character
	 * @return
	 */
	public abstract void setType(String type) ;
	/**
	 * Get the inventory within the character
	 * @return
	 */
	public abstract Thing getInventory();
	/**
	 * Get the inventory within the character
	 * @return
	 */
	public abstract void setInventory(Thing inventory);

/**
 * Get the behaviour of this character
 * @return
 */
	public abstract String getBehaviour() ;
	/**
	 * Set the behaviour of this character
	 * @return
	 */
	public abstract void setBehaviour(String behaviour);
		
	
	
}