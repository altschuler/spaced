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

/**
 * Loads {@link Sprite}s from spaced/resources/sprites and caches them in a hashmap. Returns Sprites the method get() is called (which is the game in {@link view.render.GameStateRenderer})
 */
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
        this.add("aBlue.png");
        this.add("bBlue.png");
        this.add("cBlue.png");
        this.add("aRed.png");
        this.add("bRed.png");
        this.add("cRed.png");
        this.add("bonus.png");
        this.add("explosion.png");
        this.add("explosionSprite.png");
        this.add("explosionAoE.png");
        this.add("bunkerPart.png");
        this.add("bunkerPartBroken.png");
        this.add("bunkerPartBrokenUpwardsBullet.png");
        this.add("bullet.png");
        this.add("fastBullet.png");
        this.add("bulletInvader.png");
        this.add("bulletInvaderHoming.png");
        this.add("missile.png");
        this.add("hubble.jpg");
        this.add("logo.png");
        this.add("present2.png");
        this.add("heart.png");
        this.add("snowFlake.png");
        this.add("alien.png");
        this.add("cage.png");
        this.add("nicholas01.png");
        this.add("nicholas02.png");
        this.add("nicholas03.png");
        this.add("nicholas04.png");
        this.add("nicholas05.png");
        this.add("nicholas06.png");
        this.add("nicholas07.png");
        this.add("nicholas08.png");
        this.add("nicholas09.png");
        this.add("nicholas10.png");
    }
    
    private void fail(String message) {
        System.err.println(message);
        System.exit(1);
    }
    

}
