package commands;

import javax.swing.JTextField;

import models.GameModel;
import views.state.ViewState;
import controlllers.GameController;

public class CommandFactory {

	public static GameController gc;
	public static GameModel gm;

	public static void init(GameController _gc, GameModel _gm) {
		CommandFactory.gc = _gc;
		CommandFactory.gm = _gm;
		
	}

	public static ICommand createSetStateCommand(ViewState state) {
		return new SetStateCommand(gc, state);
	}

	public static ICommand createSetGameTextCommand(JTextField gameTextField) {
		return new SetGameTextCommand(gm, gameTextField);
	}
}
