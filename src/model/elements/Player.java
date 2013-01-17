package model.elements;

import model.core.BulletType;
import model.core.Difficulty;
import model.core.PlayerIndex;

/**
 * Spacecraft that the actual player will control. Has one life, but there are
 * more of them.
 */
public class Player extends KillableGameElement {
	private PlayerIndex id;
	
	private int lives = 3;
	private int points = 0;
	private long timeOfLastShot = 0;
	private BulletType weapon = BulletType.Normal;
	
	
	public Player(int health, String imageURL) {
		super(health, imageURL);
		this.setSpeed(10);
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

	public BulletType getWeapon() {
		return weapon;
	}

	public void setWeapon(BulletType weapon) {
		this.weapon = weapon;
	}

	public int getPoints() {
		return points;
	}
	
	public int getFinalPoints(Difficulty activeDifficulty){
		int pointsFromExtraLives;
		
		if(this.getLives() > 0){
			pointsFromExtraLives = (this.getLives()-1) * 500 * (activeDifficulty.getId()+1);
		}else{
			pointsFromExtraLives = 0;
		}
		return this.getPoints() + pointsFromExtraLives;
	}

	public void setPoints(int point) {
		this.points = point;
	}
}
