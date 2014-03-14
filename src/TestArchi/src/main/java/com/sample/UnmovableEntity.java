package com.sample;

public class UnmovableEntity extends Entity {

	private MovableEntity entity;
	
	public void setEntity(MovableEntity entity) {
		this.entity = entity;
		
		if(entity==null) {
			symbol = 'A';
		} else {
			symbol = entity.getSymbol();
		}
	}

	public MovableEntity getEntity() {
		return entity;
	}

	public boolean isEmpty() {
		return entity == null;
	}
	
}
