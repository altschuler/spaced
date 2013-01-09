package view.state;

import java.util.Observable;

import javax.swing.JLabel;

import command.CommandFactory;

import model.GameModel;
import utils.GuiUtils;

/**
 * The main menu view state
 */
@SuppressWarnings("serial")
public class MenuViewState extends AbstractViewState {

	private JLabel label;

	public MenuViewState() {
		super();
		this.label = new JLabel();
		this.add(this.label);
		
		this.add(GuiUtils.createButtonWithCommand("Start game", 
				CommandFactory.createLoadLevelCommand(0).chain(CommandFactory.createSetStateCommand(ViewState.Game))));
	}

	@Override
	public void update(Observable o, Object arg) {
		GameModel gm = (GameModel) o;
		this.label.setText(String.format("Hi %s!", gm.getPlayerName()));
	}
}