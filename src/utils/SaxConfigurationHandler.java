package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.GameConfiguration;
import model.core.Difficulty;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * XML Parser that recieves our configuration info 
 */
public class SaxConfigurationHandler extends DefaultHandler {
    
    private Stack<String> elementStack = new Stack<>();
    
    private GameConfiguration config = new GameConfiguration();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException {

        this.elementStack.push(qName);
        
        switch (qName) {
            case "default-name":
                
                break;
            case "highscore":
                try {
                    config.setHsAdd(new URL(attributes.getValue("add")));
                    config.setHsGet(new URL(attributes.getValue("get")));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(SaxConfigurationHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "difficulty":
                config.addDifficulty(new Difficulty(Integer.valueOf(attributes.getValue("id")), attributes.getValue("name")));
                break;
            case "invader":
                config.getDifficulties().get(config.getDifficulties().size()-1).setInvader(
                        Integer.valueOf(attributes.getValue("speed")), 
                        Integer.valueOf(attributes.getValue("shoot-frequency")));
                break;
            case "player":
                config.getDifficulties().get(config.getDifficulties().size()-1).setPlayer(
                        Integer.valueOf(attributes.getValue("speed")), 
                        Integer.valueOf(attributes.getValue("shoot-frequency")));
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

    public GameConfiguration getConfig() {
        return config;
    }
    
    
    
}
