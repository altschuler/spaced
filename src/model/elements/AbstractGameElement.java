package model.elements;

import view.render.SpriteHandler;
import model.Coordinate;

/**
 * The base of all interactive game elements. The common feats 
 * area a visual appearance and a position
 */
abstract public class AbstractGameElement {
	
	private Coordinate position;
	private int width;
	private int height;
	private String imageURL; 
	//TODO some visual identify?
	
	public AbstractGameElement(int width, int height) {
		this.position = new Coordinate();
		this.width = width;
		this.height = height;
	}
	public AbstractGameElement(int width, int height, String imageURL) { //TODO implement this in all model.elements
		this.position = new Coordinate();
		SpriteHandler spriteHandler = SpriteHandler.getInstance();

		this.width = spriteHandler.get(imageURL).getHeight();
		this.height = spriteHandler.get(imageURL).getHeight();
//		this.width = width;
//		this.height = height;
		this.imageURL = imageURL;
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
}
