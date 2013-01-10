package command;

import javax.swing.JTextField;

import model.GameModel;
import model.GameState;
import view.state.ViewState;
import controller.MainController;

/**
 * Factory class which creates commands. It is initialized with the 
 * main {@link MainController} and {@link GameModel} to easily create
 * command which depend on these, from classes that are decoupled from them. 
 * Which is nice.
 */
public class CommandFactory {

	public static MainController mainController;
	public static GameModel gameModel;

	public static void init(MainController $gc, GameModel $gm) {
		mainController = $gc;
		gameModel = $gm;
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

	public static Command createStartNewGameCommand() {
		return new StartNewGameCommand(mainController);
	}

	public static Command createLoadNextLevelCommand() {
		return new LoadNextLevelCommand(mainController);
	}
}
