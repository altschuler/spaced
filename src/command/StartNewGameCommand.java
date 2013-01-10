package command;

import controller.MainController;

public class StartNewGameCommand extends Command{

	private MainController mainController;

	public StartNewGameCommand(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void execute() {
		this.mainController.getFlowController().loadLevel(0, false);
		
		super.execute();
	}
}
