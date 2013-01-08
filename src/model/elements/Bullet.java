/**
 * 
 */
package models.elements;

/**
 * A bullet that can be fire either by the player or an {@link Invader}
 */
public class Bullet extends AbstractGameElement {
	private BulletDirection direction;
	
	public Bullet(BulletDirection direction) {
		super(5, 15);
		this.direction = direction;
	}

	public BulletDirection getDirection() {
		return direction;
	}
}
