package ie.gmit.computing;

public class HeuristicCalculator {
	/* The heuristic function computes a score by biasing the actual distance using the levels of terrain
	 * and danger as components of the function. In this case the A* heuristic f*(n) + g*(n) + h*(n) is:)
	 */
	public static float getHeuristicValue(int distanceTravelled, String approxGoalDistance, String RouteDifficulty){
		float goaldis=Float.parseFloat(approxGoalDistance);
		float routediffi=Float.parseFloat(RouteDifficulty);
		//float dangerFloat=Float.parseFloat(danger);
		return distanceTravelled +goaldis;
	}
}
