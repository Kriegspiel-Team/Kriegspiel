package model;

/**
 * A Cavalry.
 */
public class Cavalry extends Fighter {
	
	/** The attack charge. */
	private int attackCharge;
	
	/**
	 * Instantiates a new cavalry.
	 *
	 * @param owner the owner
	 */
	public Cavalry(int owner)
	{
		super();
		symbol = 'H';
		speed = 2;
		range = 2;
		attack = 4;
		attackCharge = 7;
		defence = 5;
		enemyAttack = 0;
		allyDefence = 0;
		this.owner = owner;
	}

	/**
	 * Gets the charge attack value.
	 *
	 * @return the charge attack value
	 */
	public int getAttackCharge() {
		return attackCharge;
	}
	
	public void setAttackNull() {
		attack = 0;
		attackCharge = 0;
	}
}
