package model;

/**
 * The Class UnmovableEntity.
 */
public abstract class UnmovableEntity extends Entity {

	/** The entity. */
	private MovableEntity entity;
	
	/**
	 * Sets the entity.
	 *
	 * @param entity the new entity
	 */
	public void setEntity(MovableEntity entity) {
		this.entity = entity;
		
		if(entity==null) {
			symbol = 'A';
		} else {
			symbol = entity.getSymbol();
		}
	}

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	public MovableEntity getEntity() {
		return entity;
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return entity == null;
	}
	
}
