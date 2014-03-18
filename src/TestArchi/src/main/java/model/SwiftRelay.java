package model;

public class SwiftRelay extends MovableEntity {
	
	public SwiftRelay(int owner)
	{
		super();
		symbol = 'R';
		speed = 2;
		speedLeft = speed;
		this.owner = owner;
	}
}
