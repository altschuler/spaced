package command;

import model.GameModel;
import controller.GameController;


public class UpdateGameStateCommand extends Command {
	
	private GameModel gameModel;
	private GameController gameController;

	public UpdateGameStateCommand(GameModel gameModel, GameController gc) {
		this.gameController = gc;
		this.gameModel = gameModel;
	}
	
	@Override
	public void execute() {
		this.gameController.updateGameState(this.gameModel.getActiveGameState());
		
		super.execute();
	}
}
