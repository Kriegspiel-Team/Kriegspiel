package com.sample;

public class Relay extends MovableEntity {
	
	public Relay(int owner)
	{
		super();
		symbol = 'r';
		speed = 1;
		speedLeft = speed;
		this.owner = owner;
	}
}
