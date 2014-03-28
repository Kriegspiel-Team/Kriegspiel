package model;


public class Infantry extends Fighter {
	
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
