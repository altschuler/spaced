package model.elements;

import command.ICommand;

import model.elements.KillableGameElement;

/**
 * An invader. Comes in different types.
 */
public class Invader extends KillableGameElement {
	//TODO must implement different types of invaders
	private int InvaderSpeed = 15;
	
	public Invader(int health) {
		super(health, 48, 48);
	}

	public Invader(int health, ICommand cmd) {
		super(health, 48, 48, cmd);
	}

	public int getSpeed() {
		return InvaderSpeed;
	}

}
