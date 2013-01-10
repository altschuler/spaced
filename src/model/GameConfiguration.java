package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.core.Difficulty;
import org.xml.sax.SAXException;
import utils.SaxGameStateHandler;

class GameConfiguration {
    
    private static final String XML_FILE = "./Configuration.xml";
    
    private String defaultName;
    private ArrayList<Difficulty> difficulties;

    public GameConfiguration() {
        this.parseXML();   
    }

    public void parseXML() {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            try {
                    InputStream xmlInput = this.getClass().getResourceAsStream(XML_FILE);

                    SAXParser saxParser = factory.newSAXParser();

                    SaxGameStateHandler handler = new SaxGameStateHandler();
                    saxParser.parse(xmlInput, handler);

                    this.levels = handler.getLevels();

            } catch (FileNotFoundException e) {
                    System.out.println("ERROR: Cannot find file: " + XML_FILE);
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
    
}
