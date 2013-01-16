package model.elements;

import command.ICommand;

/**
 * A game element with health, meaning it can die/break. 
 * A command can be executed upon death.
 */
public class KillableGameElement extends GameElement {
	private int points;
	private int health;
	private ICommand cmd;

/*	public KillableGameElement(int health, int width, int height) {
		super(width, height); //TODO new Point should be created here
		this.health = health;
	}
*/

	public KillableGameElement(int health, String imageURL) {
		super(imageURL); //TODO new Point should be created here
		this.health = health;
	}
	
/*	public KillableGameElement(int health, int width, int height, ICommand cmd) {
		this(health, width, height);
		this.setCommand(cmd);
	}
*/	
	public int getHealth() {
		return health;
	}

	/**
	 * Utility method that decrements health by one
	 */
	public void healthDown() {
		this.setHealth(this.getHealth() - 1);
	}
	/**
	 * @param health The new health value
	 * The point of setHealth being protected is that subclasses should implement their 
	 * own specific health handling themselves, and not let outside code set their health directly.
	 */
	protected void setHealth(int health) {
		this.health = health;
		
		// execute command if dead and command is non-null
		if (this.isDead() && this.getCommand() != null) {
			this.getCommand().execute();
		}
	}
	
	/**
	 * @return A boolean which tells if the game element is not broken/alive
	 * An element is dead/broken if its health is 0 or less
	 */
	public boolean isDead() {
		return this.health <= 0;
	}
	
	public ICommand getCommand() {
		return cmd;
	}
	public void setCommand(ICommand cmd) {
		this.cmd = cmd;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
