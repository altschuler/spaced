package utils;

import model.Coordinate;
import model.elements.AbstractGameElement;

public class Mathx {

	/**
	 * @param n
	 *            The number to check
	 * @param min
	 *            Minimum value for validation
	 * @param max
	 *            Maximum value for validation
	 * @return True if n is inbetween (inclusive) min and max
	 */
	static public boolean between(double n, double min, double max) {
		return n > min && n < max;
	}

	/**
	 * @param timeDelta
	 *            Time passed since last frame
	 * @param speed
	 *            How fast is the object moving
	 * @return A distance dependant on time, thereby avoiding glitches that make
	 *         movement unstable
	 */
	static public long distance(long timeDelta, int speed) {
		return timeDelta / 20 * speed / 2;
	}
	
	static public boolean intersects(AbstractGameElement a, AbstractGameElement b) {
		Coordinate ap = a.getPosition();
		Coordinate bp = b.getPosition();
		double ax1 = ap.x, ax2 = ap.x + a.getWidth();
		double ay1 = ap.y, ay2 = ap.y + a.getHeight();
		double bx1 = bp.x, bx2 = bp.x + b.getWidth();
		double by1 = bp.y, by2 = bp.y + b.getHeight();
		
		return ax1 < bx2 && ax2 > bx1 && ay1 < by2 && ay2 > by1;
	}
}
