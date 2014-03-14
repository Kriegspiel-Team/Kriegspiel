package com.sample;

public class Entity {
	protected char symbol;
	protected int owner;
	protected Coord coord;
	
	public char getSymbol(){
		return symbol;
	}
	
	public void setCoord(Coord c){
		coord = c;
	}

	public Coord getCoord() {
		return coord;
	}
	
	
}
