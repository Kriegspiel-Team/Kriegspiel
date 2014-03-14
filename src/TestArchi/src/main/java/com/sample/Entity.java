package com.sample;

import com.sample.Coord;

public class Entity {
	protected char symbol;
	protected int owner;
	protected Coord coord;
	protected Boolean canContain = false;
	
	public Boolean canContain() {
		return canContain;
	}

	public char getSymbol(){
		return symbol;
	}
	
	public void setCoord(Coord c){
		coord = c;
	}

	public Coord getCoord() {
		return coord;
	}
	
	public int getOwner()
	{
		return this.owner;
	}
}
