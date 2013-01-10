package service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.GameConfiguration;
import model.core.Difficulty;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * XML Parser that recieves our configuration info 
 */
public class ConfigurationSaxHandler extends AbstractSaxHandler {
    
    private GameConfiguration config;

    public ConfigurationSaxHandler(GameConfiguration config) {
    	this.config = config;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts)
    throws SAXException {
        super.startElement(uri, localName, qName, atts);
        
        switch (qName) {
            case "defaults":
                config.setDefaultName(atts.getValue("name"));
                break;
            case "highscore":
                try {
                    config.setHsAdd(new URL(atts.getValue("add")));
                    config.setHsGet(new URL(atts.getValue("get")));
                    config.setToken(atts.getValue("token"));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ConfigurationSaxHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "difficulty":
                config.addDifficulty(new Difficulty(Integer.valueOf(atts.getValue("id")), atts.getValue("name")));
                break;
            case "invaders":
                config.getDifficulties().get(config.getDifficulties().size()-1).setInvader(
                        Integer.valueOf(atts.getValue("speed")), 
                        Integer.valueOf(atts.getValue("shoot-frequency")));
                break;
            case "player":
                config.getDifficulties().get(config.getDifficulties().size()-1).setPlayer(
                        Integer.valueOf(atts.getValue("speed")), 
                        Integer.valueOf(atts.getValue("shoot-frequency")));
                break;
        }
    }

    public GameConfiguration getConfig() {
        return config;
    }
    
    
    
}
