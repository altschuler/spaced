package view.render;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpriteHandler {
    
    private static SpriteHandler instance = null;
    
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
    
     
    private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    
    public void add(String ref) {
    	BufferedImage sourceImage = null;

    	try {
			sourceImage = ImageIO.read(new File(ref));
		} catch (IOException e1) {
			fail("ERROR: Failed to load: "+ref);
			e1.printStackTrace();
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
    
    public Sprite get(String ref) {
        if (sprites.get(ref) != null) {
            return (Sprite) sprites.get(ref);
        } 
        
        fail("ERROR: Cannot get null ref: "+ref);
        return (Sprite) null;
    }
    
    private void initSprites() {
        this.add("resources/sprites/player.png");
        this.add("resources/sprites/player_life.png");
        this.add("resources/sprites/invaderA.png");
        this.add("resources/sprites/invaderB.png");
        this.add("resources/sprites/invaderC.png");
        this.add("resources/sprites/invaderCRed.png");
        this.add("resources/sprites/invaderCRedSemi.png");
        this.add("resources/sprites/invaderCBlue.png");
        this.add("resources/sprites/invaderCPurple.png");
        this.add("resources/sprites/bonus.png");
        this.add("resources/sprites/explosion.png");
        this.add("resources/sprites/bunkerPart.png");
        this.add("resources/sprites/bunkerPartBroken.png");
        this.add("resources/sprites/bunkerPartBrokenUpwardsBullet.png");
        this.add("resources/sprites/bullet.png");
        this.add("resources/sprites/bulletInvader.png");
        this.add("resources/sprites/bulletInvaderHoming.png");
        this.add("resources/sprites/missile.png");
        this.add("resources/sprites/hubble.jpg");
        this.add("resources/sprites/logo.png");
        this.add("resources/sprites/present.png");
        this.add("resources/sprites/present2.png");
    }
    
    private void fail(String message) {
        System.err.println(message);
        System.exit(1);
    }
    

}
