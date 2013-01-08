package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.elements.Bunker;
import model.elements.Invader;
import model.elements.Player;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utils.SaxHandler;

/**
 * This Factory is responsible for creating {@link GameState}s that are levels
 */
public class GameStateFactory {
        
    private static final String XML_FILE = "./GameStateLevels.xml";
    
    public GameStateFactory() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        try {
            //System.out.println(this.getClass().getResource(XML_FILE).toString());
            //FileInputStream xmlInput = new FileInputStream(this.getClass().getClassLoader().getResource(XML_FILE).toString().substring(5));
            InputStream xmlInput = this.getClass().getResourceAsStream(XML_FILE);
            
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler   = new SaxHandler();
            saxParser.parse(xmlInput, handler);
    
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Cannot find file: "+XML_FILE);
            System.exit(1);
        } catch (ParserConfigurationException e) {
            System.out.println("ERROR: ParserConfigurationException thrown");
            System.exit(1);
        } catch (SAXException e) {
            System.out.println("ERROR: SAXException thrown");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("ERROR: IOException thrown");
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
