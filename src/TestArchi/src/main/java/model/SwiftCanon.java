package model;

public class SwiftCanon extends MovableEntity {
	
	public SwiftCanon(int owner)
	{
		super();
		symbol = 'C';
		speed = 2;
		speedLeft = speed;
		this.owner = owner;
	}
}
