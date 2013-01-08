package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
    
    private Image image;
    
    
    public Sprite(Image _image) {
        
        /*BufferedImage image = null;
        try {
            image = ImageIO.read(_file);
            this.image = image;
        } catch (IOException e) {
            System.out.println("ERROR: Could not load file: " + _file.getName());
            System.exit(1);
        } */
        
        this.image = _image;
    }
    
    
    public int getWidth() {
		return image.getWidth(null);
	}
    
    public int getHeight() {
        return image.getHeight(null);
    }
    
    public void draw(Graphics g,int x,int y) {
        g.drawImage(image,x,y,null);
    }
    
}
