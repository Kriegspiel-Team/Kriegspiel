package model;

/**
 * The Class Infantry.
 */
public class Infantry extends Fighter {
	
	/**
	 * Instantiates a new infantry.
	 *
	 * @param owner the owner
	 */
	public Infantry(int owner)
	{
		super();
		symbol = 'i';
		speed = 1;
		range = 2;
		attack = 4;
		defence = 6;
		enemyAttack = 0;
		allyDefence = 0;
		this.owner = owner;
	}
}
