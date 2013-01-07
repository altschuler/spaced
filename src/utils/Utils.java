package utils;

import javax.swing.JButton;

import views.state.ViewState;

import commands.CommandFactory;
import commands.CommandListener;
import commands.ICommand;

public class Utils {
	
	public static JButton createButtonWithCommand(String label, ICommand cmd) {
		JButton btn = new JButton(label);
		btn.addActionListener(new CommandListener(cmd));
		return btn;
	}

	public static JButton createButtonWithStateCommand(String label, ViewState state) {
		JButton btn = new JButton(label);
		btn.addActionListener(new CommandListener(CommandFactory.createSetStateCommand(state)));
		return btn;
	}
}
