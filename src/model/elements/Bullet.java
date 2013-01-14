package model.elements;

import model.core.BulletType;
import model.core.Direction;

/**
 * A bullet that can be fire either by the player or an {@link Invader}
 */
public class Bullet extends GameElement {
	private Direction direction;
	private BulletType type;
	
	public Bullet(Direction direction, BulletType type, String imageURL) {
		super(imageURL);
		this.direction = direction;
		this.type = type;
		this.setSpeed(15);
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public BulletType getType() {
		return type;
	}

	public void setType(BulletType type) {
		this.type = type;
	}
}
