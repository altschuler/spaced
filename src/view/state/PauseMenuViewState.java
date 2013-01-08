package view.state;

import java.util.Observable;

import javax.swing.JLabel;

import model.GameModel;
import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class PauseMenuViewState extends AbstractViewState {

	public PauseMenuViewState() {
		super();
		
		this.add(new JLabel("Game paused"));
		
		this.add(GuiUtils.createButtonWithStateCommand("Resume game", ViewState.Game));
		this.add(GuiUtils.createButtonWithStateCommand("Back to menu", ViewState.Menu));
	}

	@Override
	public void update(Observable o, Object arg) {
		GameModel gm = (GameModel) o;
	}
}