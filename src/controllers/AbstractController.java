package controllers;

import models.GameModel;
import views.MainView;

public class AbstractController {

	protected MainView gw;
	protected GameModel gm;
	
	public AbstractController(MainView gw, GameModel gm) {
		this.gw = gw;
		this.gm = gm;
	}
}
