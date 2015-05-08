package ie.gmit.computing;
/**
 * It is exit location
 * @author Siyi_Kong
 *
 */
public class Exit extends Location{
	private Location parent;
	private String direction;
	private String RouteDifficulty;
	private String RouteDistance;
	private String name;
	private int distanceTraveled=0;


	public Exit(Location parent, String direction, String routeDifficulty,
			String routeDistance, String name) {
		this.parent = parent;
		this.direction = direction;
		RouteDifficulty = routeDifficulty;
		RouteDistance = routeDistance;
		this.name = name;
	}



	public int getDistanceTraveled() {
		return distanceTraveled;
	}



	public void setDistanceTraveled(int distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}



	public String getRouteDifficulty() {
		return RouteDifficulty;
	}



	public void setRouteDifficulty(String routeDifficulty) {
		RouteDifficulty = routeDifficulty;
	}



	public String getRouteDistance() {
		return RouteDistance;
	}



	public void setRouteDistance(String routeDistance) {
		RouteDistance = routeDistance;
	}


   /**
    * get last location
    * @return
    */
	

	public String getDirection() {
		return direction;
	}

	public Location getParent() {
	return parent;
}



public void setParent(Location parent) {
	this.parent = parent;
}



	public void setDirection(String direction) {
		this.direction = direction;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public void look() {
		StringBuffer sb = new StringBuffer();
		sb.append("-------- " + this.name + " -----------\n");
		
		sb.append("\n");
		
		System.out.println(new String(sb));
	}
}
