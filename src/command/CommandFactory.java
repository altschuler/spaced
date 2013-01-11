package command;

import javax.swing.JTextField;

import model.MainModel;
import view.state.ViewState;
import controller.MainController;

/**
 * Factory class which creates commands. It is initialized with the 
 * main {@link MainController} and {@link MainModel} to easily create
 * command which depend on these, from classes that are decoupled from them. 
 * Which is nice.
 */
public class CommandFactory {

	private static MainController mainController;
	private static MainModel gameModel;

	public static void init(MainController gc, MainModel gm) {
		mainController = gc;
		gameModel = gm;
	}

	public static Command createSetStateCommand(ViewState state) {
		return new SetViewStateCommand(mainController, state);
	}

	public static Command createSetPlayerNameCommand(JTextField gameTextField) {
		return new SetPlayerNameCommand(gameModel, gameTextField);
	}

	public static Command createUpdateGameStateCommand() {
		return new UpdateGameStateCommand(gameModel, mainController.getGameController());
	}

	public static Command createGameLoopEnabledCommand(boolean enabled) {
		return new GameLoopEnabledCommand(mainController.getGameController(), gameModel, enabled);
	}

	public static Command createSetDifficultyCommand(int id) {
		return new SetDifficultyCommand(gameModel, id);
	}

	public static Command createSetDifficultyCommand(String name) {
		return new SetDifficultyCommand(gameModel, name);
	}
        
        public static Command createStartNewGameCommand() {
		return new StartNewGameCommand(mainController);
	}

	public static Command createLoadNextLevelCommand() {
		return new LoadNextLevelCommand(mainController);
	}
}
