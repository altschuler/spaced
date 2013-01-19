package controller;

import model.MainModel;
import utils.Input;
import view.MainView;
import view.state.AbstractViewState;
import view.state.GameOverViewState;
import view.state.GameViewState;
import view.state.HighscoreViewState;
import view.state.MenuViewState;
import view.state.PauseMenuViewState;
import view.state.SplashViewState;
import view.state.ViewState;

/**
 * Is called by {@link command.SetViewStateCommand} to switch between view states
 */
public class StateController extends AbstractController {

        private MainController mainController;
        
	public StateController(MainView gw, MainModel gm, MainController mc) {
		super(gw, gm);
                this.mainController = mc;
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
				
			case Highscore:
				newState = new HighscoreViewState();
				break;
				
			case Game:
				newState = new GameViewState();
				break;
				
			case Splash:
				newState = new SplashViewState();
				break;
				
			case PauseMenu:
				newState = new PauseMenuViewState();
				break;
				
			case GameOver:
				newState = new GameOverViewState();
				break;
	
			default:
				throw new IllegalArgumentException(String.format("No such game state: %d", state));
		}
		
		if (oldState != null) {
			this.gameModel.deleteObserver(oldState);
			oldState.dispose();
		}
		
		this.gameModel.addObserver(newState);
        newState.setMainController(this.mainController);
		newState.update(this.gameModel, null);
		this.mainView.setContentPane(newState);
		
		newState.initialize();
		
		this.mainView.pack();
		
		Input.getInstance().update();
	}
}
