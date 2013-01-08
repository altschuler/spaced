package controllers;

import models.GameModel;
import views.MainView;

/**
 * Controller that manages other {@link AbstractController}s, and provides them with
 * access to the {@link GameModel} and {@link MainView}.
 */
public class MainController {

	private StateController stateController;
	private GameController gameController;
	private MainView gw;
	private GameModel gm;

	public MainController(MainView gw, GameModel gm) {
		this.gw = gw;
		this.gm = gm;
	}

	/**
	 * The controller instance is lazily instantiated
	 * 
	 * @return A {@link StateController} instance
	 */
	public StateController getStateController() {
		if (this.stateController == null) {
			this.stateController = new StateController(gw, gm);
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
}
