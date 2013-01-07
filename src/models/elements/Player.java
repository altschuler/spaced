package models.elements;

import models.elements.KillableGameElement;

/**
 * @author Simon
 * Spacecraft that the actual player will control. 
 * Has one life, but there are more of them.
 */
public class Player extends KillableGameElement {

	public Player() {
		super(1, 20, 10);
	}
	
}
