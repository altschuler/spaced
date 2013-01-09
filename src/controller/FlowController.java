package controller;

import model.GameModel;
import model.GameStateFactory;
import view.MainView;

/**
 * Controls the flow of loading and changing levels.
 */
public class FlowController extends AbstractController {
    
    private GameStateFactory factory;

    public FlowController(MainView gw, GameModel gm) {
        super(gw, gm);

        factory = new GameStateFactory();
    }
    
    public void loadLevel(int id) {
    	this.gameModel.setActiveGameState(factory.getLevel(id));
    }

}
