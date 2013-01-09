package model.elements;

import model.elements.KillableGameElement;

/**
 * Spacecraft that the actual player will control. Has one life, but there are
 * more of them.
 */
public class Player extends KillableGameElement {
	private int lives = 3;
	private int maxShootFrequency = 450;
	private long timeOfLastShot = 0; //used when space is pressed
	
	public Player(int health) {
		super(health, 48, 48);
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
}
