package utils;

import model.core.Coordinate;
import model.elements.GameElement;

public class Mathx {

	/**
	 * @param milliseconds Time in milliseconds
	 * @return The given time as a String formatted nicely in minutes and seconds
	 */
	static public String prettyTime(long milliseconds) {
		double totalSeconds = milliseconds / 1000;
		
		int seconds = (int) (totalSeconds % 60);
		int minutes = (int) ((totalSeconds - seconds) / 60);

		String secondString = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
		String minuteString = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
		
		return String.format("%s:%s", minuteString, secondString);
	}

	/**
	 * @param timeDelta
	 *            Time passed since last frame
	 * @param speed
	 *            How fast is the object moving
	 * @return A distance dependant on time, thereby avoiding glitches that make
	 *         movement unstable
	 */
	static public double distance(long timeDelta, double speed) {
		return timeDelta * speed / 40.0; //40 er en naturkonstant der beskriver forholdet mellem millisekunder og hvor mange pixels vi synes en invader skal bevæge sig pr. millisekunder
	}

	/**
	 * Determine whether two game elements are intersecting, depending on their
	 * size and position.
	 * 
	 * @return True if a and b intersects
	 */
	static public boolean intersects(GameElement a, GameElement b) {
		Coordinate ap = a.getPosition();
		Coordinate bp = b.getPosition();
		double ax1 = ap.x, ax2 = ap.x + a.getWidth();
		double ay1 = ap.y, ay2 = ap.y + a.getHeight();
		double bx1 = bp.x, bx2 = bp.x + b.getWidth();
		double by1 = bp.y, by2 = bp.y + b.getHeight();

		return ax1 < bx2 && ax2 > bx1 && ay1 < by2 && ay2 > by1;
	}
	
	/**
	 * @param rect The rectangular GameElement
	 * @param circ The position of the circular GameElement
	 * @param radius The radius of the circular GameElement
	 * @return True if rect and circ intersects
	 */
	static public boolean circleRectangleIntersects(GameElement rect, Coordinate circ, double radius){
		double rectCenterX = rect.getPosition().x + ((double) rect.getWidth()/2);
		double rectCenterY = rect.getPosition().y + ((double) rect.getHeight()/2);

		double circleDistanceX = Math.abs(rectCenterX-circ.x);
		double circleDistanceY = Math.abs(rectCenterY-circ.y);
		
		if (circleDistanceX > (rect.getWidth()/2 + radius)) { return false; }
	    if (circleDistanceY > (rect.getHeight()/2 + radius)) { return false; }
//De to ovenstående har udelukket alle tilfælde hvor cirklen ligger udenfor en forstørret udgave af rektanglen (rektanglen + radius)
//Derfor vil cirklen ligge indenfor rektanglen+(cirklens radius) i de to nedenstående.
	    if (circleDistanceX <= (rect.getWidth()/2)) { return true; } 
	    if (circleDistanceY <= (rect.getHeight()/2)) { return true; }
	    
//Tilbage er der kun at kontrollere hjørnerne af den forstørrede rektangel (kvadrater med cirklens radius som sidelængde)
//cornerDistanceSquared er cirklens centrums afstand til den oprindelige rektangels hjørner.
	    double cornerDistanceSquared = Math.pow((circleDistanceX - rect.getWidth()/2),2) +
                Math.pow((circleDistanceY - rect.getHeight()/2), 2);
//Er cirklens centrum længere væk end dens radius er der ikke kollision
		return (cornerDistanceSquared <= Math.pow(radius,2));
	}

	public static Coordinate getDirectionVector(Coordinate a, Coordinate b) {
		return new Coordinate(a.x - b.x, a.y - b.y);
	}	
}
