package model.core;

/**
 * Represents a position in the game space. Uses doubles compared to Java's
 * Point, and has setters for x and y compared to Java's Point2D
 */
public class Coordinate {
	public double x;
	public double y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate() {
		this(0.0, 0.0);
	}
	
	public Coordinate clone() {
		return new Coordinate(this.x, this.y);
	}
	
	public void normalize(){
		double length = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
		this.x = this.x/length;
		this.y = this.y/length;
	}
}
