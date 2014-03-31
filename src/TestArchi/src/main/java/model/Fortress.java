package model;

/**
 * The Class Fortress.
 */
public class Fortress extends UnmovableEntity {

	/** The fortress bonus. */
	public static int FORTRESS_BONUS = 4;
	
	/**
	 * Instantiates a new fortress.
	 */
	public Fortress() {
		symbol = 'F';
		canContain = true;
	}
}
