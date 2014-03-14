package com.sample;


public class Infantry extends MovableEntity {
	public Infantry(){
		super();
		
		symbol = 'I';
		speed = 1;
		speedLeft = speed;
	}
	
	public Infantry(int owner)
	{
		super();
		symbol = 'I';
		speed = 1;
		speedLeft = speed;
		this.owner = owner;
	}
}
