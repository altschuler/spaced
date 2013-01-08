import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import model.GameModel;
import view.MainView;
import view.state.ViewState;

import command.CommandFactory;

import controller.MainController;

/**
 * Java entry class
 */
public class Main {
	public static void main(String[] args) {
		playSound(new File("hallelujah.wav"),1,0);	//Plays the hallelujah-thing 2000 times
		GameModel gameModel = new GameModel();
		MainView mainView = new MainView();
		mainView.setVisible(true);
		MainController mainController = new MainController(mainView, gameModel);
		
		CommandFactory.init(mainController, gameModel);

		// bootstrap the application
		CommandFactory.createSetStateCommand(ViewState.Splash).execute();
	}
	
	
	/**
	 * @param f		the sound file (MUST be .wav)
	 * @param repeats    how many times the sound should be repeated
	 * @param stopTime	set = 0 if should just keep playing 'till end, else set = time it should play (in milliseconds)
	 * 
	 * Plays the .wav file in a new thread (so it is not affected by the rest of the game)
	 */
	public static synchronized void playSound(final File f, int repeats, int stopTime) {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
          clip.open(inputStream);
          System.out.println("Length of the soundclip: "+(double) clip.getMicrosecondLength()/1000000+"s");
          clip.loop(repeats);
          clip.start(); 
          if(stopTime > 0){
          try { Thread.currentThread().sleep(stopTime); }
	        catch (InterruptedException e) { System.out.println("Error sleeping");}
          clip.stop();
          }
          
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }
  }
}
