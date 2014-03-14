package com.sample;

public class Canon extends MovableEntity {
	
	public Canon(int owner)
	{
		super();
		symbol = 'c';
		speed = 1;
		speedLeft = speed;
		this.owner = owner;
	}
}
