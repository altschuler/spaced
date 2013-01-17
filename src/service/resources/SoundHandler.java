package service.resources;

import java.io.File;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundHandler {
    
    private static final String PATH_PREFIX = "resources/audio/";
    private HashMap<String, Sound> sounds = new HashMap<>();
    
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
    
    public synchronized void playSound(String name, final int repeats, final int stopTime, final float gain) {
        final String ref = PATH_PREFIX + name;
        
	    new Thread(new Runnable() {
	      public void run() {
        try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(sounds.get(ref).getFile());
          clip.open(inputStream);
          FloatControl gainControl =   (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
          gainControl.setValue(gain);
          clip.loop(repeats);
          clip.start();
          if(stopTime > 0){
	          try { Thread.currentThread().sleep(stopTime); }
		        catch (InterruptedException e) { System.out.println("ERROR: sleeping");}
	          clip.stop();
          }
          
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }

	      }
	    }).start();
  }

	public File getSound(String name) {
            String ref = PATH_PREFIX + name;
            return sounds.get(ref).getFile();
	}
    
    public void add(String name) {
        String ref = PATH_PREFIX + name;
        Sound sound = new Sound(new File(ref));
    	sounds.put(ref, sound);
    }
 
    private void initSprites() {
        this.add("boom01.wav");        
        this.add("boom03.wav");
        this.add("tillykke01.wav");
        this.add("sheep01.wav");
        this.add("zap05.wav");
        this.add("zap01.wav");
        this.add("game_over02.wav");
    }

}