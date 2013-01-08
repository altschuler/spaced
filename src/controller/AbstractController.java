package controller;

import model.GameModel;
import view.MainView;

public class AbstractController {

	protected MainView mainView;
	protected GameModel gameModel;
	
	public AbstractController(MainView gw, GameModel gm) {
		this.mainView = gw;
		this.gameModel = gm;
	}
}
