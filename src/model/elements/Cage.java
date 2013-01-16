package model.elements;

import model.core.Coordinate;

public class Cage extends KillableGameElement {
	private int cageOrbitTime; //The time it takes the cage to orbit Nicholas
	private int cageID;
	
	public Cage(Coordinate position, int cageID) {
		super(2, "cage.png");
		this.setCageOrbitTime(4000);
		this.setPosition(position);
		this.setCageID(cageID);
	}

	public int getCageOrbitTime() {
		return cageOrbitTime;
	}

	public void setCageOrbitTime(int cageOrbitTime) {
		this.cageOrbitTime = cageOrbitTime;
	}

	public int getCageID() {
		return cageID;
	}

	public void setCageID(int cageID) {
		this.cageID = cageID;
	}
}
