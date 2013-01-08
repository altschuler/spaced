package models.elements;

import java.awt.Point;

/**
 * The base of all interactive game elements. The common feats 
 * area a visual appearance and a position
 */
abstract public class AbstractGameElement {
	
	private Point position;
	private int width;
	private int height;
	//TODO some visual identify?
	
	public AbstractGameElement(int width, int height) {
		this.position = new Point(0, 0);
		this.width = width;
		this.height = height;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
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
}
