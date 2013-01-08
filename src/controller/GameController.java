package controller;

import java.awt.event.KeyEvent;

import javax.swing.Timer;

import model.GameModel;
import model.GameState;
import model.GameStateFactory;
import model.elements.Player;
import utils.Input;
import view.MainView;
import view.render.GameStateRenderer;
import view.state.GameViewState;
import view.state.ViewState;

import command.CommandFactory;
import command.CommandListener;

public class GameController extends AbstractController {

	private Timer timer;
	private GameStateRenderer renderer;

	public GameController(MainView gw, GameModel gm) {
		super(gw, gm);

		this.renderer = new GameStateRenderer();

		// TODO dont init this here!
		GameState gameState = GameStateFactory.createLevelOne();
		gameState.setLastUpdateTime(System.currentTimeMillis());
		gm.setActiveGameState(gameState);
	}

	private Timer getTimer() {
		if (this.timer == null) {
			this.timer = new Timer(20, new CommandListener(CommandFactory.createUpdateGameStateCommand(gameModel.getActiveGameState())));
		}

		return timer;
	}

	/**
	 * Sets the enabled state of the game loop, effectively starting and
	 * stopping a game session
	 * 
	 * @param enabled
	 *            Whether the loop will run or not
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
		GameViewState gameView;
		
		try {
			gameView = (GameViewState) mainView.getContentPane();
		} 
		catch (ClassCastException e) {
			// Current game view is not GameViewState. Exit early.
			return;
		}
		long currentTime = System.currentTimeMillis();
		long timeDelta = currentTime - gameState.getLastUpdateTime();
		
		// Escape triggers in-game menu
		if (Input.getInstance().isKeyDown(KeyEvent.VK_ESCAPE)) {
			CommandFactory.createSetStateCommand(ViewState.PauseMenu).execute();
		}

		// Update Player
		Player player = gameState.getPlayer();
		if (Input.getInstance().isKeyDown(KeyEvent.VK_LEFT)) {
			player.getPosition().x -= this.distance(timeDelta, 5);
		}
		if (Input.getInstance().isKeyDown(KeyEvent.VK_RIGHT)) {
			player.getPosition().x += this.distance(timeDelta, 5);
		}

		player.getPosition().x = Math.max(0, player.getPosition().x);
		player.getPosition().x = Math.min(GameModel.SCREEN_WIDTH - 48, player.getPosition().x);

		// Render the game state
		this.renderer.render(gameView.getDisplay(), gameState);

		// Update time stamp
		gameState.setLastUpdateTime(currentTime);
	}

	/**
	 * @param timeDelta
	 *            Time passed since last frame
	 * @param speed
	 *            How fast is the object moving
	 * @return A distance dependant on time, thereby avoiding glitches that make
	 *         movement unstable
	 */
	private long distance(long timeDelta, int speed) {
		return timeDelta / 20 * speed;
	}
}
