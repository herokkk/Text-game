package ie.gmit.computing;

import java.util.*;
/**
 * This class is an entity class for Location
 * @author Siyi_Kong
 *
 */
public class Location implements Lookable{
	private List<RealCharacters> observers = new ArrayList<RealCharacters>();
	private List<Thing> objects = new ArrayList<Thing>();
	private List<Location> exits = new ArrayList<Location>();
	private Map<String, Location> Nextlocs=new HashMap<String, Location>();
	private Map<Location, Map<String,String>> locAttrs=new HashMap<Location, Map<String,String>>();

	/********location attrs***************/
	private String goalDistance;
	private String danger;
	private String name;// the name of this location
	private String description;
	private String id;
	
	
	/**********exit attrs**********/
	private String direction;
	private String RouteDifficulty;
	private String RouteDistance;
	private Location children;
	
	private int distanceTravelled = 0;
	private boolean isGoalLoc=false;

	private Location parent;


	/**
	 * The Constructor
	 */
	public Location() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param direction The direction to the next location
	 * @param routeDifficulty Difficulty of this route
	 * @param routeDistance  The distance between this route
	 * @param children  The next location
	 */
	public void setLocationAttr(String direction, String routeDifficulty, String routeDistance,
		Location children) {
		Map<String, String> attrs=new HashMap<String,String>();
		
	this.direction = direction;
	RouteDifficulty = routeDifficulty;
	RouteDistance = routeDistance;
	this.children = children;
	
	attrs.put(routeDifficulty, routeDistance);
	locAttrs.put(children, attrs);
	
	exits.add(children);
	Nextlocs.put(direction, children);
}

	/**
	 * Retrieve the attrs of a location
	 * @return
	 */
	public Map<Location, Map<String, String>> getLocAttrs() {
		return locAttrs;
	}
	/**
	 * Set the attrs of a location
	 * @return
	 */
	public void setLocAttrs(Map<Location, Map<String, String>> locAttrs) {
		this.locAttrs = locAttrs;
	}
/**
 * Get the direction of this location
 * @return
 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Set the direction
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * Get route difficulty
	 * @return
	 */
	public String getRouteDifficulty() {
		return RouteDifficulty;
	}
	/**
	 * Set route difficulty
	 * @return
	 */
	public void setRouteDifficulty(String routeDifficulty) {
		RouteDifficulty = routeDifficulty;
	}
	/**
	 * Get Route Distance
	 * @return
	 */
	public String getRouteDistance() {
		return RouteDistance;
	}
	/**
	 * Set Route Distance
	 * @return
	 */
	public void setRouteDistance(String routeDistance) {
		RouteDistance = routeDistance;
	}


/**
 * Return all the sub-locations
 * @return
 */
	public Object[] children(){
		return  getExits().toArray();
	}

	/**
	 * Get the parent of this location
	 * @return
	 */
	public Location getParent() {
		return parent;
	}

	/**
	 * Set the parent of this location
	 * @return
	 */
	public void setParent(Location parent) {
		this.parent = parent;
	}
/**
 * Check if this location is goal loc
 * @return
 */
	public boolean isGoalLoc() {
		
		return isGoalLoc;
	}

	/**
	 * Set this location is goal loc or not
	 * @return
	 */
	public void setGoalLoc(boolean isGoalLoc) {
		this.isGoalLoc = isGoalLoc;
	}

	
	/**
	 * Get the distance travelled 
	 * @return
	 */
	public int getDistanceTravelled() {
		return distanceTravelled;
	}

