package service;

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
	private Difficulty difficulty;
	private GameState currentLevel;

    public GameStateSaxHandler(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

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
            	this.currentLevel.getPlayer(PlayerIndex.One).setSpeed(difficulty.getPlayerSpeed());
            	this.currentLevel.getPlayer(PlayerIndex.One).setMaxShootFrequency(difficulty.getPlayerShootFreq());
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
                invader.setSpeed(this.difficulty.getInvaderSpeed());
                this.currentLevel.getInvaders().add(invader);
                break;
            case "bonus":
            	this.currentLevel.getBonuss().add(new Bonus(
                        Integer.valueOf(atts.getValue("points")), 
                        Integer.valueOf(atts.getValue("health"))));
                break;
        }
    }

    public ArrayList<GameState> getLevels() {
            return this.levels;
    }
    
    
}
