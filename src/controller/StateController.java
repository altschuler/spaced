package controller;

import model.GameModel;
import utils.Input;
import view.MainView;
import view.state.AbstractViewState;
import view.state.GameViewState;
import view.state.MenuViewState;
import view.state.SplashViewState;
import view.state.ViewState;

public class StateController extends AbstractController {

	public StateController(MainView gw, GameModel gm) {
		super(gw, gm);
	}

	/**
	 * Changes the active view state 
	 * 
	 * @param state The new state to change to
	 */
	public void setViewState(ViewState state) {
		AbstractViewState newState;
		AbstractViewState oldState = null;
		
		try {
			oldState = (AbstractViewState) this.mainView.getContentPane();
		} catch (Exception e) {
			// no previous state, dont throw an exception 
			// as this is valid on first view
		}
		
		switch (state) {
			case Menu:
				newState = new MenuViewState();
				break;
				
			case Game:
				newState = new GameViewState();
				break;
				
			case Splash:
				newState = new SplashViewState();
				break;
	
			default:
				throw new IllegalArgumentException(String.format("No such game state: %d", state));
		}
		
		if (oldState != null) {
			this.gameModel.deleteObserver(oldState);
			oldState.dispose();
		}
		
		this.gameModel.addObserver(newState);
		newState.update(this.gameModel, null); // TODO W T F
		this.mainView.setContentPane(newState);
		
		newState.initialize();
		
		this.mainView.pack();
		
		Input.getInstance().update();
	}
}
