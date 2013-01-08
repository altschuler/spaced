package commands;

import views.state.ViewState;
import controllers.MainController;

/**
 * Command to change the top-level view state
 */
public class SetViewStateCommand extends Command {

	private ViewState state;
	private MainController mainController;

	public SetViewStateCommand(MainController ctrl, ViewState state) {
		this.mainController = ctrl;
		this.state = state;
	}

	@Override
	public void execute() {
		this.mainController.getStateController().setViewState(this.state);
		
		super.execute();
	}

}
