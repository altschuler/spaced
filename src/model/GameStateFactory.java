package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.elements.Bunker;
import model.elements.Invader;
import model.elements.InvaderType;
import model.elements.Player;
import model.elements.PlayerIndex;

import org.xml.sax.SAXException;

import com.rits.cloning.Cloner;

import utils.SaxGameStateHandler;

/**
 * This Factory is responsible for creating {@link GameState}s that are levels
 */
public class GameStateFactory {

	private static final String XML_FILE = "./GameStates.xml";
	private ArrayList<GameState> levels;

	public GameStateFactory() {
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			InputStream xmlInput = this.getClass().getResourceAsStream(XML_FILE);

			SAXParser saxParser = factory.newSAXParser();

			SaxGameStateHandler handler = new SaxGameStateHandler();
			saxParser.parse(xmlInput, handler);

			this.levels = handler.getLevels();

			levels.get(0).printInfo();

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

	public GameState getLevel(int id) {
		return (GameState) new Cloner().deepClone(levels.get(id));
	}

	static public GameState createLevelOne() {
		GameState gs = new GameState(0);

		// Bunkers
		Bunker b1 = new Bunker();
		b1.getPosition().x = 50;
		b1.getPosition().y = 450;
		gs.getBunkers().add(b1);

		// Player
		gs.setPlayer(PlayerIndex.One, new Player(3));
		gs.getPlayer(PlayerIndex.One).getPosition().x = 250 - 24;
		gs.getPlayer(PlayerIndex.One).getPosition().y = 600;

		// Invaders
		int invaderCounter = 0, columnsOfInvaders = 7, rowsOfInvaders = 3, widthBetweenInvaders = 55, heightBetweenInvaders = 50;
		int xInvaderStart = 50, yInvaderStart = 50;
		int invaderHealth = 2;
		for (int i = 0; i < columnsOfInvaders; i++) {
			for (int j = 0; j < rowsOfInvaders; j++) {
				gs.getInvaders().add(new Invader(InvaderType.A, invaderHealth, "view/sprites/invader2.png"));
				gs.getInvaders().get(invaderCounter).getPosition().x = i * widthBetweenInvaders + xInvaderStart;
				gs.getInvaders().get(invaderCounter).getPosition().y = j * heightBetweenInvaders + yInvaderStart;
				invaderCounter++;
			}

		}

		return gs;
	}

}
