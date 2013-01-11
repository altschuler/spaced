package service.parsing;

import java.util.ArrayList;

import model.GameState;
import model.core.Coordinate;
import model.core.Difficulty;
import model.core.InvaderType;
import model.core.PlayerIndex;
import model.elements.Bonus;
import model.elements.Bunker;
import model.elements.Invader;
import model.elements.Player;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * XML Parser that recieves our level info 
 */
public class GameStateSaxHandler extends AbstractSaxHandler {
    
    private ArrayList<GameState> levels  = new ArrayList<>();
	private GameState currentLevel;

	@Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        super.startElement(uri, localName, qName, atts);

        switch (qName) {
            case "level":
            	this.currentLevel = new GameState(Integer.valueOf(atts.getValue("id")));
                this.levels.add(this.currentLevel);
                break;
            case "player":
            	this.currentLevel.setPlayer(PlayerIndex.One, new Player(Integer.valueOf(atts.getValue("health")), "view/sprites/player.png"));
            	this.currentLevel.getPlayer(PlayerIndex.One).setPosition(new Coordinate(Double.valueOf(atts.getValue("x")), Double.valueOf(atts.getValue("y"))));
                break;
            case "bunker":
                Bunker b = new Bunker(Integer.valueOf(atts.getValue("health")));
                b.setPosition(new Coordinate(Double.valueOf(atts.getValue("x")), Double.valueOf(atts.getValue("y"))));
                this.currentLevel.getBunkers().add(b);
                break;
            case "invader":
                Invader invader = new Invader(
                        InvaderType.valueOf(atts.getValue("type")), 
                        Integer.valueOf(atts.getValue("health")));
                invader.setPosition(new Coordinate(Double.valueOf(atts.getValue("x")), Double.valueOf(atts.getValue("y"))));
                this.currentLevel.getInvaders().add(invader);
                break;
            case "bonus":
            	this.currentLevel.getBonus().add(new Bonus(
                        Integer.valueOf(atts.getValue("points")), 
                        Integer.valueOf(atts.getValue("health"))));
                break;
        }
    }

    public ArrayList<GameState> getLevels() {
            return this.levels;
    }
    
    
}
