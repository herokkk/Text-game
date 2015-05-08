package ie.gmit.computing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class is used for other characters 
 * @author Siyi_Kong
 *
 */
public class RealCharacters extends AbstractGameCharacter{

	 private String type;
	  private String name;
	  private float life=10;
	  private Thing inventory;
	  private String behaviour;
	  private Location loc=null;
	  private String strengh;
	  
	  private Location lastLoc=null;
	  
	 /**
	  * Constructor with params
	  * @param type
	  * @param name
	  * @param inventory
	  * @param behaviour
	  * @param strengh
	  */
	public RealCharacters(String type, String name, Thing inventory,
			String behaviour, String strengh) {
		super();
		this.type = type;
		this.name = name;
		this.inventory = inventory;
		this.behaviour = behaviour;
		this.strengh = strengh;
	}

/**
 * Constructor
 * @param type
 * @param name
 * @param inventory
 * @param behaviour
 */
	public RealCharacters(String type, String name, Thing inventory,
			String behaviour) {
		super();
		this.type = type;
		this.name = name;
		this.inventory = inventory;
		this.behaviour = behaviour;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
          
			while (life > 0.00f&&Executor.GAME_STATE){
				//Do some stuff. 
				String name=loc.getName();
				List<RealCharacters> removedCharacters=new ArrayList<RealCharacters>();
				Location templocation=MySaxParser.getLocationsMap().get(name);
				this.setLoc(templocation);
				List<Location> list=templocation.getExits();
	
		        Object[] locs=list.toArray();
				//Object[] directions=direct.toArray();
				int val=(int) (Math.random()*locs.length);
				
				try {
					removedCharacters.add(this);
					this.setLoc((Location)locs[val]);
					
					System.out.println("------------------------------------");
					System.out.println(getName()+" arrive at "+loc.getName());
					
					
					Thread.sleep(40000);//30S
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (ArrayIndexOutOfBoundsException e) {
					// TODO: handle exception
					System.out.println("Null location name: "+templocation.getName());
					System.out.println(e.getCause());
				}
       removedCharacters=null;// Kill this object to avoid massive creation of it
			}
			System.out.println(this.getName() + " has just died...");
		
		
	}

/**
 * Get the location before current one
 * @return
 */
	public Location getLastLoc() {
		return lastLoc;
	}

/**
 * Set the location as current one
 * @param lastLoc
 */
	public void setLastLoc(Location lastLoc) {
		this.lastLoc = lastLoc;
	}

/**
 * Get the strength of this character
 * @return
 */
	public String getStrengh() {
	return strengh;
}

/**
 * Set the strength for this character
 * @param strengh
 */
public void setStrengh(String strengh) {
	this.strengh = strengh;
}

/**
 * Get the type of this character
 */
public String getType() {
		return type;
	}

/**
 * Set the type
 */
public void setType(String type) {
		this.type = type;
}

/**
 * Get the name
 */
public String getName() {
		return name;
}

/**
 * Set the name for the current character
 */
public void setName(String name) {
		this.name = name;
	}

/**
 * Get the life of this character
 * @return
 */
public float getLife() {
		return life;
}

/**
 * Set the life for this character
 * @param life
 */
public void setLife(float life) {
		this.life = life;
}

/**
 * Get the inventory
 */
public Thing getInventory() {
		return inventory;
	}

/**
 * Set the inventory
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

/**
 * Get the location this character in
 * @return
 */
	public Location getLoc() {
		return loc;
	}

	/**
	 * Set the location this character in
	 * @return
	 */
	public void setLoc(Location loc) {
		this.loc = loc;
	}

	
}
