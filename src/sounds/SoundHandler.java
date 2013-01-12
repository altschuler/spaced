package sounds;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundHandler {
	private HashMap<String, File> sounds = new HashMap<String, File>();
    
    private static SoundHandler instance = null;
    
    protected SoundHandler() {
    	this.initSprites();
    }
    
    // Singleton
    public static SoundHandler getInstance() {
        if (instance == null) {
            instance = new SoundHandler();
        }
        return instance;
    }
 
    private void initSprites() {
        this.add("audio/8bit_metal_new.wav");
        this.add("audio/bang01.wav");
        this.add("audio/bang02.wav");
        this.add("audio/boom01.wav");
        this.add("audio/boom02.wav");
        this.add("audio/boom03.wav");
        this.add("audio/boom04.wav");
        this.add("audio/boom05.wav");
        this.add("audio/boom06.wav");
        this.add("audio/boom07.wav");
        this.add("audio/fart.wav");
        this.add("audio/game_over01.wav");
        this.add("audio/game_over02.wav");
        this.add("audio/muha01.wav");
        this.add("audio/muha02.wav");
        this.add("audio/sheep01.wav");
        this.add("audio/tillykke_highscore01.wav");
        this.add("audio/tillykke_highscore02.wav");
        this.add("audio/tillykke_highscore03.wav");
        this.add("audio/tillykke01.wav");
        this.add("audio/zap01.wav");
        this.add("audio/zap02.wav");
        this.add("audio/zap03.wav");
        this.add("audio/zap04.wav");
        this.add("audio/zap05.wav");
        this.add("audio/zap06.wav");
        this.add("audio/zap07.wav");
        this.add("audio/zap08.wav");
        this.add("audio/zap09.wav");
    }
    
    public void add(String ref) {
    	sounds.put(ref, new File(ref));
    }
    
    public synchronized void playSound(final String ref, final int repeats, final int stopTime, final float gain) {

	    new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
	      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(sounds.get(ref));
          clip.open(inputStream);
          FloatControl gainControl =   (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
          gainControl.setValue(gain);
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

	public File getSound(String ref) {
		return sounds.get(ref);
	}
    
  

}