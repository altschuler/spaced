package command;

import model.MainModel;
import controller.GameController;


/**
 * Tells the {@link controller.GameController} to update it's current {@link model.GameState}.
 */
public class UpdateGameStateCommand extends Command {
	
	private MainModel gameModel;
	private GameController gameController;

	public UpdateGameStateCommand(MainModel gameModel, GameController gc) {
		this.gameController = gc;
		this.gameModel = gameModel;
	}
	
	@Override
	public void execute() {
		this.gameController.updateGameState(this.gameModel.getActiveGameState());
		
		super.execute();
	}
}
