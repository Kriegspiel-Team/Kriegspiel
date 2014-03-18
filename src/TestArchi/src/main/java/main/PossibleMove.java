package main;

import model.MovableEntity;

public class PossibleMove {

	private Coord coord;
	private MovableEntity movableEntity;
	
	public PossibleMove(Coord coord, MovableEntity movableEntity){
		this.coord = coord;
		this.movableEntity = movableEntity;
	}

	public Coord getCoord() {
		return coord;
	}

	public MovableEntity getMovableEntity() {
		return movableEntity;
	}
	
}
