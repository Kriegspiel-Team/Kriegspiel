package main;

public class Coord {
	public int x;
	public int y;
	
	public Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Boolean equals(Coord c){
		if (c.x == x && c.y ==y)
			return true;
	
		return false;
	}
}
