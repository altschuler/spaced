package model.elements;

import model.core.Coordinate;

public class Bonus extends KillableGameElement {
    
    private int points;

    public Bonus(int points, int health, Coordinate position) {
        super(health, "view/sprites/present2.png");
        this.setSpeed(7);
        this.setPosition(position);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
