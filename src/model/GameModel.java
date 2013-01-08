package model;

import java.util.Observable;

/**
 * The top-level game model which holds overall game state
 */
public class GameModel extends Observable {

	public static final int SCREEN_WIDTH = 300;
	public static final int SCREEN_HEIGHT = 600;
	
	private String playerName;
	private int highScore;
	private GameState activeGameState;

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

	public GameState getActiveGameState() {
		return activeGameState;
	}

	public void setActiveGameState(GameState activeGameState) {
		this.activeGameState = activeGameState;
	}

}
