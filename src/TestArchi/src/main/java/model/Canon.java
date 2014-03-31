package model;

/**
 * The Class Canon.
 */
public class Canon extends Fighter {
	
	/**
	 * Instantiates a new canon.
	 *
	 * @param owner the owner
	 */
	public Canon(int owner)
	{
		super();
		symbol = 'c';
		speed = 1;
		range = 3;
		attack = 5;
		defence = 8;
		enemyAttack = 0;
		allyDefence = 0;
		this.owner = owner;
	}
}
