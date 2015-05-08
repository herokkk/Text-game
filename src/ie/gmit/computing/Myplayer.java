package ie.gmit.computing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
/**
 * This class is for the character you play
 * @author Siyi_Kong
 *
 */
public class Myplayer  extends AbstractGameCharacter{
  private String type;
  private String name;
  private float life=40;
  private Thing inventory;
  private String behaviour;
  private Location loc=null;
  private List<Thing> myinventories;
  private Map<String,Thing> mapToThing;// each name map each entity
  private List<Thing> objects;// objects in that location
  
  private FunctionBlock fb;
  
  /*******A star***********/
  PriorityQueue<Location> open = null;
	List<Location> closed = new ArrayList<Location>();
	MyComparator sorter = new MyComparator();
	static int nodecount;
	/**
	 * The constructor
	 * @param type
	 * @param name
	 * @param inventory
	 * @param behaviour
	 * @param loc
	 */
public Myplayer(String type, String name, Thing inventory,
			String behaviour, Location loc) {
		super(type,name,inventory,behaviour);
		this.type = type;
		this.name = name;
		this.inventory = inventory;
		this.behaviour = behaviour;
		this.loc = loc;
		
		mapToThing=new HashMap<String, Thing>();
		myinventories=new ArrayList<Thing>();// items I own
		
		if (inventory!=null) {
			mapToThing.put(inventory.getType(), inventory);
		}

		search(loc);
	}

/**
 * Take the a location as starting point and go to search from here
 * @param start
 */
public void search(Location start){
	open=new PriorityQueue<Location>(10, sorter);
    start.setDistanceTravelled(0);
    open.add(start);
    
    while(!open.isEmpty()){
    	Location loc=open.poll();
    	if(loc.isGoalLoc()){
    		List<Location> path=new ArrayList<Location>();
    		while(loc.getParent()!=null){
    			path.add(loc);
    			loc=loc.getParent(); 
    		}
    		path.add(loc);
    		Collections.reverse(path);
    		System.out.println("Best path is:");
    		
    		for(int i=0;i<path.size();i++){
    			System.out.print(path.get(i).getName()+",");//
    			
    		}
           break;
    	}
    	pushSucessor(loc);
    	closed.add(loc);
    }
}
/**
 * push the current location's sub-locations into open queue
 * @param location
 */
public void pushSucessor(Location location){
	Object[] locs=location.children();
	
	for(int i=0;i<locs.length;i++){
		
		//acc
		Location child=(Location) locs[i];
		float score=HeuristicCalculator.getHeuristicValue(location.getDistanceTravelled()+Integer.parseInt(child.getRouteDistance()),
				location.getGoalDistance().trim(), child.getRouteDifficulty().trim());
		
		if(open.contains(child)||closed.contains(child)){
			
			continue;
		}else {
			open.remove(child);
			closed.remove(child);
			child.setParent(location);
			child.setDistanceTravelled(location.getDistanceTravelled()+Integer.parseInt(child.getRouteDistance()));
			open.add(child);
		}
		
		
	}
	

}
/**
 * Get the functionBlock
 * @return
 */
	public FunctionBlock getFb() {
		return fb;
	}

/**
 * Set the functionBlock
 * @param fb
 */
	public void setFb(FunctionBlock fb) {
		this.fb = fb;
	}

/**
 * Get the player's current health
 * @return
 */
	public float getLife() {
		return life;
	}

/**
 * Set the player's current health
 * @param life
 */
	public void setLife(float life) {
		this.life = life;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}

	/**
	 * get the current weapon at hand
	 */
	public Thing getInventory() {
		return inventory;
	}

   //thing's lifeforce is by default, -0.3
	public void setInventory(Thing inventory) {
		Thing temp=getInventory();
		this.inventory = inventory;
		
		
		if(!mapToThing.containsKey(inventory.getType()))
		mapToThing.put(inventory.getType(), inventory);
	}

/**
 * Get the collection of items in this player
 * @return
 */
	public List<Thing> getMyinventories() {
		return myinventories;
	}

/**
 * Set the items in bag
 * @param myinventories
 */
	public void setMyinventories(List<Thing> myinventories) {
		this.myinventories = myinventories;
	}


/**
 * Set the current location
 * @return
 */
	public  Location getLoc() {
		return loc;
	}

