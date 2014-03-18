package model;

public class Canon extends MovableEntity {
	
	public Canon(int owner)
	{
		super();
		symbol = 'c';
		speed = 1;
		range = 3;
		attack = 5;
		defence = 8;
		speedLeft = speed;
		this.owner = owner;
	}
}
