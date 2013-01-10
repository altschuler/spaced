package utils;

import java.util.ArrayList;
import java.util.Stack;
import model.GameState;
import model.core.InvaderType;
import model.core.PlayerIndex;
import model.elements.Bonus;
import model.elements.Bunker;
import model.elements.Invader;
import model.elements.Player;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * XML Parser that recieves our level info 
 */
public class SaxGameStateHandler extends DefaultHandler {
    
    private ArrayList<GameState> levels  = new ArrayList<GameState>();
    
    private Stack<String> elementStack = new Stack<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException {

        this.elementStack.push(qName);

        switch (qName) {
            case "level":
                levels.add(new GameState(Integer.valueOf(attributes.getValue("id"))));
                break;
            case "player":
                levels.get(levels.size()-1).setPlayer(PlayerIndex.One, new Player(Integer.valueOf(attributes.getValue("health")),"view/sprites/player.png"));
                levels.get(levels.size()-1).getPlayer(PlayerIndex.One).getPosition().x = Double.valueOf(attributes.getValue("x"));
                levels.get(levels.size()-1).getPlayer(PlayerIndex.One).getPosition().y = Double.valueOf(attributes.getValue("y"));
                break;
            case "bunker":
                Bunker bunker = new Bunker(Integer.valueOf(attributes.getValue("health")));
                bunker.getPosition().x = Double.valueOf(attributes.getValue("x"));
                bunker.getPosition().y = Double.valueOf(attributes.getValue("y"));
                levels.get(levels.size()-1).getBunkers().add(bunker);
                break;
            case "invader":
                Invader invader = new Invader(
                        InvaderType.valueOf(attributes.getValue("type")), 
                        Integer.valueOf(attributes.getValue("health"))); //the constructor demands a String
                invader.getPosition().x = Double.valueOf(attributes.getValue("x"));
                invader.getPosition().y = Double.valueOf(attributes.getValue("y"));
                levels.get(levels.size()-1).getInvaders().add(invader);
                break;
            case "bonus":
                levels.get(levels.size()-1).getBonuss().add(new Bonus(
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
    
    
}
