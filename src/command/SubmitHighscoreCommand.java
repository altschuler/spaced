package command;

import model.HighscoreEntry;
import controller.HighscoreController;

/**
 * Submits a highscore to our server.
 * 
 * It does so by passing a {@link model.HighscoreEntry} to the {@link controller.HighscoreController}.
 */
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
