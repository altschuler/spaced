package commands;

import views.state.ViewState;
import controllers.GameController;

/**
 * @author Simon
 * Command to change the top-level view state
 */
public class SetViewStateCommand extends Command {

	private ViewState state;
	private GameController ctrl;

	public SetViewStateCommand(GameController ctrl, ViewState state) {
		this.ctrl = ctrl;
		this.state = state;
	}

	@Override
	public void execute() {
		this.ctrl.setViewState(this.state);
		super.execute();
	}

}
