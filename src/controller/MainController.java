package controller;

import model.MainModel;
import view.MainView;

/**
 * Controller that manages other {@link AbstractController}s, and provides them with
 * access to the {@link MainModel} and {@link MainView}.
 */
public class MainController {

	private StateController stateController;
	private GameController gameController;
	private FlowController flowController;
        private HighscoreController highscoreController;
	
	private MainView gw;
	private MainModel gm;

	public MainController(MainView gw, MainModel gm) {
		this.gw = gw;
		this.gm = gm;
	}

	/**
	 * The controller instance is lazily instantiated
	 * 
	 * @return A {@link FlowController} instance
	 */
	public FlowController getFlowController() {
		if (this.flowController == null) {
			this.flowController = new FlowController(gw, gm);
		}
		
		return this.flowController;
	}

	/**
	 * The controller instance is lazily instantiated
	 * 
	 * @return A {@link StateController} instance
	 */
	public StateController getStateController() {
		if (this.stateController == null) {
			this.stateController = new StateController(gw, gm, this);
		}
		
		return this.stateController;
	}

	/**
	 * The controller instance is lazily instantiated
	 * 
	 * @return A {@link GameController} instance
	 */
	public GameController getGameController() {
		if (this.gameController == null) {
			this.gameController = new GameController(gw, gm);
		}
		
		return this.gameController;
	}

	/**
	 * The controller instance is lazily instantiated
	 * 
	 * @return A {@link HighscoreController} instance
	 */
	public HighscoreController getHighscoreController() {
		if (this.highscoreController == null) {
			this.highscoreController = new HighscoreController(gw, gm);
		}
		
		return this.highscoreController;
	}
        
        
}
