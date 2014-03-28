package model;

public class Cavalry extends Fighter {
	
	private int attackCharge;
	
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

	public int getAttackCharge() {
		return attackCharge;
	}
}
