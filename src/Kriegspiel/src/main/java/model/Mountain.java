package model;

/**
 * A Mountain.
 */
public class Mountain extends UnmovableEntity {
	
	/**
	 * Instantiates a new mountain.
	 */
	public Mountain(){
		symbol = '^';
		canContain = false;
	}
	
	public void setEntity(Entity e) {
		
	}
}
