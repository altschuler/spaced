package models;

import models.elements.Bunker;

/**
 * This Factory is responsible for creating {@link GameState}s that are levels
 */
public class GameStateFactory {

	static public GameState createLevelOne() {
		GameState gs = new GameState();

		Bunker b1 = new Bunker();
		b1.getPosition().x = 50;
		b1.getPosition().y = 450;

		gs.getBunkers().add(b1);

		return gs;
	}

}
