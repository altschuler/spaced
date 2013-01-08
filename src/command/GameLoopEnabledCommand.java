package command;

import controller.GameController;

public class GameLoopEnabledCommand extends Command {

	private boolean enabled;
	private GameController gameController;

	public GameLoopEnabledCommand(GameController gameController, boolean enabled) {
		this.gameController = gameController;
		this.enabled = enabled;
	}
	
	@Override
	public void execute() {
		this.gameController.setGameLoopEnabled(this.enabled);
		
		super.execute();
	}

}
