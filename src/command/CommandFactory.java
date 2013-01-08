package commands;

import javax.swing.JTextField;

import models.GameModel;
import models.GameState;
import views.state.ViewState;
import controllers.MainController;

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

	public static Command createUpdateGameStateCommand(GameState activeGameState) {
		return new UpdateGameStateCommand(activeGameState, mainController.getGameController());
	}

	public static Command createGameLoopEnabledCommand(boolean enabled) {
		return new GameLoopEnabledCommand(mainController.getGameController(), enabled);
	}
}
