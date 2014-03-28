package model;

import main.Coord;

public class Entity {
	protected char symbol;
	protected int owner = -1;
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
