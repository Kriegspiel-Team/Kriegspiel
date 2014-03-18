package model;

import java.util.HashSet;
import java.util.Set;

import main.Coord;

public class MovableEntity extends Entity {
	
	protected int defense;
	protected int attack;
	protected int speed;
	protected int speedLeft;
	protected int counterDirectionRule;
	protected Set<Coord> possibleMovement;
	
	public MovableEntity(){
		possibleMovement = new HashSet<Coord>();
		counterDirectionRule = 0;
		canContain = false;
	}

	public int getSpeed() {
		return speed;
	}
	
	public void resetPossibleMovement(){
		possibleMovement.clear();
	}
	
	public void addPossibleMovement(Coord c){		
		possibleMovement.add(c);
		
	}

	public Set<Coord> getPossibleMovement() {
		return possibleMovement;
	}

	public int getSpeedLeft() {
		return speedLeft;
	}
	
	public void setSpeedLeft(int speedLeft){
		this.speedLeft = speedLeft;
	}
	
	public void decreaseSpeedLeft(){
		speedLeft--;
	}
	
	public int getCounterDirectionRule() {
		return counterDirectionRule;
	}
	
	public void setCounterDirectionRule(int n) {
		this.counterDirectionRule = n;
	}

	public void decrCounterDirectionRule() {
		this.counterDirectionRule -= 1;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
}
