package controlllers;

import models.GameModel;
import views.GameView;
import views.state.AbstractViewState;
import views.state.GameState;
import views.state.MenuState;
import views.state.ViewState;

public class GameController {

	private GameView gw;
	private GameModel gm;

	public GameController(GameView gw, GameModel gm) {
		this.gw = gw;
		this.gm = gm;
		
		gw.setVisible(true);
	}

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
				newState = new MenuState();
				break;
				
			case Game:
				newState = new GameState();
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
