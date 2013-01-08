package controller;

import java.awt.event.KeyEvent;

import javax.swing.Timer;

import model.GameModel;
import model.GameState;
import model.GameStateFactory;
import model.elements.Invader;
import utils.Input;
import view.MainView;

import command.CommandFactory;
import command.CommandListener;

public class GameController extends AbstractController {

	private Timer timer;

	public GameController(MainView gw, GameModel gm) {
		super(gw, gm);

		gm.setActiveGameState(GameStateFactory.createLevelOne()); // TODO this is BAD

	}
	
	private Timer getTimer() {
		if (this.timer == null) {
			this.timer = new Timer(200, new CommandListener(CommandFactory.createUpdateGameStateCommand(gameModel.getActiveGameState())));
		}
		
		return timer;
	}

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
		// TODO implement
		long currentTime = System.currentTimeMillis();
		long timeDelta = currentTime - gameState.getLastUpdateTime();
		
		for (Invader inv : gameState.getInvaders()) {
			//inv.getPosition().x
		}
		
		if (Input.getInstance().isKeyDown(KeyEvent.VK_LEFT)) {
			System.out.println(String.format("Go left %d time", timeDelta));
			
		}
		
		System.out.println(timeDelta);
		gameState.setLastUpdateTime(currentTime);
	}

}
