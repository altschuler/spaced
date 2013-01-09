/**
 * 
 */
package model.elements;

import model.core.Direction;

/**
 * A bullet that can be fire either by the player or an {@link Invader}
 */
public class Bullet extends AbstractGameElement {
	private Direction direction;
	private int speed = 15;
	
	public Bullet(Direction direction) {
		super(5, 15);
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}
	
	public int getSpeed(){
		return speed;
	}
}
