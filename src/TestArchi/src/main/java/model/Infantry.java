package model;


public class Infantry extends MovableEntity {
	
	public Infantry(int owner)
	{
		super();
		symbol = 'i';
		speed = 1;
		speedLeft = speed;
		this.owner = owner;
	}
}
