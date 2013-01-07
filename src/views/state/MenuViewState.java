package views.state;

import java.util.Observable;

import javax.swing.JTextField;

import models.GameModel;
import utils.Utils;

import commands.CommandFactory;

/**
 * @author Simon
 * The main menu view state
 */
@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {
	private JTextField textf;

	public MenuViewState() {
		this.textf = new JTextField();
		this.add(this.textf);
		
		this.add(Utils.createButtonWithStateCommand("Go to board", ViewState.Game));
		this.add(Utils.createButtonWithCommand("Update Text", CommandFactory.createSetGameTextCommand(this.textf)));
	}

	@Override
	public void update(Observable o, Object arg) {
		GameModel gm = (GameModel) o;
		this.textf.setText(gm.getGameText());
	}
}