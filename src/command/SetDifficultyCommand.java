package command;

import model.MainModel;
import model.core.Difficulty;

/**
 * This is a dummy test class and has no real use, but sets a property on the model.
 */
public class SetDifficultyCommand extends Command {
	
        private boolean byName;
	private int difficultyId;
        private String difficultyName;
	private MainModel gm;
	
	public SetDifficultyCommand(MainModel gm, int difficultyId) {
		this.difficultyId = difficultyId;
		this.gm = gm;
	}

        public SetDifficultyCommand(MainModel gm, String difficultyName) {
            this.difficultyName = difficultyName;
            this.byName = true;
            this.gm = gm;
        }

	@Override
	public void execute() {
		for (Difficulty difficulty : this.gm.getGameConfig().getDifficulties()) {
                    if ((this.difficultyId == difficulty.getId() && !this.byName) || 
                            this.difficultyName.equals(difficulty.getName())) {
                        this.gm.setActiveDifficulty(difficulty);
                        break;
                    }
                }
		
		super.execute();
	}

}
