package main;

/**
 * This represents a pair of coordinates.
 */
public class Coord {
	
	/** The x. */
	public Integer x;
	
	/** The y. */
	public Integer y;
	
	/**
	 * Instantiates a new Coord.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Coord(int x, int y){
		this.x = new Integer(x);
		this.y = new Integer(y);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return 100+x.hashCode() + y.hashCode(); //HashCode unique for each couple of coordinates
	}
}
