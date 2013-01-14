package model.elements;

import model.core.Coordinate;
import model.core.InvaderType;
import service.resources.SpriteHandler;

/**
 * The base of all interactive game elements. The common feats 
 * area a visual appearance and a position
 */
public abstract class GameElement {
	
	private Coordinate position;
	private int width;
	private int height;
	private int speed;
	
	private boolean destroyed;
	
	private String imageURL; 
	//TODO some visual identify?
	
	public GameElement(String imageURL) { //TODO implement this in all model.elements
		this.position = new Coordinate();
		SpriteHandler spriteHandler = SpriteHandler.getInstance();
		this.width = spriteHandler.get(imageURL).getWidth();
		this.height = spriteHandler.get(imageURL).getHeight();
		this.setImageURL(imageURL);
	}

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
	
	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	protected void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	protected void setHeight(int height) {
		this.height = height;
	}
	
	public void move(double x, double y){
		this.position.x += x;
		this.position.y += y;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	public void destroy() {
		this.setDestroyed(true);
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
