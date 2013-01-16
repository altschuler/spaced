package model.elements;

import model.core.BulletType;
import model.core.InvaderType;
import service.resources.SpriteHandler;

/**
 * An invader. Comes in different types.
 */
public class Invader extends KillableGameElement {

	private InvaderType type;
	private int frozenTime;

	public Invader(InvaderType type, int health) { //TODO: messed up constructor, as there are several types of invaders
		super(health, "invaderA.png");
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
	}

	public int getFrozenTime() {
		return frozenTime;
	}

	public void setFrozenTime(int frozenTime) {
		this.frozenTime = frozenTime;
	}
	
	public double getSpeedMultiplier(long timeDelta){
		if(this.getFrozenTime() > 0){	//The invaders are slowed!
			this.setFrozenTime(this.getFrozenTime()- (int) timeDelta);
			return 0.5;
		}else{
			return 1.0;
		}
	}
	
}
