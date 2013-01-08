import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundTest {
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
		
		File f = new File("hallelujah.wav");
		System.out.println("Filen indeholder "+f.length()+" tegn og ligger i stien "+f.getAbsolutePath());
		
		playSound(f);
		soundTest(f);
		
	}
	public static void soundTest(File f) throws UnsupportedAudioFileException, IOException{
		  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f);
		  try {
		    Clip clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    System.out.println(clip.available());
	         System.out.println(clip.getMicrosecondLength());
	         clip.loop(2);
	         FloatControl gainControl = 
	        		    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        		gainControl.setValue(6.0f); // Reduce volume by 10 decibels.
		    clip.start();
		    
		  } catch (Exception e) {
	          System.err.println(e.getMessage());
	      }
	}
	
	public static synchronized void playSound(final File f) {
	    new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
	      public void run() {
	        try {
	          Clip clip = AudioSystem.getClip();
	          AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
	          clip.open(inputStream);
	          System.out.println(clip.available());
	          System.out.println(clip.getMicrosecondLength());
	          clip.loop(2);
	          clip.start(); 
	        } catch (Exception e) {
	          System.err.println(e.getMessage());
	        }
	      }
	    }).start();
	  }
}
