package utils;

import javax.swing.JButton;

import views.state.ViewState;

import commands.CommandFactory;
import commands.CommandListener;
import commands.ICommand;

public class Utils {
	
	/**
	 * @param label The label to be displayed on the button
	 * @param cmd The command to be executed when the button is activated
	 * @return A new JButton instance
	 * Creates a button that will execute a given command upon activation
	 */
	public static JButton createButtonWithCommand(String label, ICommand cmd) {
		JButton btn = new JButton(label);
		btn.addActionListener(new CommandListener(cmd));
		return btn;
	}

	/**
	 * @param label The label to be displayed on the button
	 * @param state The state to change to when button is activated
	 * @return A new JButton instance
	 * Creates a button that will change the view state upon activation
	 */
	public static JButton createButtonWithStateCommand(String label, ViewState state) {
		JButton btn = new JButton(label);
		btn.addActionListener(new CommandListener(CommandFactory.createSetStateCommand(state)));
		return btn;
	}
}
