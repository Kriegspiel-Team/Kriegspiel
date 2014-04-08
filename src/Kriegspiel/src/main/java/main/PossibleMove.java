package main;

import model.MovableEntity;

/**
 * This class represents a playable move of an unit.
 */
public class PossibleMove {

	/** The target coordinates. */
	private Coord coord;
	
	/** The movable entity. */
	private MovableEntity movableEntity;
	
	/**
	 * Instantiates a new possible move.
	 *
	 * @param coord the coordinates
	 * @param movableEntity the movable entity
	 */
	public PossibleMove(Coord coord, MovableEntity movableEntity){
		this.coord = coord;
		this.movableEntity = movableEntity;
	}

	/**
	 * Gets the coordinates.
	 *
	 * @return the coordinates
	 */
	public Coord getCoord() {
		return coord;
	}

	/**
	 * Gets the movable entity.
	 *
	 * @return the movable entity
	 */
	public MovableEntity getMovableEntity() {
		return movableEntity;
	}
	
}
