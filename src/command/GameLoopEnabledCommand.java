package command;

import model.MainModel;
import controller.GameController;

/**
 * Starts and stops the game-loop.
 * 
 *  It does so by calling {@link controller.GameController}'s method .setGameLoopEnabled(boolean enabled) and thus starting/stopping the Timer in the GameController
 */
public class GameLoopEnabledCommand extends Command {

	private boolean enabled;
	private GameController gameController;
	private MainModel gameModel;

	public GameLoopEnabledCommand(GameController gameController, MainModel gameModel, boolean enabled) {
		this.gameController = gameController;
		this.gameModel = gameModel;
		this.enabled = enabled;
	}

	@Override
	public void execute() {
		// Make sure we dont jump in time
		if (this.enabled) {
			this.gameModel.getActiveGameState().setLastUpdateTime(System.currentTimeMillis());
		}
		
		this.gameController.setGameLoopEnabled(this.enabled);

		super.execute();
	}
}
