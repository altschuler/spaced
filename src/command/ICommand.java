package command;

/**
 * The command interface that commands must implement. 
 * Nothing more than the {@code execute} method is needed.
 */
public interface ICommand {
	/**
	 * Executes the command
	 */
	void execute();
	/**
	 * @param cmd Command to be executed just after executing this Command instance
	 * @return returns the first command to be executed
	 * 
	 * chain enables chaining of multiple command into one single executable Command.
	 * For this concept to work it is, however, VERY(!) important that all Command subclasses
	 * call "super.execute()" just before their own .execute() exits.
	 */
	Command chain(Command cmd);
}
