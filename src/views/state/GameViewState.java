package views.state;

import java.util.Observable;

import javax.swing.JLabel;

import utils.GuiUtils;

import models.GameModel;

/**
 * The main game view. This is where the actual game is rendered.
 */
@SuppressWarnings("serial")
public class GameViewState extends AbstractViewState {
	
	private JLabel currentPlayerLabel;
	
	public GameViewState() {
		super();
		this.add(GuiUtils.createButtonWithStateCommand("Go back", ViewState.Menu));
		
		// player label
		this.currentPlayerLabel = new JLabel();
		this.add(this.currentPlayerLabel);
	}

	@Override
	public void update(Observable o, Object arg) {
		GameModel gm = (GameModel) o;
		this.currentPlayerLabel.setText(String.format("The text is: %s", gm.getPlayerName()));
	}
}