   /**
    * Set the current location where character is in
    * @param loc
    */
	public void setLoc(Location loc) {
		this.loc = loc;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
		while(Executor.GAME_STATE){
			objects=getLoc().getObjects();// get the initial state and then get the objects in this loca area
//			if(loc.getName()=="Cyprus"){
//				System.out.println("Congratulation!! You win the game!");
//				Executor.GAME_STATE=false;
//			}
			String command=scanner.nextLine();
			if(command.equals("LOOK")){
				lookAround();
			}
			if(command.startsWith("GET")){
				getItemFromCurrentLocation(command);
				
			}	
            if(command.startsWith("TELL")){
				greetPeople(command);
			}
            if(command.startsWith("DROP")){
            	dropItems(scanner, command);
            }
            if(command.equals("CHECK")){
            	System.out.println(toString());
            }
            if(command.equals("USE")){
            	useItems(scanner);
              
            }
            if(command.equals("EAT")){
            	eatFoods(scanner);
              
            }
            if(command.startsWith("GO")){
            	if(locHasEnemy(scanner)==0)
            	goToPlaces(scanner,command);
            	else {
					continue;//means no battle before, waitting for next keyboard input
				}
			}
            if (command.startsWith("KILL")) {
				killPeople(command);
			}
            if (command.startsWith("HELP")) {
				callForHelp();
			}
            //if the player's thread is killed, the game is over
             if (!Thread.currentThread().isAlive()) {
            	 Executor.GAME_STATE=false;
				System.exit(0);
			}
		}
	}

