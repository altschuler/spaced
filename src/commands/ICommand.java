package commands;

/**
 * @author Simon
 * The command interface that commands must implement. 
 * Nothing more than the {@code execute} method is needed.
 */
public interface ICommand {
	void execute();
}
