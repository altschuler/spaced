package model.elements;

import model.elements.KillableGameElement;

/**
 * Spacecraft that the actual player will control. 
 * Has one life, but there are more of them.
 */
public class Player extends KillableGameElement {
	private int speed = 10;
	private int maxShootFrequency = 450;
	private long timeOfLastShot = 0;
	
	public Player() {
		super(1, 48, 48);
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
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
	
}
