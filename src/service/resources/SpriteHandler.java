package service.resources;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpriteHandler {
    
    private static SpriteHandler instance = null;
    private static final String PATH_PREFIX = "resources/sprites/";
    
    protected SpriteHandler() {
    	this.initSprites();
    }
    
    // Singleton
    public static SpriteHandler getInstance() {
        if (instance == null) {
            instance = new SpriteHandler();
        }
        return instance;
    }
     
    private HashMap<String, Sprite> sprites = new HashMap<>();
    
    public void add(String name) {
        String ref = PATH_PREFIX + name;
    	BufferedImage sourceImage = null;

    	try {
            sourceImage = ImageIO.read(new File(ref));
        } catch (IOException e) {
                fail("ERROR: Failed to load: "+ref);
        }


        // create an accelerated image of the right size to store our sprite in
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
        
        // draw our source image into the accelerated image
        image.getGraphics().drawImage(sourceImage,0,0,null);
        
        // create a sprite, add it the cache then return it
        Sprite sprite = new Sprite(image);
        sprites.put(ref,sprite);
    }
    
    public Sprite get(String name) {
        String ref = PATH_PREFIX + name;
        if (sprites.get(ref) != null) {
            return (Sprite) sprites.get(ref);
        } 
        
        fail("ERROR: Cannot get null ref: "+ref);
        return (Sprite) null;
    }
    
    private void initSprites() {
        this.add("player.png");
        this.add("player_life.png");
        this.add("invaderA.png");
        this.add("invaderB.png");
        this.add("invaderC.png");
        this.add("invaderCRed.png");
        this.add("invaderCRedSemi.png");
        this.add("invaderCBlue.png");
        this.add("invaderCPurple.png");
        this.add("bonus.png");
        this.add("explosion.png");
        this.add("bunkerPart.png");
        this.add("bunkerPartBroken.png");
        this.add("bunkerPartBrokenUpwardsBullet.png");
        this.add("bullet.png");
        this.add("bulletInvader.png");
        this.add("bulletInvaderHoming.png");
        this.add("missile.png");
        this.add("hubble.jpg");
        this.add("logo.png");
        this.add("present2.png");
        this.add("heart.png");
        this.add("snowFlake.png");
    }
    
    private void fail(String message) {
        System.err.println(message);
        System.exit(1);
    }
    

}