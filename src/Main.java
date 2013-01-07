import models.GameModel;
import views.MainView;
import views.state.ViewState;

import commands.CommandFactory;

import controlllers.GameController;

public class Main {
	public static void main(String[] args) {
		GameModel gm = new GameModel();
		MainView gw = new MainView();
		GameController gc = new GameController(gw, gm);
		
		CommandFactory.init(gc, gm);
		
		// Set initial state to menu
		CommandFactory.createSetStateCommand(ViewState.Menu).execute();
	}
}
