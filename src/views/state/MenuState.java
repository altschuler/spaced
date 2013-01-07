package views.state;

import java.util.Observable;

import javax.swing.JTextField;

import commands.CommandFactory;
import commands.CommandListener;

import models.GameModel;
import utils.Utils;

@SuppressWarnings("serial")
public class MenuState extends AbstractViewState {
	private JTextField textf;

	public MenuState() {
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