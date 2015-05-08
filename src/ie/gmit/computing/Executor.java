package ie.gmit.computing;

import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

import org.xml.sax.SAXException;

public class Executor {
    protected static boolean GAME_STATE=true;
    static final String filename = "conf/myrule.fcl";
    
	public static void main(String[] args) {
		MySaxParser parser=new MySaxParser();
		List<Location> list=parser.getLocations();
		Thread charcThread;
		
		
		FIS fis = FIS.load(filename, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + filename + "'");
			System.exit(1);
		}

		// Get default function block
		FunctionBlock fb = fis.getFunctionBlock(null);		
		
        for(int i=0;i<list.size();i++){
        	 if(i%3==0){
				  
        		 Iterator<RealCharacters> characters=list.get(i).getObservers().iterator();
        		 
        		 while(characters.hasNext()){
        			 
        				 charcThread=new Thread(characters.next());
        				 charcThread.setPriority(3);
          				  charcThread.start();
			}
		} 
			
        }
		
        
		Myplayer myPlayer=new Myplayer("hero", "Hero",null, "fighting",list.get(list.size()-1));
		myPlayer.setFb(fb);
		Thread myThread=new Thread(myPlayer);
		 
		myThread.start();
		

		
	}
}