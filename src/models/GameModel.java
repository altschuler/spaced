package models;

import java.util.Observable;

/**
 * @author Simon
 * The top-level game model which holds overall game state
 */
public class GameModel extends Observable {

	private String playerName;
	private int highScore;

	public GameModel() {
		//TODO defaults should be placed in configuration files
		this.setPlayerName("Anonymous");
		this.setHighScore(0);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		
		this.notifyObservers();
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
		
		this.notifyObservers();
	}

}
