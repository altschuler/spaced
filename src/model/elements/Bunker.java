package model.elements;

import command.ICommand;


/**
 * A bunker blocking shots from invaders 
 */
public class Bunker extends KillableGameElement {
	public Bunker() {
		super(10, "view/sprites/1357850269_present_48.png");
	}

/*	public Bunker(ICommand cmd) {
		super(10, 30, 30, cmd);
	}
*/
}
