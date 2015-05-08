package ie.gmit.computing;

/**
 * This is an entity class for objects that can be carried
 * @author Siyi_Kong
 *
 */
public class Thing {
   private String type;
   private String life="-0.3";// default value is -0.3
   private String power="0";
   private boolean hasPower=false;// default is no power. Depend on the value
   private boolean hasSpeed=false;
  
   /**
    * Constructor
    * @param type
    */
  public Thing() {
	// TODO Auto-generated constructor stub
}
/**
 * Constructor with params
 * @param type
 */
public Thing(String type) {
	super();
	this.type = type;
}


/**
 * constructor
 * @param type
 * @param hasPower
 */
public Thing(String type, boolean hasPower) {
	super();
	this.type = type;
	this.hasPower = hasPower;
}
/**
 * 
 * @param type
 * @param life
 */
public Thing(String type, String life) {
	super();
	this.type = type;
	this.life = life;
}

/**
 * If this object has speed
 * @return
 */
public boolean isHasSpeed() {
	return hasSpeed;
}

/**
 * Set if this object has speed or not
 * @param hasSpeed
 */
public void setHasSpeed(boolean hasSpeed) {
	this.hasSpeed = hasSpeed;
}

/**
 * if this object has power or not
 * @return
 */
public boolean isHasPower() {
	if(Integer.parseInt(power)>0){
		hasPower=true;
	}else {
		hasPower=false;
	}
	return hasPower;
}

/**
 * set if this object has power or not
 * @param hasPower
 */
public void setHasPower(boolean hasPower) {
	this.hasPower = hasPower;
}

/**
 * Get the power of this object
 * @return
 */
public String getPower() {
	return power;
}

/**
 * Set the power
 * @param power
 */
public void setPower(String power) {
	this.power = power;
}

/**
 * Get the type of this object
 * @return
 */
public String getType() {
	return type;
}

/**
 * Set the type of this object
 * @param type
 */
public void setType(String type) {
	this.type = type;
}

/**
 * Get the life effects of this object
 * @return
 */
public String getLife() {
	return life;
}


/**
 * Set the life effects of this object
 * @param life
 */
public void setLife(String life) {
	this.life = life;
}
   
   
}
