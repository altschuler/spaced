package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import service.GameStateSaxHandler;

import com.rits.cloning.Cloner;

/**
 * This Factory is responsible for creating {@link GameState}s that are levels
 */
public class GameStateFactory {
	
	private static final String XML_FILE = "./GameStates.xml";
	private static ArrayList<GameState> levels;
	private static GameModel gameModel;

	public static void init(GameModel $gm) {
		gameModel = $gm;
		
		parseXML();
	}

	private static void parseXML() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		try {
			InputStream xmlInput = GameStateFactory.class.getResourceAsStream(XML_FILE);

			SAXParser saxParser = factory.newSAXParser();

			GameStateSaxHandler handler = new GameStateSaxHandler(gameModel.getActiveDifficulty());
			saxParser.parse(xmlInput, handler);

			levels = handler.getLevels();

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

	static public GameState getLevel(int id) {
		return (GameState) new Cloner().deepClone(levels.get(id));
	}
	
	static public boolean levelExists(int id) {
		try {
			levels.get(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
