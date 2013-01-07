package controlllers;

import models.GameModel;
import views.MainView;
import views.state.AbstractViewState;
import views.state.GameViewState;
import views.state.MenuViewState;
import views.state.ViewState;

/**
 * @author Simon
 * Controller that glues together the {@link GameModel} and {@link MainView}.
 */
public class GameController {

	private MainView gw;
	private GameModel gm;

	public GameController(MainView gw, GameModel gm) {
		this.gw = gw;
		this.gm = gm;
		
		gw.setVisible(true);
	}

	/**
	 * @param state The new state to change to
	 * Changes the active game state 
	 */
	public void setGameState(ViewState state) {
		AbstractViewState newState;
		AbstractViewState oldState = null;
		
		try {
			oldState = (AbstractViewState) this.gw.getContentPane();
		} catch (Exception e) {
			// no previous state, dont throw an exception as this is valid
		}
		
		switch (state) {
			case Menu:
				newState = new MenuViewState();
				break;
				
			case Game:
				newState = new GameViewState();
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
