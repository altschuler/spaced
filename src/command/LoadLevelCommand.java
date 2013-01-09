package command;

import controller.MainController;

public class LoadLevelCommand extends Command{

	private MainController mainController;
	private int levelId;

	public LoadLevelCommand(MainController mainController, int levelId) {
		this.mainController = mainController;
		this.levelId = levelId;
	}

	@Override
	public void execute() {
		this.mainController.getFlowController().loadLevel(this.levelId);
		
		super.execute();
	}
}
