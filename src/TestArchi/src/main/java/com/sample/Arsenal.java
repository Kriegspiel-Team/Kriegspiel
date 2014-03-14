package com.sample;

public class Arsenal extends UnmovableEntity {
	
	public Arsenal(int owner)
	{
		super();
		symbol = 'A';
		this.owner = owner;
		canContain = true;
	}
	
}
