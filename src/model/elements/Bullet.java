/**
 * 
 */
package model.elements;

/**
 * A bullet that can be fire either by the player or an {@link Invader}
 */
public class Bullet extends AbstractGameElement {
	private BulletDirection direction;
	private int speed = 15;
	
	public Bullet(BulletDirection direction) {
		super(5, 15);
		this.direction = direction;
	}

	public BulletDirection getDirection() {
		return direction;
	}
	
	public int getSpeed(){
		return speed;
	}
}
