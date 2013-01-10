package model;

import java.util.Date;

import model.core.Difficulty;

public class HighscoreEntry {
	
	public static HighscoreEntry parse(String xmlString) {
		//TODO implement
		return new HighscoreEntry();
	}
	
	private String playerName;
	private int score;
	private Difficulty difficulty;
	private Date creationDate;
	
	public HighscoreEntry() {
		
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
