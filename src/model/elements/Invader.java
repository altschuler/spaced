package model.elements;

import command.ICommand;
import model.elements.KillableGameElement;

/**
 * An invader. Comes in different types.
 */
public class Invader extends KillableGameElement {
	
	private InvaderType type;
	private int InvaderSpeed = 10;
	
	public Invader(InvaderType _type, int health) {
		super(health, 48, 48);
                this.type = _type;
	}
	public Invader(InvaderType _type, int health, String imageURL) {
		super(health, 48, 48, imageURL);
                this.type = _type;
	}

	public Invader(InvaderType _type, int health, ICommand cmd) {
		super(health, 48, 48, cmd);
                this.type = _type;
	}

	public int getSpeed() {
		return InvaderSpeed;
	}
        
        public InvaderType getType() {
            return type;
        }

}
