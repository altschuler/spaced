package controller;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundController {

	/**
	 * @param f		the sound file (MUST be .wav)
	 * @param repeats    how many times the sound should be repeated
	 * @param stopTime	set = 0 if should just keep playing 'till end, else set = time it should play (in milliseconds)
	 * 
	 * Plays the .wav file in a new thread (so it is not affected by the rest of the game)
	 */
	public static synchronized void playSound(final File f, final int repeats, final int stopTime) {

	    new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
	      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
          clip.open(inputStream);
//        System.out.println("Length of the soundclip: "+(double) clip.getMicrosecondLength()/1000000+"s");
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
	    }).start();
  }
}
