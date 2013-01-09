/**
 * 
 */
package model.elements;

import model.core.Direction;

/**
 * A bullet that can be fire either by the player or an {@link Invader}
 */
public class Bullet extends GameElement {
	private Direction direction;
	
	public Bullet(Direction direction) {
		super(5, 15);
		this.direction = direction;
		this.setSpeed(15);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

//	@Override
//	public Bullet clone() {
//		Bullet b = (Bullet) super.clone();
//		b.setDirection(this.getDirection());
//		return b;
//	}
}
