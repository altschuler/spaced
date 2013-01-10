package command;

import model.MainModel;

/**
 * This is a dummy test class and has no real use, but sets a property on the model.
 */
public class SetDifficultyCommand extends Command {
	
	private int difficultyId;
	private MainModel gm;
	
	public SetDifficultyCommand(MainModel gm, int difficultyId) {
		this.difficultyId = difficultyId;
		this.gm = gm;
	}

	@Override
	public void execute() {
		this.gm.setActiveDifficulty(this.gm.getGameConfig().getDifficulty(difficultyId));
		
		super.execute();
	}

}
