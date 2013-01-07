/**
 * 
 */
package models.elements;

/**
 * @author Simon
 *
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
