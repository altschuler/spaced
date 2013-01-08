import models.GameModel;
import views.MainView;
import views.state.ViewState;

import commands.CommandFactory;

import controllers.MainController;

/**
 * Java entry class
 */
public class Main {
	public static void main(String[] args) {
		GameModel gameModel = new GameModel();
		MainView mainView = new MainView();
		mainView.setVisible(true);
		MainController mainController = new MainController(mainView, gameModel);
		
		CommandFactory.init(mainController, gameModel);

		// bootstrap the application
		CommandFactory.createSetStateCommand(ViewState.Splash).execute();
	}
}
