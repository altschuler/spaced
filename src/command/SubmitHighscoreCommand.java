package command;

import model.HighscoreEntry;
import controller.HighscoreController;

public class SubmitHighscoreCommand extends Command {

	private HighscoreController scoreController;
	private HighscoreEntry entry;


	public SubmitHighscoreCommand(HighscoreController scoreController, HighscoreEntry highscoreEntry) {
		this.scoreController = scoreController;
		this.entry = highscoreEntry;
	}

	@Override 
	public void execute() {
		this.scoreController.add(entry);
		
		super.execute();
	}

}