	/**
	 * Set the distance travelled 
	 * @return
	 */
	public void setDistanceTravelled(int distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

/**
 * Figure out the score according to the goalDistance and routeDifficulty 
 * @return
 */
	public float getScore(){
		 return HeuristicCalculator.getHeuristicValue(distanceTravelled,goalDistance, 
				 RouteDifficulty);
	}

	/**
	 * Get the distance from here to goalDdistance
	 * @return
	 */
	public String getGoalDistance() {
		return goalDistance;
	}

	/**
	 * Get the distance from here to goalDdistance
	 * @param goalDistance
	 */
	public void setGoalDistance(String goalDistance) {
		this.goalDistance = goalDistance;
	}

	/**
	 * Get the id of this location
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the id of this location
	 * @return
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get  the characters  in this area
	 * @return
	 */
	public List<RealCharacters> getObservers() {
		return observers;
	}
    
	/**
	 * Set the characters in this area
	 * @param observers
	 */
	public void setObservers(List<RealCharacters> observers) {
		this.observers = observers;
	}

	/**
	 * Get items in this area
	 * @return
	 */
	public List<Thing> getObjects() {
		return objects;
	}

	/**
	 * Set items in this area
	 * @return
	 */
	public void setObjects(List<Thing> objects) {
		this.objects = objects;
	}

	/**
	 * Get danger in this area
	 * @return
	 */
	public String getDanger() {
		return danger;
	}

	/**
	 * Set danger in this area
	 * @return
	 */
	public void setDanger(String danger) {
		this.danger = danger;
	}

	/**
	 * Get the exit locations
	 * @return
	 */
	public List<Location> getExits() {
		return exits;
	}

	/**
	 * Set the exit locations
	 * @return
	 */
	public void setExits(List<Location> exits) {
		this.exits = exits;
	}

	/**
	 * Put one exit into list
	 * @param exit
	 */
	public void setExitsByOne(Exit exit){
		this.exits.add(exit);
	}
	/**
	 * Get the name of this location
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this location
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the description of this location
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set the description of this location
	 * @return
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * put a newly-entered character into list
	 * @param gc
	 */
	public void enter(RealCharacters gc){
		observers.add(gc);
	}
	
	/**
	 * Remove a character from the list
	 * @param gc
	 */
	public void exit(GameChacter gc){
		observers.remove(gc);
	}
	
	/**
	 * This method is to store all the locs come next
	 * @param direct
	 * @param location
	 */
	public void setNextLoc(String direction,Exit location){
		Nextlocs.put(direction, location);
	}
	
	/**
	 * Get the next location from the current one
	 * @param direction
	 * @return
	 */
	public Location getNextLoc(String direction){
		return Nextlocs.get(direction);
	}
	
	/**
	 * Get the next location by the direction
	 * @return
	 */
	public Map<String, Location> getNextlocs() {
		return Nextlocs;
	}

	/**
	 * Set a new map
	 * @param nextlocs
	 */
	public void setNextlocs(Map<String, Location> nextlocs) {
		Nextlocs = nextlocs;
	}

	/**
	 * Prints out the description about this location
	 */
	public void look() {
		StringBuffer sb = new StringBuffer();
		sb.append("-------- " + this.name + " -----------\n");
		sb.append(this.description+" :)");
		
		sb.append("\nYou see: \n");
		for (RealCharacters gc : observers){
			sb.append(gc.getName()+"(Power:"+gc.getStrengh()+")");
			sb.append(" , ");
		}
		
		//You also see objects....
		sb.append("\n");
		sb.append("\nVisible Objects are: \n");
		for (Thing obj : objects){
			sb.append(obj.getType()+"(Power:"+obj.getPower()+")"+" , ");
		}	
		sb.append("\n");
		sb.append("\nVisible exits are: \n");
		
		for(String direc:Nextlocs.keySet()){
			sb.append(direc+",");
		}
//		for (Location e : exits){
//			sb.append(e.getd+" , ");
//		}		
		sb.append("\n");
		
		System.out.println(new String(sb));
	}

	@Override
	public String toString() {
		return "Location [goalDistance=" + goalDistance + ", danger=" + danger
				+ ", name=" + name + ", id=" + id + ", direction=" + direction
				+ ", RouteDifficulty=" + RouteDifficulty + ", RouteDistance="
				+ RouteDistance + "]";
	}

	
}
