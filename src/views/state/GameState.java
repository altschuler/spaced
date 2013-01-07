package views.state;

import java.util.Observable;

import javax.swing.JLabel;

import utils.Utils;

import models.GameModel;

@SuppressWarnings("serial")
public class GameState extends AbstractViewState {
	
	private JLabel currentPlayerLabel;
	
	public GameState() {
		this.add(Utils.createButtonWithStateCommand("Go back", ViewState.Menu));
		
		// player label
		this.currentPlayerLabel = new JLabel();
		this.add(this.currentPlayerLabel);
	}

	@Override
	public void update(Observable o, Object arg) {
		GameModel gm = (GameModel) o;
			
		this.currentPlayerLabel.setText(String.format("The text is: %s", gm.getGameText()));
	}
}
