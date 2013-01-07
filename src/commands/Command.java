package commands;

public class Command implements ICommand {
	protected Command chained;

	public Command chain(Command chained) {
		this.chained = chained;
		
		return this;
	}

	@Override
	public void execute() {
		if (this.chained != null) {
			this.chained.execute();
		}
	}
}
