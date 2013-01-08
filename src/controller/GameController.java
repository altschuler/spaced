package controller;

import java.awt.event.KeyEvent;

import javax.swing.Timer;

import model.GameModel;
import model.GameState;
import model.GameStateFactory;
import utils.Input;
import view.MainView;

import command.CommandFactory;
import command.CommandListener;

public class GameController extends AbstractController {

	private Timer timer;

	public GameController(MainView gw, GameModel gm) {
		super(gw, gm);

		GameState gameState = GameStateFactory.createLevelOne();
		gameState.setLastUpdateTime(System.currentTimeMillis());
		gm.setActiveGameState(gameState);
	}
	
	private Timer getTimer() {
		if (this.timer == null) {
			this.timer = new Timer(100, new CommandListener(CommandFactory.createUpdateGameStateCommand(gameModel.getActiveGameState())));
		}
		
		return timer;
	}

	/**
	 * Sets the enabled state of the game loop, effectively starting and stopping a game session
	 * @param enabled Whether the loop will run or not
	 */
	public void setGameLoopEnabled(boolean enabled) {
		if (enabled) {
			this.getTimer().start();
		} else {
			this.getTimer().stop();
		}
	}

	/**
	 * @param gameState
	 *            The {@link GameState} whose values are to be stepped forward
	 *            in time.
	 */
	public void updateGameState(GameState gameState) {

		long currentTime = System.currentTimeMillis();
		long timeDelta = currentTime - gameState.getLastUpdateTime();
		
		// Update Player
		if (Input.getInstance().isKeyDown(KeyEvent.VK_LEFT)) {
			gameState.getPlayer().getPosition().x -= this.distance(timeDelta, 5);
		}
		else if (Input.getInstance().isKeyDown(KeyEvent.VK_RIGHT)) {
			gameState.getPlayer().getPosition().x += this.distance(timeDelta, 5);
		}
		
		System.out.println(String.format("Player is at %s", gameState.getPlayer().getPosition().toString()));
		
		// Update time stamp
		gameState.setLastUpdateTime(currentTime);
	}

	/**
	 * @param timeDelta Time passed since last frame
	 * @param speed How fast is the object moving
	 * @return A distance dependant on time, thereby avoiding glitches that make movement unstable
	 */
	private long distance(long timeDelta, int speed) {
		return timeDelta/100 * speed;
	}
}
