package views.state;

import javax.swing.JTextField;

import utils.Utils;

import commands.CommandFactory;

@SuppressWarnings("serial")
public class SplashViewState extends AbstractViewState {

	private JTextField textf;
	
	public SplashViewState() {
		super();
		this.textf = new JTextField("Anonymous");
		this.add(this.textf);
		
		this.add(Utils.createButtonWithCommand("GO", CommandFactory.createSetPlayerNameCommand(this.textf)
													.chain(CommandFactory.createSetStateCommand(ViewState.Menu))));
	}

}
