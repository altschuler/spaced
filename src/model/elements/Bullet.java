/**
 * 
 */
package model.elements;

/**
 * A bullet that can be fire either by the player or an {@link Invader}
 */
public class Bullet extends GameElement {
	private BulletDirection direction;
	
	public Bullet(BulletDirection direction) {
		super(5, 15);
		this.direction = direction;
		this.setSpeed(15);
	}

	public BulletDirection getDirection() {
		return direction;
	}

	public void setDirection(BulletDirection direction) {
		this.direction = direction;
	}

//	@Override
//	public Bullet clone() {
//		Bullet b = (Bullet) super.clone();
//		b.setDirection(this.getDirection());
//		return b;
//	}
}
