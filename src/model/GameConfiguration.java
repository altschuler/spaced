package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.core.Difficulty;
import org.xml.sax.SAXException;
import service.parsing.ConfigurationSaxHandler;

public class GameConfiguration {
    
    private static final String XML_FILE = "./Configuration.xml";
    
    private String defaultName;
    private URL hsGet;
    private URL hsAdd;
    private String token;
    private ArrayList<Difficulty> difficulties;

    public GameConfiguration() {
    	this.difficulties = new ArrayList<>();
        this.parseXML();
    }

    public void parseXML() {
            SAXParserFactory factory = SAXParserFactory.newInstance();

            try {
                    InputStream xmlInput = this.getClass().getResourceAsStream(XML_FILE);

                    SAXParser saxParser = factory.newSAXParser();

                    ConfigurationSaxHandler handler = new ConfigurationSaxHandler(this);
                    saxParser.parse(xmlInput, handler);
                    
                    
                    this.defaultName = handler.getConfig().getDefaultName();
                    this.hsGet = handler.getConfig().getHsGet();
                    this.hsAdd = handler.getConfig().getHsAdd();
                    this.difficulties = handler.getConfig().getDifficulties();

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

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public URL getHsGet() {
        return hsGet;
    }

    public void setHsGet(URL hsGet) {
        this.hsGet = hsGet;
    }

    public URL getHsAdd() {
        return hsAdd;
    }

    public void setHsAdd(URL hsAdd) {
        this.hsAdd = hsAdd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Difficulty> getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(ArrayList<Difficulty> difficulties) {
        this.difficulties = difficulties;
    }
    
    public void addDifficulty(Difficulty difficulty) {
        this.difficulties.add(difficulty);
    }

	public Difficulty getDifficulty(int id) {
		for (Difficulty difficulty : this.getDifficulties()) {
			if (difficulty.getId() == id) return difficulty;
		}
		return null;
	}
}
