import java.io.File;



import model.GameModel;
import view.MainView;
import view.state.ViewState;

import command.CommandFactory;

import controller.MainController;
import controller.SoundController;

/**
 * Java entry class
 */
public class Main {
	public static void main(String[] args) {
//		SoundController.playSound(new File("hallelujah.wav"),1000,0);	//Plays the hallelujah-thing 2000 times
		GameModel gameModel = new GameModel();
		MainView mainView = new MainView();
		mainView.setVisible(true);
		MainController mainController = new MainController(mainView, gameModel);
		
		CommandFactory.init(mainController, gameModel);

		// bootstrap the application
		CommandFactory.createSetStateCommand(ViewState.Splash).execute();
	}
	
	
}
