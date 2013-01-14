package model.elements;

import model.core.BonusType;
import model.core.Coordinate;

public class Bonus extends KillableGameElement {
    
    private int points;
    private BonusType bonusType;

    public Bonus(Coordinate position) {
        super(1, "heart.png");
        this.setSpeed(7);
        this.setPosition(position);
        this.points = 10;
        double whichBonusType = Math.random();
        if(whichBonusType < 2.0/5){
        	this.setBonusType(BonusType.Score);
            this.setImageURL("present2.png");
        }else if(whichBonusType < 4.0/5){
        	this.setBonusType(BonusType.Slow);
        	this.setImageURL("snowFlake.png");
        }else{
        	this.setBonusType(BonusType.Health); //this is quite a bonus, so the chances of getting it is smaller than the other bonuses
        }
    }

    public int getPoints() {
        return points;
    }

	public BonusType getBonusType() {
		return bonusType;
	}

	public void setBonusType(BonusType bonusType) {
		this.bonusType = bonusType;
	}
}
