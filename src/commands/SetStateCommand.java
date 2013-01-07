package commands;

import views.state.ViewState;
import controlllers.GameController;

public class SetStateCommand implements ICommand {

	private ViewState state;
	private GameController ctrl;

	public SetStateCommand(GameController ctrl, ViewState state) {
		this.ctrl = ctrl;
		this.state = state;
	}

	@Override
	public void execute() {
		this.ctrl.setGameState(this.state);
	}

}
