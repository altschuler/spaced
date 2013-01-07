package commands;

import javax.swing.JTextField;

import models.GameModel;
import views.state.ViewState;
import controllers.GameController;

/**
 * Factory class which creates commands. It is initialized with the 
 * main {@link GameController} and {@link GameModel} to easily create
 * command which depend on these, from classes that are decoupled from them. 
 * Which is nice.
 */
public class CommandFactory {

	public static GameController gc;
	public static GameModel gm;

	public static void init(GameController $gc, GameModel $gm) {
		CommandFactory.gc = $gc;
		CommandFactory.gm = $gm;
	}

	public static Command createSetStateCommand(ViewState state) {
		return new SetViewStateCommand(gc, state);
	}

	public static Command createSetPlayerNameCommand(JTextField gameTextField) {
		return new SetPlayerNameCommand(gm, gameTextField);
	}
}
