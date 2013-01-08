package view.render;

import java.awt.Image;

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
