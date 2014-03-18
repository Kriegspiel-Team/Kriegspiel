package model;


public class Infantry extends MovableEntity {
	
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
		speedLeft = speed;
		this.owner = owner;
	}
}
