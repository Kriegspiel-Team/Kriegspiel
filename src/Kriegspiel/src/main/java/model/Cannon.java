package model;

/**
 * A Canon.
 */
public class Cannon extends Fighter {
	
	/**
	 * Instantiates a new canon.
	 *
	 * @param owner the owner
	 */
	public Cannon(int owner)
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
