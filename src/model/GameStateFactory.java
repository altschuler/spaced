package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import service.parsing.GameStateSaxHandler;

import com.rits.cloning.Cloner;

/**
 * This Factory is responsible for creating {@link GameState}s that are levels
 */
public class GameStateFactory {
	
	private static final String XML_FILE = "./GameStates.xml"; // TODO maybe move this to config file>
	private static ArrayList<GameState> levels;

	public static void init() {
		parseXML();
	}

	private static void parseXML() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		try {
			InputStream xmlInput = GameStateFactory.class.getResourceAsStream(XML_FILE);

			SAXParser saxParser = factory.newSAXParser();

			GameStateSaxHandler handler = new GameStateSaxHandler();
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
		for (GameState gs : levels) {
			if (gs.getId() == id) return (GameState) new Cloner().deepClone(gs);
		}
		return null;
	}
	
	static public boolean levelExists(int id) {
		for (GameState gameState : levels) {
			if (gameState.getId() == id) return true;
		}
		return false;
	}
}
