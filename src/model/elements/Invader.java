package model.elements;

import utils.Mathx;
import view.render.SpriteHandler;
import command.ICommand;
import model.core.BulletType;
import model.core.InvaderType;
import model.elements.KillableGameElement;

/**
 * An invader. Comes in different types.
 */
public class Invader extends KillableGameElement {

	private InvaderType type;

	public Invader(InvaderType type, int health, String imageURL) { //TODO: messed up constructor, as there are several types of invaders
		super(health, "view/sprites/invaderA.png");
		switch (type) {
			case A:
				super.setImageURL("view/sprites/invaderA.png");
				break;
			case B:
				super.setImageURL("view/sprites/invaderB.png");
				break;
			default:
				super.setImageURL("view/sprites/invaderC.png");
		}
		SpriteHandler spriteHandler = SpriteHandler.getInstance();
		super.setWidth(spriteHandler.get(super.getImageURL()).getWidth());
		super.setHeight((spriteHandler.get(super.getImageURL()).getHeight()));
		this.setSpeed(10);
		this.setType(type);
	}

/*	public Invader(InvaderType type, int health, ICommand cmd) {
		super(health, 48, 48, cmd);
		this.setType(type);
	}
*/
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
