package model;

public class Cavalry extends MovableEntity {
	
	public Cavalry(int owner)
	{
		super();
		symbol = 'H';
		speed = 2;
		speedLeft = speed;
		this.owner = owner;
	}
}
