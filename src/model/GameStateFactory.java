package model;

import model.elements.Bunker;
import model.elements.Player;

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

		gs.setPlayer(new Player());
		gs.getPlayer().getPosition().y = 600;
		
		return gs;
	}

}
