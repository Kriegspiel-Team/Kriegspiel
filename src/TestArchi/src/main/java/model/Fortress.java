package model;

public class Fortress extends UnmovableEntity {

	public static int FORTRESS_BONUS = 4;
	
	public Fortress() {
		symbol = 'F';
		canContain = true;
	}
}
