package command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import command.ICommand;

/**
 * Simple ActionListener that executes an {@link ICommand} upon activation
 */
public class CommandListener implements ActionListener {
	
	private ICommand cmd;

	public CommandListener(ICommand cmd) {
		this.cmd = cmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cmd.execute();
	}

}
