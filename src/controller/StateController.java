package controllers;

import models.GameModel;
import views.MainView;
import views.state.AbstractViewState;
import views.state.GameViewState;
import views.state.MenuViewState;
import views.state.SplashViewState;
import views.state.ViewState;

public class StateController extends AbstractController {

	public StateController(MainView gw, GameModel gm) {
		super(gw, gm);
	}

	/**
	 * Changes the active game state 
	 * 
	 * @param state The new state to change to
	 */
	public void setViewState(ViewState state) {
		AbstractViewState newState;
		AbstractViewState oldState = null;
		
		try {
			oldState = (AbstractViewState) this.gw.getContentPane();
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
		
		if (oldState != null)
			this.gm.deleteObserver(oldState);
		
		this.gm.addObserver(newState);
		newState.update(this.gm, null); // TODO W T F
		this.gw.setContentPane(newState);
		this.gw.pack();
	}
}