package view.state;

import java.util.Observable;

import javax.swing.JTextField;

import model.GameModel;
import utils.GuiUtils;

import command.CommandFactory;

/**
 * First splash view states shown when initializing the game
 */
@SuppressWarnings("serial")
public class SplashViewState extends AbstractViewState {

	private JTextField textf;
	
	public SplashViewState() {
		super();
		
		this.textf = new JTextField("hejehejehej");
		this.add(this.textf);
		
		this.add(GuiUtils.createButtonWithCommand("GO", CommandFactory.createSetPlayerNameCommand(this.textf)
														.chain(CommandFactory.createSetStateCommand(ViewState.Menu))));
	}
	
	@Override 
	public void update(Observable o, Object arg) {
		GameModel gameModel = (GameModel) o;
		this.textf.setText(gameModel.getGameConfig().getDefaultName());
	}

}
