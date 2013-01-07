package commands;

import javax.swing.JTextField;

import models.GameModel;

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
