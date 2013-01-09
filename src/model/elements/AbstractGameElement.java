package model.elements;

import model.Coordinate;

/**
 * The base of all interactive game elements. The common feats 
 * area a visual appearance and a position
 */
abstract public class AbstractGameElement {
	
	private Coordinate position;
	private int width;
	private int height;
	//TODO some visual identify?
	
	public AbstractGameElement(int width, int height) {
		this.position = new Coordinate();
		this.width = width;
		this.height = height;
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
