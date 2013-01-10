package command;

import controller.MainController;

public class LoadNextLevelCommand extends Command{

	private MainController mainController;

	public LoadNextLevelCommand(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void execute() {
		this.mainController.getFlowController().loadNextLevel();
		
		super.execute();
	}
}
