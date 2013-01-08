package model.elements;

import model.elements.KillableGameElement;

/**
 * Spacecraft that the actual player will control. 
 * Has one life, but there are more of them.
 */
public class Player extends KillableGameElement {
	private int playerMovementSpeed = 10;
	private int playerShotFrequency = 750;
	private int timeOfLastShot = 0;
	
	public Player() {
		super(1, 20, 10);
	}
	public int getPlayerMovementSpeed() {
		return playerMovementSpeed;
	}
	public void setPlayerMovementSpeed(int playerMovementSpeed) {
		this.playerMovementSpeed = playerMovementSpeed;
	}
	public int getPlayerShotFrequency() {
		return playerShotFrequency;
	}
	public void setPlayerShotFrequency(int playerShotFrequency) {
		this.playerShotFrequency = playerShotFrequency;
	}
	public int getTimeOfLastShot() {
		return timeOfLastShot;
	}
	public void setTimeOfLastShot(int timeOfLastShot) {
		this.timeOfLastShot = timeOfLastShot;
	}
	
}
