package model.core;

public class Difficulty {
	
	private int id;
	private String name;
	private int invaderSpeed;
	private int invaderShootFreq;
	private int playerSpeed;
	private int playerShootFreq;

	public Difficulty(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Difficulty(int id, String name, int invaderSpeed, int invaderShootFreq, int playerSpeed, int playerShootFreq) {
		this(id, name);
		this.invaderSpeed = invaderSpeed;
		this.invaderShootFreq = invaderShootFreq;
		this.playerSpeed = playerSpeed;
		this.playerShootFreq = playerShootFreq;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getInvaderSpeed() {
		return this.invaderSpeed;
	}

	public int getInvaderShootFreq() {
		return this.invaderShootFreq;
	}

	public int getPlayerSpeed() {
		return this.playerSpeed;
	}

	public int getPlayerShootFreq() {
		return this.playerShootFreq;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInvader(int invaderSpeed, int invaderShootFreq) {
		this.invaderSpeed = invaderSpeed;
		this.invaderShootFreq = invaderShootFreq;
	}

	public void setInvaderSpeed(int invaderSpeed) {
		this.invaderSpeed = invaderSpeed;
	}

	public void setInvaderShootFreq(int invaderShootFreq) {
		this.invaderShootFreq = invaderShootFreq;
	}

	public void setPlayer(int playerSpeed, int playerShootFreq) {
		this.playerSpeed = playerSpeed;
		this.playerShootFreq = playerShootFreq;
	}

	public void setPlayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	public void setPlayerShootFreq(int playerShootFreq) {
		this.playerShootFreq = playerShootFreq;
	}

}
