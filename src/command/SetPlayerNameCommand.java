package command;

import javax.swing.JTextField;

import model.MainModel;
import model.core.PlayerIndex;

/**
 * This is a dummy test class and has no real use, but sets a property on the model.
 */
public class SetPlayerNameCommand extends Command {
	
	private JTextField playerNameField;
	private MainModel gm;
	
	public SetPlayerNameCommand(MainModel gm, JTextField playerNameField) {
		this.playerNameField = playerNameField;
		this.gm = gm;
	}

	@Override
	public void execute() {
		this.gm.setPlayerName(PlayerIndex.One, this.playerNameField.getText());
		
		super.execute();
	}

}
