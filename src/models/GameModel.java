package models;

import java.util.Observable;

public class GameModel extends Observable {

	private String gameText;

	public GameModel() {
		this.setGameText("I am default");
	}

	public String getGameText() {
		return gameText;
	}

	public void setGameText(String gameText) {
		this.gameText = gameText;
	}

}
