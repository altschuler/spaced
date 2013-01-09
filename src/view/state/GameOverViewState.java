package view.state;

import java.util.Observable;

import javax.swing.JLabel;

import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class GameOverViewState extends AbstractViewState {

	public GameOverViewState() {
		super();
		
		this.add(new JLabel("GAME OVER"));
		
		this.add(GuiUtils.createButtonWithStateCommand("Back to menu", ViewState.Menu));
	}

	@Override
	public void update(Observable o, Object arg) {
		//GameModel gm = (GameModel) o;
		//TODO show score etc
	}
}