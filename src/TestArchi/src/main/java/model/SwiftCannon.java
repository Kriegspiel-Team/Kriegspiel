package model;

/**
 * The Class SwiftCanon.
 */
public class SwiftCannon extends Fighter {
	
	/**
	 * Instantiates a new swift canon.
	 *
	 * @param owner the owner
	 */
	public SwiftCannon(int owner)
	{
		super();
		symbol = 'C';
		speed = 2;
		range = 3;
		attack = 5;
		defence = 8;
		enemyAttack = 0;
		allyDefence = 0;
		this.owner = owner;
	}
}
