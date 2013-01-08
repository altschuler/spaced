package view.render;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpriteStore {

    private static SpriteStore single = new SpriteStore();
    
    private HashMap sprites = new HashMap();
    
    public static SpriteStore get() {
            return single;
    }

    	public Sprite getSprite(String ref) {
		// if exist, return from cache
		if (sprites.get(ref) != null) {
			return (Sprite) sprites.get(ref);
		}
		
		// otherwise, grab the sprite from resource

                BufferedImage sourceImage = null;
		
		try {
			URL url = this.getClass().getClassLoader().getResource(ref);
			
			if (url == null){ fail("ERROR: Can't find ref: "+ref); }
			
			sourceImage = ImageIO.read(url);
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
		
		return sprite;
	}
	

	private void fail(String message) {
		System.err.println(message);
		System.exit(1);
	}

    
}
