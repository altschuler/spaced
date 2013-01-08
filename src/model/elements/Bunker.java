package model.elements;

import command.ICommand;


/**
 * A bunker blocking shots from invaders 
 */
public class Bunker extends KillableGameElement {
	public Bunker() {
		super(10, 30, 30); //TODO refactor out. Maybe create BunkerType enum which can map to health values 
	}

	public Bunker(ICommand cmd) {
		super(10, 30, 30, cmd);
	}
}
