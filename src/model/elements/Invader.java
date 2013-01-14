package model.elements;

import model.core.BulletType;
import model.core.InvaderType;
import view.render.SpriteHandler;

/**
 * An invader. Comes in different types.
 */
public class Invader extends KillableGameElement {

	private InvaderType type;
	private int points;

	public Invader(InvaderType type, int health) { //TODO: messed up constructor, as there are several types of invaders
		super(health, "resources/sprites/invaderA.png");
		switch (type) {
			case A:
				super.setImageURL("resources/sprites/invaderA.png");
				this.points=10;
				break;
			case B:
				super.setImageURL("resources/sprites/invaderB.png");
				this.points=10;
				break;
			case C:
				super.setImageURL("resources/sprites/invaderCRed.png");
				this.points=30;
				break;
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
	
	//@override
	public void healthDown() {
		this.setHealth(this.getHealth() - 1);
		switch(this.getType()){
			case C:
				if(this.getHealth() == 1){
					this.setImageURL("resources/sprites/invaderC.png");
				}else if(this.getHealth() <= 2){
					this.setImageURL("resources/sprites/invaderCRedSemi.png");
				}
				break;
			default:
				break;
		}
	}

	public int getPoints() {
		return points;
	}
	
}
