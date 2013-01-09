package model.elements;

public class Bonus extends KillableGameElement {
    
    private int points;

    public Bonus(int points, int health) {
        super(health, 48, 48);
        this.setSpeed(10);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

//	@Override
//	public Bonus clone() {
//		Bonus b = (Bonus) super.clone();
//		b.setPoints(this.getPoints());
//
//		return b;
//	} 
}
