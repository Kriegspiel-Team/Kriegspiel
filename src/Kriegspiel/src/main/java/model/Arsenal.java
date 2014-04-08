package model;

/**
 * An Arsenal.
 */
public class Arsenal extends UnmovableEntity {
	
	/**
	 * Instantiates a new arsenal.
	 *
	 * @param owner the owner
	 */
	public Arsenal(int owner)
	{
		super();
		symbol = 'A';
		this.owner = owner;
		canContain = true;
	}
	
}
