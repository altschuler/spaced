package model.elements;

import model.core.BulletType;
import model.core.PlayerIndex;

/**
 * Spacecraft that the actual player will control. Has one life, but there are
 * more of them.
 */
public class Player extends KillableGameElement {
	private PlayerIndex id;
	
	private int lives = 3;
	private int maxShootFrequency = 450;
	private long timeOfLastShot = 0;
	private BulletType playersWeapon = BulletType.Normal;
	
	
	public Player(int health, String imageURL) {
		super(health, imageURL);
		this.setSpeed(10);
	}

	public int getMaxShootFrequency() {
		return maxShootFrequency;
	}

	public void setMaxShootFrequency(int maxShotFrequency) {
		this.maxShootFrequency = maxShotFrequency;
	}

	public long getTimeOfLastShot() {
		return timeOfLastShot;
	}

	public void setTimeOfLastShot(long timeOfLastShot) {
		this.timeOfLastShot = timeOfLastShot;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void livesDown() {
		this.setLives(this.getLives() - 1);
	}

	public PlayerIndex getId() {
		return id;
	}

	public void setId(PlayerIndex id) {
		this.id = id;
	}
}
