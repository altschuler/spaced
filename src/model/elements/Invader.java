package model.elements;

import command.ICommand;
import model.core.BulletType;
import model.core.InvaderType;
import model.elements.KillableGameElement;

/**
 * An invader. Comes in different types.
 */
public class Invader extends KillableGameElement {

	private InvaderType type;

	public Invader(InvaderType type, int health) {
		super(health, 48, 48);
		this.setSpeed(10);
		this.setType(type);
	}
/*
	public Invader(InvaderType type, int health, String imageURL) {
		super(health,imageURL);
		this.setType(type);
	}
*/
	public Invader(InvaderType type, int health, ICommand cmd) {
		super(health, 48, 48, cmd);
		this.setType(type);
	}

	public BulletType getBulletType() {
		switch (this.getType()) {
			case C:
				return BulletType.Homing;
			default:
				return BulletType.Normal;
		}
	}

	public InvaderType getType() {
		return type;
	}

	public void setType(InvaderType type) {
		this.type = type;
	}
}
