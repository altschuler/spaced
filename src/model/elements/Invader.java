package model.elements;

import command.ICommand;

import model.elements.KillableGameElement;

/**
 * An invader. Comes in different types.
 */
public class Invader extends KillableGameElement {
	//TODO must implement different types of invaders
	public Invader(int health) {
		super(health, 20, 20);
	}

	public Invader(int health, ICommand cmd) {
		super(health, 20, 20, cmd);
	}

}
