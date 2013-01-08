package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.elements.Bunker;
import model.elements.Invader;
import model.elements.Player;
import utils.XMLParser;

/**
 * This Factory is responsible for creating {@link GameState}s that are levels
 */
public class GameStateFactory {
        
    private static final String XML_FILE = "GameStateLevels.xml";
    
    public GameStateFactory() {
        try {
            InputStream xmlInput = new FileInputStream(XML_FILE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: Cannot find file: "+XML_FILE);
            System.exit(1);
        }
    }

    static public GameState createLevelOne() {
            GameState gs = new GameState();

            // Bunkers
            Bunker b1 = new Bunker();
            b1.getPosition().x = 50;
            b1.getPosition().y = 450;
            gs.getBunkers().add(b1);

            // Player
            gs.setPlayer(new Player());
            gs.getPlayer().getPosition().y = 600;

            // Invaders
            gs.getInvaders().add(new Invader(1));
            gs.getInvaders().add(new Invader(1));
            gs.getInvaders().add(new Invader(1));
            gs.getInvaders().add(new Invader(1));
            gs.getInvaders().add(new Invader(1));

            return gs;
    }

}
