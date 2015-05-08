package ie.gmit.computing;

import java.util.Comparator;
/**
 * The comparator for figuring out two locations
 * @author Siyi_Kong
 *
 */
public class MyComparator implements Comparator<Location>{

	@Override
	public int compare(Location loc1, Location loc2) {
		// TODO Auto-generated method stub
		if(loc1.getScore()>loc2.getScore()){
			return 1;
		}else if (loc1.getScore()<loc2.getScore()) {
			return -1;
		}
		return 0;
	}

}
