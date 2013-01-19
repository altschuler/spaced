package model.elements;

/**
 * A bunker blocking shots from both players and {@link Invader}s. 
 */
public class Bunker extends KillableGameElement {
	public Bunker(int health) {
		super(health, "bunkerPart.png");
	}
}
