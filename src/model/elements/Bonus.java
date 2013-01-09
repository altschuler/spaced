package model.elements;

public class Bonus extends KillableGameElement {
    
    private int points;
    private int speed = 10;

    public Bonus(int points, int health) {
        super(health, 48, 48);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    
    
}
