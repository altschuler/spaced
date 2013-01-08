package view.state;

import javax.swing.JTextField;

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
		
		this.textf = new JTextField("Anonymous");
		this.add(this.textf);
		
		this.add(GuiUtils.createButtonWithCommand("GO", CommandFactory.createSetPlayerNameCommand(this.textf)
														.chain(CommandFactory.createSetStateCommand(ViewState.Menu))));
	}

}
