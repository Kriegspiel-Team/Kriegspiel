package main;

public class Coord {
	public Integer x;
	public Integer y;
	
	public Coord(int x, int y){
		this.x = new Integer(x);
		this.y = new Integer(y);
	}
	
	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof Coord)){
			return false;
		}
		Coord c = ((Coord)o);
		if (c.x.intValue() == x.intValue() && c.y.intValue() == y.intValue()){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return 100+x.hashCode() + y.hashCode(); //HashCode unique for each couple of coordinates
	}
}
