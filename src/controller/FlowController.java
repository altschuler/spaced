package controller;

import model.GameStateFactory;
import model.GameStateState;
import model.MainModel;
import model.core.PlayerIndex;
import view.MainView;

/**
 * Controls the flow of loading and changing levels.
 */
public class FlowController extends AbstractController {
    
    public FlowController(MainView gw, MainModel gm) {
        super(gw, gm);
    }
    
    /**
     * @param id Id of level to load
     * @param retainedProgress Should players lives and score be kept
     */
    public void loadLevel(int id, boolean retainedProgress) {
    	int playerLives = 0;
    	int playerPoints = 0;
    	boolean hadGameState = false;
    	if (this.gameModel.getActiveGameState() != null) {
    		hadGameState = true;
	    	playerLives = this.gameModel.getActiveGameState().getPlayer(PlayerIndex.One).getLives();
	    	playerPoints = this.gameModel.getActiveGameState().getPlayer(PlayerIndex.One).getPoints();
    	}
    	
    	this.gameModel.setActiveGameState(GameStateFactory.getLevel(id));
    	this.gameModel.getActiveGameState().setState(GameStateState.Waiting);
    	if (retainedProgress && hadGameState) {
    		this.gameModel.getActiveGameState().getPlayer(PlayerIndex.One).setLives(playerLives);
    		this.gameModel.getActiveGameState().getPlayer(PlayerIndex.One).setPoints(playerPoints);
    	}
    }
    
    /**
     * Continue play to the next level
     */
    public void loadNextLevel() {
		if (this.gameModel.getActiveGameState() == null ) {
			this.loadLevel(1, true);	
		}
		else {
			int nextLevelId = this.gameModel.getActiveGameState().getId() + 1;
			if (GameStateFactory.levelExists(nextLevelId)) {
				this.loadLevel(nextLevelId, true);
			} else {
				this.loadLevel(1, true);
				//TODO all levels won!
			}
		}
    }

}
