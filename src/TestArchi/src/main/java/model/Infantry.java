package model;


public class Infantry extends MovableEntity {
	
	public Infantry(int owner)
	{
		super();
		symbol = 'I';
		speed = 1;
		speedLeft = speed;
		this.owner = owner;
	}
}
