package commands;

import javax.swing.JTextField;

import models.GameModel;

/**
 * @author Simon
 * This is a dummy test class and has no real use, but sets a property on the model.
 */
public class SetGameTextCommand implements ICommand {
	
	private JTextField gameTextField;
	private GameModel gm;
	
	public SetGameTextCommand(GameModel gm, JTextField gameTextField) {
		this.gameTextField = gameTextField;
		this.gm = gm;
	}

	@Override
	public void execute() {
		this.gm.setGameText(this.gameTextField.getText());
	}

}
