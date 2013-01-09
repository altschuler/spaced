package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.elements.Bullet;
import model.elements.Bunker;
import model.elements.Invader;
import model.elements.Player;
import model.elements.PlayerIndex;

/**
 * Represents a game session. A GameState can be intialized in different ways
 * to create different levels. A GameState should never be instatiated directly
 * but be created through the {@link GameStateFactory}.
 */
public class GameState {
	// Elements
        private int id;
	private HashMap<PlayerIndex, Player> players;
	private ArrayList<Bunker> bunkers;
	private ArrayList<Invader> invaders;
	private ArrayList<Bullet> bullets;
	
	private int points;
	private long lastUpdateTime;
	private long totalGameTime;
	private boolean moveInvadersRight;
	
	private long lastInvaderShot;
	private ArrayList<Invader> lowestInvaders;

	public GameState(int id) {
                this.id = id;
		this.points = 0;
		this.bunkers = new ArrayList<Bunker>();
		this.invaders = new ArrayList<Invader>();
		this.bullets = new ArrayList<Bullet>();
		this.lowestInvaders = new ArrayList<Invader>();
		this.lastInvaderShot = 0;
		this.players = new HashMap<PlayerIndex, Player>();
	}

	public Player getPlayer(PlayerIndex idx) {
		return this.players.get(idx);
	}

	public void setPlayer(PlayerIndex idx, Player player) {
		this.players.put(idx, player);
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

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public long getTotalGameTime() {
		return totalGameTime;
	}

	public void setTotalGameTime(long totalGameTime) {
		this.totalGameTime = totalGameTime;
	}

	public void addTotalGameTime(long time) {
		this.totalGameTime += time;
	}

	public boolean getMoveInvadersRight() {
		return this.moveInvadersRight;
	}

	public void setMoveInvadersRight(boolean moveInvadersRight) {
		this.moveInvadersRight = moveInvadersRight;
	}

	public long getLastInvaderShot() {
		return lastInvaderShot;
	}

	public void setLastInvaderShot(long lastInvaderShot) {
		this.lastInvaderShot = lastInvaderShot;
	}

	public ArrayList<Invader> getLowestInvaders() {
		return lowestInvaders;
	}

	public void setLowestInvaders(ArrayList<Invader> lowestInvaders) {
		this.lowestInvaders = lowestInvaders;
	}
}
