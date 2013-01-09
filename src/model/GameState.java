package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.elements.Bonus;
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
    private int id;
    private HashMap<PlayerIndex, Player> players;
    private ArrayList<Bunker> bunkers;
    private ArrayList<Invader> invaders;
    private ArrayList<Bonus> bonuss;
    private ArrayList<Bullet> bullets;

    private int points;
    private long lastUpdateTime;
    private long totalGameTime;
    private boolean moveInvadersRight;

    private long lastInvaderShot; //Patricks logik siger at denne skal v�re i GameState
    private ArrayList<Invader> lowestInvaders; 	//ved ikke heeelt med denne

    public GameState(int id) {
            this.id = id;
            this.points = 0;
            this.bunkers = new ArrayList<Bunker>();
            this.invaders = new ArrayList<Invader>();
            this.bonuss = new ArrayList<Bonus>();
            this.bullets = new ArrayList<Bullet>();
            this.lowestInvaders = new ArrayList<Invader>();
            this.lastInvaderShot = 0;
            this.players = new HashMap<PlayerIndex, Player>();
    }

    // For testing purposes
    public void printInfo() {
        System.out.println("ID: " + id);
        System.out.println("Player: x:" + players.get(PlayerIndex.One).getPosition().x
                + " y:" + players.get(PlayerIndex.One).getPosition().y
                + " health:" + players.get(PlayerIndex.One).getHealth());
        for (Bunker bunker : bunkers) {
            System.out.println("Bunker: x:" + bunker.getPosition().x 
                    + " y:" + bunker.getPosition().y 
                    + " health:" + bunker.getHealth());
        }
        for (Invader invader : invaders) {
            System.out.println("Invader: type:" + invader.getType()
                    + " x:" + invader.getPosition().x
                    + " y:" + invader.getPosition().y
                    + " health:" + invader.getHealth());
        }

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
    
    public ArrayList<Bonus> getBonuss() {
        return bonuss;
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
    
//    public GameState clone() {
//    	GameState state = new GameState(this.id);
//    	
//    	// primitives
//    	state.setLastInvaderShot(this.getLastInvaderShot());
//    	state.setLastUpdateTime(this.getLastUpdateTime());
//    	state.setLowestInvaders(this.getLowestInvaders());
//    	state.setMoveInvadersRight(this.getMoveInvadersRight());
//    	state.setPoints(this.getPoints());
//    	state.setTotalGameTime(this.getTotalGameTime());
//    	
//    	// complex types
//    	state.setPlayer(PlayerIndex.One, this.getPlayer(PlayerIndex.One).clone());
//    	state.setPlayer(PlayerIndex.Two, this.getPlayer(PlayerIndex.Two).clone());
//
//    	for (Bonus bonus : this.getBonuss()) 
//			state.getBonuss().add(bonus.clone());
//
//    	for (Invader invader : this.getInvaders())
//			state.getInvaders().add(invader.clone());
//    	
//    	for (Bunker bunker: this.getBunkers())
//			state.getBunkers().add(bunker.clone());
//    	
//    	for (Bullet bullet: this.getBullets())
//			state.getBullets().add(bullet.clone());
//    	
//    	return state;
//    }
}
