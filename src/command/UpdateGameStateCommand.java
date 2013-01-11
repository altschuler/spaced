package command;

import model.MainModel;
import controller.GameController;


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
