package command;

import javax.swing.JTextField;

import model.MainModel;
import model.core.PlayerIndex;

/**
 * Sets the player's name on the {@link model.MainModel}.
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
