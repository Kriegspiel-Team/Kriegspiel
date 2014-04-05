package model;

import main.Coord;

/**
 * This represents an Entity.
 */
public abstract class Entity {
	
	/** The ASCII symbol used to represent the Entity in the board. */
	protected char symbol;
	
	/** The entity's owner. */
	protected int owner = -1;
	
	/** The entity's coordinates. */
	protected Coord coord;
	
	/** Whether it can contain another Entity. */
	protected Boolean canContain = false;
	
	/**
	 * Can contain.
	 *
	 * @return whether the unit can contain another unit
	 */
	public Boolean canContain() {
		return canContain;
	}

	/**
	 * Gets the ASCII symbol of the entity.
	 *
	 * @return the ASCII symbol
	 */
	public char getSymbol(){
		return symbol;
	}
	
	/**
	 * Sets the coordinates of the entity.
	 *
	 * @param c the new coordinates
	 */
	public void setCoord(Coord c){
		coord = c;
	}

	/**
	 * Gets the coordinates of the entity.
	 *
	 * @return the coordinates
	 */
	public Coord getCoord() {
		return coord;
	}
	
	/**
	 * Gets the owner of the entity.
	 *
	 * @return the owner
	 */
	public int getOwner()
	{
		return this.owner;
	}
}
