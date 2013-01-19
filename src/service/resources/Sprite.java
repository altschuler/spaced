package service.resources;

import java.awt.Image;

/**
 *Simply an {@link Image} to be stored and accessed by {@link SpriteHandler}
 */
public class Sprite {
    
    private Image image;
    
    public Sprite(Image _image) {
        this.image = _image;
    }
    
    public Image getImage() {
        return image;
    }
    
    public int getWidth() {
        return image.getWidth(null);
    }
    
    public int getHeight() {
        return image.getHeight(null);
    }
    
}
