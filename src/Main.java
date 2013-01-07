import models.GameModel;
import views.MainView;
import views.state.ViewState;

import commands.CommandFactory;

import controllers.GameController;

/**
 * @author Simon
 * Java entry class
 */
public class Main {
	public static void main(String[] args) {
		GameModel gm = new GameModel();
		MainView gw = new MainView();
		GameController gc = new GameController(gw, gm);
		
		CommandFactory.init(gc, gm);
		
		// bootstrap the application by showing the menu view state
		CommandFactory.createSetStateCommand(ViewState.Splash).execute();
	}
}
