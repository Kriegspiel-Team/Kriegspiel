package com.sample;

import java.util.ArrayList;

public class MovableEntity extends Entity {
	protected int defense;
	protected int attack;
	protected int speed;
	protected ArrayList<Coord> possibleMovement;
	
	public MovableEntity(){
		possibleMovement = new ArrayList<Coord>();
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
	
}
