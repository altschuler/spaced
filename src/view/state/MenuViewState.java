package views.state;

import java.util.Observable;

import javax.swing.JLabel;

import models.GameModel;
import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {

	private JLabel label;

	public MenuViewState() {
		this.label = new JLabel();
		this.add(this.label);
		
		this.add(GuiUtils.createButtonWithStateCommand("Go to board", ViewState.Game));
	}

	@Override
	public void update(Observable o, Object arg) {
		GameModel gm = (GameModel) o;
		this.label.setText(String.format("Hi %s!", gm.getPlayerName()));
	}
}