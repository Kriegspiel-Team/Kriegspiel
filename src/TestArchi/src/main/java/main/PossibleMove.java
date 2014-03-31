package main;

import model.MovableEntity;

/**
 * The Class PossibleMove.
 */
public class PossibleMove {

	/** The coord. */
	private Coord coord;
	
	/** The movable entity. */
	private MovableEntity movableEntity;
	
	/**
	 * Instantiates a new possible move.
	 *
	 * @param coord the coord
	 * @param movableEntity the movable entity
	 */
	public PossibleMove(Coord coord, MovableEntity movableEntity){
		this.coord = coord;
		this.movableEntity = movableEntity;
	}

	/**
	 * Gets the coord.
	 *
	 * @return the coord
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
