package model.elements;

import command.ICommand;

import model.elements.KillableGameElement;

/**
 * An invader. Comes in different types.
 */
public class Invader extends KillableGameElement {
	//TODO must implement different types of invaders
	private int InvaderSpeed = 5;
        private InvaderType type;
	
	public Invader(InvaderType _type, int health) {
		super(health, 48, 48);
                this.type = _type;
	}

	public Invader(InvaderType _type, int health, ICommand cmd) {
		super(health, 48, 48, cmd);
                this.type = _type;
	}

	public int getInvaderSpeed() {
		return InvaderSpeed;
	}

}
