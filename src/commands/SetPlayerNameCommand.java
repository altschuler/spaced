package commands;

import javax.swing.JTextField;

import models.GameModel;

/**
 * @author Simon
 * This is a dummy test class and has no real use, but sets a property on the model.
 */
public class SetPlayerNameCommand extends Command {
	
	private JTextField playerNameField;
	private GameModel gm;
	
	public SetPlayerNameCommand(GameModel gm, JTextField playerNameField) {
		this.playerNameField = playerNameField;
		this.gm = gm;
	}

	@Override
	public void execute() {
		this.gm.setPlayerName(this.playerNameField.getText());
		
		super.execute();
	}

}
