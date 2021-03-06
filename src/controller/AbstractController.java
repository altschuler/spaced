package controller;

import model.MainModel;
import view.MainView;

public abstract class AbstractController {

	protected MainView mainView;
	protected MainModel gameModel;
	
	public AbstractController(MainView gw, MainModel gm) {
		this.mainView = gw;
		this.gameModel = gm;
	}
}
