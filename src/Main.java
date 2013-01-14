import model.MainModel;
import model.GameStateFactory;
import view.MainView;
import view.state.ViewState;
import command.CommandFactory;
import controller.MainController;

/**
 * Java entry class
 */
public class Main {
	public static void main(String[] args) {
		// wire up mvc
		MainModel gameModel = new MainModel();
		
		MainView mainView = new MainView();
		MainController mainController = new MainController(mainView, gameModel);
		
		// init factories
		CommandFactory.init(mainController, gameModel);
		GameStateFactory.init();
		
		// bootstrap the application
		CommandFactory.createSetStateCommand(ViewState.Splash).execute();
	}
	
	
}
