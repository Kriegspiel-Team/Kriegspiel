package model;

/**
 * A mountain pass.
 */
public class MountainPass extends UnmovableEntity {

	/** The mountain pass bonus. */
	public static int MOUNTAIN_PASS_BONUS = 2;
	
	/**
	 * Instantiates a new mountain pass.
	 */
	public MountainPass() {
		symbol = ' ';
		canContain = true;
	}
}
