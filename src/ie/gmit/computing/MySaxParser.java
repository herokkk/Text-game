package ie.gmit.computing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * This class is for parsing the xml file
 * @author Siyi_Kong
 *
 */
public class MySaxParser extends DefaultHandler{
	private static final String GAME_FILE="resource/data.xml";
	
	/*************switche for each of element*********************/
	private boolean location=false;
	private boolean description=false;
	private boolean Object=false;
	private boolean game_character=false;
	private boolean Exit=false;
	private String tag;
	/*************switche for each of element*********************/
	
	
	
	/*************Entity class for each element*********************/
	private Location locationEntity=null;
	private Exit exitEntity=null;
	private RealCharacters realCharacterEntity=null;
	private Thing thing=null;
	private static MySaxParser parser=null;
	/*************Entity class for each element*********************/
	
	/*************Collections for each element*********************/
	private List<Thing> tempListForObject=null;// a collection of inventory
	private List<RealCharacters> characters=null;
	private List<Location> locations=new ArrayList<Location>();// non-duplicate 10 locations
	private List<Location> exits =null;// a collection of exit location
	
	//direction:childrens
	//private Map<Location,HashMap<String,Location>> mapsForTenLocations=new HashMap<Location, HashMap<String,Location>>();// for all locations along with its children
	private static HashMap<String, Location> maps=new HashMap<String, Location>();// name, locations
	/*************Collections for each element*********************/
	
	public MySaxParser() {
		try {
			SAXParser parser=SAXParserFactory.newInstance().newSAXParser();
			parser.parse(new File(GAME_FILE), this);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	 
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		//super.startElement(uri, localName, qName, attributes);
		//String locationName;
		String locID;
		
		if(qName.equals("location")){
			tempListForObject=new ArrayList<Thing>();
			characters=new ArrayList<RealCharacters>();
			exits = new ArrayList<Location>();
			
			
			location=true;
			
			try {
				locationEntity=(Location) Class.forName("ie.gmit.computing.Location").newInstance();
				locationEntity.setName(attributes.getValue("name"));
				locationEntity.setId(attributes.getValue("id"));
				locationEntity.setDanger(attributes.getValue("danger"));
				locationEntity.setGoalDistance(attributes.getValue("goalRouteDistance"));
				
				if(attributes.getValue("name").equals("Cyprus")){
					locationEntity.setGoalLoc(true);
				}
				maps.put(locationEntity.getName(), locationEntity);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(qName.equals("Description")){
			description=true;
		  
		}else if(qName.equals("Object")){
			String type=attributes.getValue("type");
			String life=attributes.getValue("life");
			String power=attributes.getValue("power");
			String speed=attributes.getValue("speed");

			thing=new Thing(type, life);
			
			if (power!=null) {
				thing.setPower(power);
			}
			if(speed!=null){
				thing.setHasSpeed(true);
			}
			tempListForObject.add(thing);
			//locationEntity.setObjects(tempListForObject);// update
			//tempListForObject.remove(0);// remove all the element after set the value to location
			
			
		}else if(qName.equals("game_character")){
			//the following four parts are not attr, so cant be found
			String type=attributes.getValue("type");
			String name=attributes.getValue("name");
			String inventory=attributes.getValue("inventory");
			String behaviour=attributes.getValue("behaviour");
			String strength=attributes.getValue("strength");
			
			realCharacterEntity=new RealCharacters(type, name,new Thing(inventory), behaviour,strength);
			realCharacterEntity.setLoc(locationEntity);
			characters.add(realCharacterEntity);// put the element in the list
		}
		else if (qName.equals("edge")) {
			String name=attributes.getValue("from");
			Location locName=maps.get(name);
			if(locName!=null){
				Location subloc=maps.get(attributes.getValue("to"));
				String direction=attributes.getValue("direction");
				String distance=attributes.getValue("distance");
				String difficulty=attributes.getValue("difficulty");
				locName.setLocationAttr(direction, difficulty, distance, subloc);
				maps.put(name, locName);// update the map
			}
		}
		tag=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		//super.endElement(uri, localName, qName);
		
		if(qName.equals("location")){
			
			//locationEntity.setExits(exits);
			locationEntity.setObjects(tempListForObject);
			locationEntity.setObservers(characters);
			locations.add(locationEntity);//put the newly loc in
			
			
			location=false;
			
			locationEntity=null;
			
			tempListForObject=null;
			exits=null;
			characters=null;
		}else if(qName.equals("Description")){
			description=false;
		  
		}else if(qName.equals("Object")){
		     Object=false;
		}else if(qName.equals("game_character")){
			game_character=false;
		}else if(qName.equals("Exit")){
			//mapsForTenLocations.put(locationEntity, maps);
			Exit=false;
		}else if(qName.equals("edge")){
			//mapsForTenLocations.put(locationEntity, maps);
    
         
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		if(description){
			locationEntity.setDescription(new String(ch,start,length));
		}
	
	}

   	public void error(SAXParseException e) {
   		System.out.println("[ERROR] " + "Yikes....:" + e.getMessage());
   		System.exit(0);
   	}

   	public void warning(SAXParseException e) {
   		System.out.println("[ERROR] " + "Warning....:" + e.getMessage());
   	}

   	public void fatalError(SAXParseException e) {
   		System.out.println("[ERROR] " + "Fatal....:" + e.getMessage());
   		System.exit(0);
   	}
   	/**
   	 * Get the collection of locations
   	 * @return
   	 */
	public List<Location> getLocations() {
		return locations;
	}
   	/**
   	 * Get the specific location by the direction
   	 * @return
   	 */
	public static HashMap<String, Location> getLocationsMap(){
		return maps;
	}
	/**
	 * Singleton
	 * @return
	 */
	public static MySaxParser getInstance(){
		if(parser==null){
			parser=new MySaxParser();
			return parser;
		}
		return parser;
	}

}
