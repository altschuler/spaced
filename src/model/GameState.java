package model;

import java.util.ArrayList;

import model.elements.Bullet;
import model.elements.Bunker;
import model.elements.Invader;
import model.elements.Player;

/**
 * Represents a game session. A GameState can be intialized in different ways
 * to create different levels. A GameState should never be instatiated directly
 * but be created through the {@link GameStateFactory}.
 */
public class GameState {
	// Elements
	private Player player;
	private ArrayList<Bunker> bunkers;
	private ArrayList<Invader> invaders;
	private ArrayList<Bullet> shots;
	
	private int points;
	private long lastUpdateTime;
	
	public GameState() {
		this.points = 0;
		this.bunkers = new ArrayList<Bunker>();
		this.invaders = new ArrayList<Invader>();
		this.shots = new ArrayList<Bullet>();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public ArrayList<Bunker> getBunkers() {
		return bunkers;
	}

	public ArrayList<Invader> getInvaders() {
		return invaders;
	}

	public ArrayList<Bullet> getShots() {
		return shots;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
