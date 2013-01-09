package utils;

import java.util.ArrayList;
import java.util.Stack;
import model.GameState;
import model.elements.Bonus;
import model.elements.Bunker;
import model.elements.Invader;
import model.elements.InvaderType;
import model.elements.Player;
import model.elements.PlayerIndex;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * XML Parser that recieves our level info 
 */
public class SaxGameStateHandler extends DefaultHandler {
    
    private ArrayList<GameState> levels  = new ArrayList<GameState>();
    
    private int counter = 0;
    private Stack<String> elementStack = new Stack<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException {

        this.elementStack.push(qName);
        
        int health = 0;

        switch (qName) {
            case "level":
                getLevels().add(new GameState(Integer.valueOf(attributes.getValue("id"))));
                break;
            case "player":
                getLevels().get(counter).setPlayer(PlayerIndex.One, new Player(Integer.valueOf(attributes.getValue("health"))));
                getLevels().get(counter).getPlayer(PlayerIndex.One).getPosition().x = Double.valueOf(attributes.getValue("x"));
                getLevels().get(counter).getPlayer(PlayerIndex.One).getPosition().y = Double.valueOf(attributes.getValue("y"));
                break;
            case "bunker":
                Bunker b = new Bunker();
                b.getPosition().x = Double.valueOf(attributes.getValue("x"));
                b.getPosition().y = Double.valueOf(attributes.getValue("y"));
                getLevels().get(counter).getBunkers().add(b);
                break;
            case "invader":
                Invader invader = new Invader(
                        InvaderType.valueOf(attributes.getValue("type")), 
                        Integer.valueOf(attributes.getValue("health")));
                invader.getPosition().x = Double.valueOf(attributes.getValue("x"));
                invader.getPosition().y = Double.valueOf(attributes.getValue("y"));
                getLevels().get(counter).getInvaders().add(invader);
                break;
            case "bonus":
                getLevels().get(counter).getBonuss().add(new Bonus(
                        Integer.valueOf(attributes.getValue("points")), 
                        Integer.valueOf(attributes.getValue("health"))));
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    throws SAXException {

        this.elementStack.pop();
        
    }

    @Override
    public void characters(char ch[], int start, int length)
    throws SAXException {
        //System.out.println("start characters : " + new String(ch, start, length));
    }

    private String currentElement() {
        return this.elementStack.peek();
    }

    private String currentElementParent() {
        if(this.elementStack.size() < 2) return null;
        return this.elementStack.get(this.elementStack.size()-2);
    }
    
    @Override
    public void startDocument() throws SAXException {
            
    }

    @Override
    public void endDocument() throws SAXException {
        
    }

	public ArrayList<GameState> getLevels() {
		return levels;
	}
    
//    private ArrayList<String> file;
//    private ArrayList<Object> level = new ArrayList();
//    private ArrayList<ArrayList> levels = new ArrayList();
//    
//    public ArrayList parseFile() {
//        
//        for (int i=0; i < file.size(); i++) {
//            if (file.get(i).equals("<players>")){
//                Boolean players = true;
//                int c = 0;
//                while(players) {
//                    c++; // Counter
//                    
//                    level.add(new Player());
//                    
//                    // Check if end of tag
//                    players = (file.get(i+c).equals("</players>")) ? false : true;
//                }
//            }
//        }
//        
//        levels.add(level);
//        
//        return levels;
//    }
//            
//    
//    public ArrayList getFile() {
//        return file;
//    }
//    
//    private void printFile() {
//        if (file != null) {
//            int c = 0;
//            for (String s : file) {
//                // Indent
//                c = s.contains("/") ? c-1 : c+1;
//                for (int i = 0; i < c; i++) {
//                    System.out.print("\t");
//                }
//
//                System.out.println(s);
//            }
//        } else {
//            System.out.println("ERROR: null file");
//            System.exit(1);
//        }
//    }
    
    
}
