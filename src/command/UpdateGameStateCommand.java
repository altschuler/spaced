package commands;

import models.GameState;
import controllers.GameController;


public class UpdateGameStateCommand extends Command {
	
	private GameState gameState;
	private GameController gameController;

	public UpdateGameStateCommand(GameState gs, GameController gc) {
		this.gameController = gc;
		this.gameState = gs;
	}
	
	@Override
	public void execute() {
		this.gameController.updateGameState(this.gameState);
		
		super.execute();
	}
}
