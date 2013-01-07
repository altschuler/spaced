package models.elements;

import commands.ICommand;

import models.elements.KillableGameElement;

public class Invader extends KillableGameElement {
	
	public Invader(int health) {
		super(health, 20, 20);
		// TODO Auto-generated constructor stub
	}

	public Invader(int health, ICommand cmd) {
		super(health, 20, 20, cmd);
		// TODO Auto-generated constructor stub
	}

}
