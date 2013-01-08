import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class SoundTest {
	public static void main(String[] args){
		
		
		playSound(new File("hallelujah.wav"));
		playSound(new File("leftright.wav"));
		while(true){		}
		
		
	}
	
	
	public static synchronized void playSound(final File f) {
	        try {
	          Clip clip = AudioSystem.getClip();
	          AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
	          clip.open(inputStream);
	          System.out.println(clip.available());
	          System.out.println(clip.getMicrosecondLength());
	          clip.loop(2000);
	          clip.start(); 
	        } catch (Exception e) {
	          System.err.println(e.getMessage());
	        }
	  }
}