	/**
	 * Call help
	 */
public void callForHelp(){

	List<RealCharacters> characters=getLoc().getObservers();

	for(RealCharacters realCharacters:characters){
		//if the enemy is what we are looking for and it is devil
		if(getInventory()!=null){
			if(realCharacters.getBehaviour().equals("friend")&&getInventory()!=null){
			   	System.out.println(realCharacters.getName()+": Dont worry,mate. I will help you");
			   String strength=realCharacters.getStrengh();
			  double newPower=Double.parseDouble(inventory.getPower())+Double.parseDouble(strength);
			  inventory.setPower(String.valueOf(newPower));
			}if(realCharacters.getBehaviour().equals("neutral")){
				System.out.println("Hmm, I am thinking which side I would be happy to stay with~~~");
				try {
					Thread.sleep(1000);
					
					int randomNum=(int) (Math.random()*4);
					
					//following code is for neutral 
					if(randomNum==0){
						System.out.println(realCharacters.getName()+": Hey,"+getName()+", I woule like to help you as well");
					realCharacters.setBehaviour("friend");
					
					 String strength=realCharacters.getStrengh();
					  double newPower=Double.parseDouble(inventory.getPower())+Double.parseDouble(strength);
					  inventory.setPower(String.valueOf(newPower));
					}else {
						System.out.println(realCharacters.getName()+": Hahaha,"+getName()+", I will turn you to ashes today!! ");

						realCharacters.setBehaviour("devil");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			System.out.println("Please pick up at least one weapon!!!");
		}
		}
}
	public void lookAround() {
		if(loc!=null)
			loc.look();
	}

/**
 * Drop the item on current location
 * @param scanner
 * @param command
 */
	public void dropItems(Scanner scanner, String command) {
		StringTokenizer tokenizer=new StringTokenizer(command, " ");
		String head=tokenizer.nextToken();
		Location location=getLoc();
		if(tokenizer.hasMoreTokens()){
			// if you command it to drop a item
			Thing item=mapToThing.get(tokenizer.nextToken());
		   System.out.println("实体是： "+item.getType());
			if(item!=null){
				myinventories.remove(item);
				getLoc().getObjects().add(item);
				
				if(item.getType().equals("Holy_grail")&&location.getName().equals("Cyprus")){
					System.out.println("Congratulation!! You win the game!");
				   Executor.GAME_STATE=false;
				   System.exit(0);
				}
				System.out.println(item.getType()+" dropped off");
			}
			
		}else {
			//if you simple input drop
			String itemToDrop;
			Thing item;
			System.out.println("Please select the items you want to drop: ");
			for(Thing thing:myinventories){
				System.out.println(thing.getType());
			}
		   itemToDrop=scanner.next();
			
		   item=mapToThing.get(itemToDrop);
		   if(item!=null){
			   System.out.println("this is not null");
			   myinventories.remove(item);
				getLoc().getObjects().add(item);

			  // objects.add(item);
		   }
		   
		   if(item.getType().equals("Holy_grail")&&location.getName().equals("Cyprus")){
				System.out.println("Congratulation!! You win the game!");
			   Executor.GAME_STATE=false;
			   System.exit(0);
			}
		}
	}

	/**
	 * Eat foods to restore health
	 * @param scanner
	 */
  public void eatFoods(Scanner scanner){
	  String itemToUse;
		Thing item;
		System.out.println("Please select the foods you want to eat: ");
		for(Thing thing:myinventories){
			if(!thing.isHasPower())
			System.out.println(thing.getType());
		}
          itemToUse=scanner.next();
		
          item=mapToThing.get(itemToUse);
          
          if(item!=null && !item.isHasPower()){
		  
			 this.life+=Float.parseFloat(item.getLife())*10;
		     this.getMyinventories().remove(item);// remove the target item out of bag
          }
  }
  /**
   * Using the weapon that has in bag
   * @param scanner
   */
	public void useItems(Scanner scanner) {
		String itemToUse;
		Thing item;
		System.out.println("Please select the weapons you want to use: ");
		for(Thing thing:myinventories){
			if(thing.isHasPower())
			System.out.println(thing.getType());
		}
            itemToUse=scanner.next();
		
            item=mapToThing.get(itemToUse);//get the item from weapon pool
            
            if(item!=null){
		   
		   if (item.isHasPower()) {
			   if(getInventory()!=null)// if the player has a item in use
			   this.getMyinventories().add(getInventory());// put the current item in bag
			   
		       this.setInventory(item);// set the target item as current item
		       this.getMyinventories().remove(item);// remove the target item out of bag
		}
		  
            }
	}

	/**
	 * To Check how dangerous for this direction
	 * @param scanner
	 * @param to
	 * @return
	 */
public int checkDanger(Scanner scanner,Location to){
	Map<String, String> attrs=getLoc().getLocAttrs().get(to);
	Set<String> keys=attrs.keySet();
	for(String difficulty:keys){
		String distance=attrs.get(difficulty);
		int disToInt=Integer.parseInt(distance);
		int diffToInt=Integer.parseInt(difficulty);
		
		int sum=disToInt+diffToInt;
		
		if(sum>=15){
			
			System.out.println("The frontal route is full of danger, are you sure (Y/N)?");
            if(scanner.nextLine().equals("Y")){
            	return 1;
            }else {
				return 2;
			}
		}
     
	}
	return 0;
}

/**
 * This method is used to prevent player from skiping over a dangerous place without fighting
 * @param scanner
 */
public int locHasEnemy(Scanner scanner){
	
	List<RealCharacters> characters=getLoc().getObservers();

	for(RealCharacters realCharacters:characters){
		//if the enemy is what we are looking for and it is devil
		if(realCharacters.getBehaviour().equals("devil")){
			  System.out.println(realCharacters.getName()+": Hey, snob!! you cannot go over here until you can beat me!! \n"+
		         "Do you want to take this challenge?Otherwise you may lose bloods(Y/N)");
			  
			  switch (scanner.nextLine()) {
			case "Y":
				 if(fight(getInventory(), realCharacters)==2) 
				   {
					   return 2;//means have not had battle
				   }//my current weapon and character
				break;

			case "N":
				this.life=getLife()-10;
				System.out.println("Your lifeforce has been reduced by 10");
				break;
			}
			}
		}
	System.out.println("yes,no enemy here now");
	return 0;
}
	public void goToPlaces(Scanner scanner,String command) {
		StringTokenizer tokenizer=new StringTokenizer(command, " ");
		String head=tokenizer.nextToken();
		
		if(tokenizer.hasMoreTokens()){
			String direction=tokenizer.nextToken();
			Location exit=loc.getNextLoc(direction);
			Location location=null;
			
			if (exit!=null) {
				System.out.println(exit.getName());
				
				location=MySaxParser.getLocationsMap().get(exit.getName());
				//System.out.println(location.getName()+" "+location.getDescription()+" "+location.getGoalDistance());
				if(location!=null){
					//check it is a dangerous place
					int code=checkDanger(scanner, location);
					switch (code) {
					case 1:
						setLoc(location);
						 System.out.println("Let's go! I  like  challenge!");
						loc.look();
						break;
					case 2:
						System.out.println("Safe place is always good! You do not move");
					    break;
					case 0:
						setLoc(location);
						 System.out.println("Let's go!");
						loc.look();
						break;
					}
					
					
					//HashMap<String, Location> maps=loc.getget(location.getName());
					//maps.get(direction).look();
				}
			}else {
				System.out.println("There is no way to go for this direction!!Please pick another one.");
			}
			
		}
	}

	public void killPeople(String command){
		// syntax: KILL name WITH objectName
    	StringTokenizer tokenizer=new StringTokenizer(command, " ");
		String head=tokenizer.nextToken();//skip over kill
		List<RealCharacters> killedCharacters=new ArrayList<RealCharacters>();
		if(tokenizer.hasMoreTokens()){
			String enemyName=tokenizer.nextToken().toLowerCase();
			
			if(tokenizer.hasMoreTokens())
			tokenizer.nextToken();// skip over WITH
			else{
				System.out.println("Hey, man. You do not have any weapon at hand now!");
                return;
			}
			
			if(tokenizer.hasMoreElements()){
				String weapon=tokenizer.nextToken();
			}else {
				return;
			}
			
			List<RealCharacters> characters=getLoc().getObservers();

			for(RealCharacters realCharacters:characters){
				//if the enemy is what we are looking for and it is devil
				if(realCharacters.getName().equals(enemyName) && realCharacters.getBehaviour().equals("devil")){
					  System.out.println("I got attack");
					  
					  if(fight(getInventory(), realCharacters)==-1)
						 System.exit(0);
						
					  
					  if(this.life>0){
						  //let the enemy dispear	 
							  System.out.println("You have killed "+realCharacters.getName());
                               killedCharacters.add(realCharacters);
							 
					  }
					  JFuzzyChart.get().chart(fb);
					  fb.getVariable("victory").defuzzify();
				}
                 
			}
			getLoc().getObservers().removeAll(killedCharacters);// remove all the stuff killed
			killedCharacters=null;
		}
	}
/**
 * Called when you want to greet people
 * @param command
 */
	public void greetPeople(String command) {
		StringTokenizer tokenizer=new StringTokenizer(command, " ");
		String head=tokenizer.nextToken();
		
		while(tokenizer.hasMoreTokens()){
			String temp=tokenizer.nextToken();
		    System.out.println("Me:Hi, "+temp+",how are you");
		    System.out.println(temp+": I am good");
		}
	}

/**
 * Get the item from the current location
 * @param command
 */
	public void getItemFromCurrentLocation(String command) {
		StringTokenizer tokenizer=new StringTokenizer(command, " ");
		String head=tokenizer.nextToken();
		List<Thing> removedItems=new ArrayList<Thing>();
		while(tokenizer.hasMoreTokens()){
			String temp=tokenizer.nextToken().toLowerCase();
			
			//List<Thing> locItemss=getLoc().getObjects();
			for(Thing thing:objects){//根本没有包括进来
				String tempThing=thing.getType().toLowerCase();
				if(tempThing.equals(temp)){
					 if(tempThing.equals("holy_grail")){
						 System.out.println("Wow!! you got Holy Grail, please bring it to Cyprus safely");
					 }
					 
					System.out.println("you are using "+thing.getType()+" as your weapon");
					removedItems.add(thing);
					//objects.remove(thing);// remove the thing from the list
		             myinventories.add(thing);
		             mapToThing.put(thing.getType(), thing);// put the new object I pick into the map
				}
			}
			objects.removeAll(removedItems);
			getLoc().setObjects(objects);// update the objects
		}
	}

	/**
	 * Fight with enemy
	 * @param thing
	 * @param realCharacters
	 * @return
	 */
	public int fight(Thing thing,RealCharacters realCharacters){
		if(thing!=null&&realCharacters!=null){
			
			System.out.println("my current power is: "+thing.getPower()+"\n"
					+"The enemy's power is: "+realCharacters.getStrengh());
			
			fb.setVariable("weapon", Double.parseDouble(thing.getPower()));
			fb.setVariable("opponent", Double.parseDouble(realCharacters.getStrengh()));
			
			fb.evaluate();
			System.out.println("My life before: "+this.getLife());
			float victory=(float) fb.getVariable("victory").getValue();
			this.life=this.getLife()-(10-victory);

			 if(!Float.isInfinite(this.life)){
					System.out.println("your life after fighting: "+getLife());
				}else {
					System.out.println("You lose!");
					Executor.GAME_STATE=false;
					System.out.println("My life after: 0");
                    return -1;
				}
			
			

		}else {
			System.out.println(thing==null?"you do not have any weapon now":"no enemy nearby!!");
		   return 2;
		}
		return 0;
	}
	
	    @Override
		public String toString() {
			// TODO Auto-generated method stub
		StringBuilder str=new StringBuilder();
		for(Thing thing:myinventories){
			str.append(thing.getType());
			str.append(":");
		}
			return "[Player name]: "+getName()+"\n"+
				   "[Lifeforce]: "+getLife()+"\n"+
				   "[Current weapon]: "+(inventory==null?"NULL":getInventory().getType())+"\n"+
			       "[Inventories]: "+str.toString()+"\n";
			
		}
	
	
}
