package com.sample;

import java.util.ArrayList;
import java.util.List;

public class MovableEntity extends Entity {
	protected int defense;
	protected int attack;
	protected int speed;
	protected List<Coord> possibleMovement;
	
	public MovableEntity(){
		possibleMovement = new ArrayList<Coord>();
	}

	public int getSpeed() {
		return speed;
	}
	
	public void addPossibleMovement(Coord c){
		possibleMovement.add(c);
	}

	public List<Coord> getPossibleMovement() {
		return possibleMovement;
	}
	

	

}
