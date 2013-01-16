package model.elements;

import java.util.ArrayList;

import model.core.Coordinate;

public class NicholasCage extends KillableGameElement {
	final private int cageRadius = 180;
	private String[] nicholases;
	private long nicholasTime;
	private boolean nicholasOpening;
	private ArrayList<Cage> cages;
	private Coordinate nicholasCenter;
	private int noOfCages;
	private double directionMultiplier;
	
	
	public NicholasCage(Coordinate spawnPosition, int noOfCages, int lives, double directionMultiplier) {
		super(lives, "nicholas01.png");
		this.setPosition(spawnPosition);
		nicholases = new String[] {
				"nicholas01.png","nicholas02.png","nicholas03.png","nicholas04.png","nicholas05.png",
				"nicholas06.png","nicholas07.png","nicholas08.png","nicholas09.png","nicholas10.png"		};
		this.setNicholasTime(0);
		this.setDirectionMultiplier(directionMultiplier);
		this.setNicholasOpening(true);
		this.setCages(new ArrayList<Cage>());
		this.setNicholasCenter(new Coordinate(this.getPosition().x + this.getWidth()/2 - 20,this.getPosition().y + this.getHeight()/2 + 55));
		this.setNoOfCages(noOfCages);
		this.addCages();
	}
	
	private void updateCenter(){
		this.setNicholasCenter(new Coordinate(this.getPosition().x + this.getWidth()/2 - 20 , this.getPosition().y + this.getHeight()/2 - 55));
	}
	
	private void addCages(){
		double nCageCenterX = this.getNicholasCenter().x;
		double nCageCenterY = this.getNicholasCenter().y;
		int cageRadius = this.getCageRadius();
		int noOfCages = this.getNoOfCages();
		for (int i = 0; i < noOfCages; i++) {
			Cage cage = new Cage(new Coordinate(nCageCenterX + cageRadius * Math.cos(i * 3.14159 * 2.0 / noOfCages), nCageCenterY + cageRadius * Math.sin(i * 3.14159 * 2.0 / noOfCages)-110),i);
			cage.setPoints(20);
			this.getCages().add(cage);
		}
	}
	
	public void updateNicholas(long timeDelta){
		this.updateCenter();
		this.setNicholasTime(this.getNicholasTime() + timeDelta);
		int nicholasDecider = (int) (this.getNicholasTime()/100) % 20;

		if(nicholasDecider < 10){
			this.setImageURL(nicholases[nicholasDecider]);
		}else{
			this.setImageURL(nicholases[19-nicholasDecider]);
			
		}
	}
	
	public void moveCages(){
		double whereInMovement; //sorry 'bout the name, it says how much of a round/revolution the cage has moved.
		int noOfCages = this.getNoOfCages();
		double multiplierNormalized = this.getDirectionMultiplier()/Math.abs(this.getDirectionMultiplier());
		for(Cage cage : this.getCages()){
			whereInMovement = (double) (this.getNicholasTime() % cage.getCageOrbitTime()) / cage.getCageOrbitTime();
			whereInMovement *= 3.14159265359 * 2;
			
			cage.setPosition(this.getCageRadius()*Math.cos(whereInMovement * multiplierNormalized+ cage.getCageID() * 3.14159 * 2.0 / noOfCages) ,this.getCageRadius()*Math.sin(whereInMovement * multiplierNormalized + cage.getCageID() * 3.14159 * 2.0 / noOfCages));
			cage.move(this.getNicholasCenter());	
		}
	}

	public String[] getNicholases() {
		return nicholases;
	}

	public void setNicholases(String[] nicholases) {
		this.nicholases = nicholases;
	}

	public long getNicholasTime() {
		return nicholasTime;
	}

	public void setNicholasTime(long nicholasSpawnTime) {
		this.nicholasTime = nicholasSpawnTime;
	}

	public boolean isNicholasOpening() {
		return nicholasOpening;
	}

	public void setNicholasOpening(boolean nicholasOpening) {
		this.nicholasOpening = nicholasOpening;
	}

	public int getCageRadius() {
		return cageRadius;
	}

	public ArrayList<Cage> getCages() {
		return cages;
	}

	public void setCages(ArrayList<Cage> cages) {
		this.cages = cages;
	}

	public Coordinate getNicholasCenter() {
		return nicholasCenter;
	}

	public void setNicholasCenter(Coordinate nicholasCenter) {
		this.nicholasCenter = nicholasCenter;
	}

	public int getNoOfCages() {
		return noOfCages;
	}

	public void setNoOfCages(int noOfCages) {
		this.noOfCages = noOfCages;
	}

	public double getDirectionMultiplier() {
		return directionMultiplier;
	}

	public void setDirectionMultiplier(double direction) {
		this.directionMultiplier = direction;
	}
}
