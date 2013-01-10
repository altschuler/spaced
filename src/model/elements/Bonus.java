package model.elements;

public class Bonus extends KillableGameElement {
    
    private int points;

    public Bonus(int points, int health) {
        super(health, "view/sprites/1357850269_present_48.png");
        this.setSpeed(10);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
