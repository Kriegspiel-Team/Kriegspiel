package com.sample;

import java.util.ArrayList;

public class MovableEntity extends Entity {
	protected int defense;
	protected int attack;
	protected int speed;
	protected int speedLeft;
	protected int counterDirectionRule;
	protected ArrayList<Coord> possibleMovement;
	
	public MovableEntity(){
		possibleMovement = new ArrayList<Coord>();
		counterDirectionRule = 0;
	}

	public int getSpeed() {
		return speed;
	}
	
	public void resetPossibleMovement(){
		possibleMovement.clear();
	}
	
	public void addPossibleMovement(Coord c){
		Boolean exists = false;
		for(Coord coord : possibleMovement){
			if (c.equals(coord))
				exists = true;
		}
		
		if (!exists)
			possibleMovement.add(c);
	}

	public ArrayList<Coord> getPossibleMovement() {
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
	
}
