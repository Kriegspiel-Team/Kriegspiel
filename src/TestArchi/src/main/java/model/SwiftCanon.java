package model;

public class SwiftCanon extends MovableEntity {
	
	public SwiftCanon(int owner)
	{
		super();
		symbol = 'C';
		speed = 2;
		range = 3;
		attack = 5;
		defence = 8;
		enemyAttack = 0;
		allyDefence = 0;
		speedLeft = speed;
		this.owner = owner;
	}
}
