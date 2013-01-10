package command;

import model.GameModel;
import controller.GameController;

public class GameLoopEnabledCommand extends Command {

	private boolean enabled;
	private GameController gameController;
	private GameModel gameModel;

	public GameLoopEnabledCommand(GameController gameController, GameModel gameModel, boolean enabled) {
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
