package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.Arsenal;
import model.Cavalry;
import model.Entity;
import model.Fighter;
import model.Fortress;
import model.Mountain;
import model.MountainPass;
import model.MovableEntity;
import model.Relay;
import model.SwiftRelay;
import model.UnmovableEntity;

/**
 * This class represents all the game data.
 */
public class Board {
	
	private boolean mapLoaded = false;

	private int winner = -1;
	
	/** The arsenals' coordinates. */
	private ArrayList<Coord> coord_arsenals;

	/** The matrix contains the entities. */
	public Entity matrix[][];
	
	/** The number of squares on the X axis. */
	public static int WIDTH = 25;
	
	/** The number of squares on the Y axis. */
	public static int HEIGHT = 20;
	
	/** This contains the squares that directly form the communication lines. */
	private HashMap<Integer,HashSet<Coord>> communications;
	

	/**
	 * Instantiates a new board.
	 */
	public Board() {
		matrix = new Entity[WIDTH][HEIGHT];
		
		coord_arsenals = new ArrayList<Coord>();
		
		communications = new HashMap<Integer,HashSet<Coord>>();
		communications.put(0, new HashSet<Coord>());
		communications.put(1, new HashSet<Coord>());
	}
	
	/**
	 * 	Tells whether the map is loaded
	 * @return true if the map is loaded
	 */
	public boolean isMapLoaded() {
		return mapLoaded;
	}

	public void setMapLoaded(boolean mapLoaded) {
		this.mapLoaded = mapLoaded;
	}
	
	public void setWinner(int team) {
		this.winner = team;
	}
	
	public int getWinner() {
		return winner;
	}

	/**
	 * Gets the number of instances of a class in the board
	 *
	 * @param c the class of the instance we are searching in the board
	 * @param team the team
	 * @return the number of instances of c
	 */
	public int getNbInstances(Class<? extends Entity> c, int team) {
		int n = 0;
		for(int y = 0; y < HEIGHT; y++){
			for(int x = 0; x < WIDTH; x++){
				
				MovableEntity e = getUnit(x,y);
				if (e != null && e.getClass().getSuperclass() == c && e.getOwner() == team)
					n++;
				else if (matrix[x][y] != null && matrix[x][y].getClass() == c && matrix[x][y].getOwner() == team)
					n++;
			}
		}
		
		return n;
	}
	
	/**
	 * Gets the number of connected fighters
	 *
	 * @param team the team
	 * @return the number of connected fighters
	 */
	public int getNbFighterConnected(int team) {
		
		Class<? extends Entity> c = Fighter.class;
		
		int n = 0;
		for(int y = 0; y < HEIGHT; y++){
			for(int x = 0; x < WIDTH; x++){
				
				MovableEntity e = getUnit(x,y);
				if (e != null && e.getClass().getSuperclass() == c && e.getOwner() == team)
					if(((Fighter)e).isConnected())
						n++;
			}
		}
		
		return n;
	}
	
	/**
	 * Gets the entity at (x,y)
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the entity at (x,y)
	 */
	public Entity getEntity(int x,int y) {
		return matrix[x][y];
	}
	
	/**
	 * Reset board.
	 * Clears the units, arsenals and communication lines.
	 */
	public void resetBoard() {
		matrix = new Entity[WIDTH][HEIGHT];
		coord_arsenals.clear();
		communications.get(0).clear();
		communications.get(1).clear();
		winner = -1;
		mapLoaded = false;
	}
	
	/**
	 * Save arsenal placement.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void saveArsenalPlacement(int x, int y) {
		coord_arsenals.add(new Coord(x,y));
	}
	

	/**
	 * Checks whether an Arsenal is under attack.
	 * 
	 * @param a the Arsenal to check
	 * @return true if a is under attack
	 */
	public boolean arsenalIsAttacked(Arsenal a) {
		if(!a.isEmpty() && a.getEntity().getOwner() != a.getOwner())
			if(!(a.getEntity() instanceof Relay) && !(a.getEntity() instanceof SwiftRelay))
				return true;
		return false;
	}
	
	/**
	 * Removes an Arsenal from both the matrix and the list
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param a the Arsenal
	 */
	public void destroyArsenal(int x, int y, Arsenal a) {
		matrix[x][y] = a.getEntity();
		coord_arsenals.remove(a.getCoord());
	}
	
	/**
	 * Place an entity.
	 *
	 * @param x the x
	 * @param y the y
	 * @param e the entity to place at (x,y)
	 */
	public void placeEntity(int x, int y, Entity e) {
		if (isValidDestination(x, y)){
			e.setCoord(new Coord(x, y));
			
			if(matrix[x][y]!=null && matrix[x][y].canContain()) {
				((UnmovableEntity)matrix[x][y]).setEntity((MovableEntity)e);
			}
			else {
				matrix[x][y] = e;
			}
		}
	}
	
	/**
	 * Gets the matrix.
	 *
	 * @return the matrix
	 */
	public Entity[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Sets the matrix.
	 *
	 * @param matrix the new matrix
	 */
	public void setMatrix(Entity[][] matrix) {
		this.matrix = matrix;
	}
	

	/**
	 * Gets the list of coordinates of Arsenals.
	 * 
	 * @return coord_arsenals
	 */
	public ArrayList<Coord> getCoord_arsenals() {
		return coord_arsenals;
	}
	
	/**
	 * Gets the communications.
	 *
	 * @param team the team
	 * @return the communications
	 */
	public HashSet<Coord> getCommunications(int team) {
		return communications.get(team);
	}
	
	/**
	 * Checks if an unit is on a communication line.
	 *
	 * @param coord the coordinates
	 * @param team the team
	 * @return true if the unit at (x,y) is on communications
	 */
	public boolean isOnCommunications(Coord coord, int team) {
		return getCommunications(team).contains(coord);
	}
	
	/**
	 * Gets the movable entities.
	 *
	 * @return all the movable entities on the board
	 */
	public List<MovableEntity> getMovableEntities() {
		List<MovableEntity> movableEntities = new ArrayList<MovableEntity>();
		
		for(int j=0; j<HEIGHT; j++) {
			for(int i=0; i<WIDTH; i++) {
				if (matrix[i][j] instanceof MovableEntity)
					movableEntities.add((MovableEntity)matrix[i][j]);
				else if (matrix[i][j] != null && matrix[i][j].canContain() && !((UnmovableEntity)matrix[i][j]).isEmpty())
					movableEntities.add(((UnmovableEntity)matrix[i][j]).getEntity());
			}
		}
		
		return movableEntities;
	}
	
	/**
	 * Checks if is valid destination.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is a valid destination
	 */
	public boolean isValidDestination(int x, int y) {
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
			return false;
		
		if (matrix[x][y] != null && (!matrix[x][y].canContain() || !((UnmovableEntity)matrix[x][y]).isEmpty()))
			return false;
		
		return true;
	}
	
	/**
	 * Checks if is movable entity.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is a movable entity
	 */
	public boolean isMovableEntity(int x, int y) {
		return matrix[x][y] instanceof MovableEntity;
	}
	
	/**
	 * Checks if is fighter.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is a fighter
	 */
	public boolean isFighter(int x, int y) {
		if(canContain(x,y))
			return getUnit(x,y) instanceof Fighter;
		return matrix[x][y] instanceof Fighter;
	}
	
	/**
	 * Checks if is mountain.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is a mountain
	 */
	public boolean isMountain(int x, int y) {
		return matrix[x][y] instanceof Mountain;
	}
	
	/**
	 * Checks if is arsenal.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is an arsenal
	 */
	public boolean isArsenal(int x, int y) {
		return matrix[x][y] instanceof Arsenal;
	}
	
	/**
	 * Checks if is mountain pass.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is a mountain pass
	 */
	public boolean isMountainPass(int x, int y) {
		return matrix[x][y] instanceof MountainPass;
	}
	
	/**
	 * Checks if is fortress.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is a fortress
	 */
	public boolean isFortress(int x, int y) {
		return matrix[x][y] instanceof Fortress;
	}
	
	/**
	 * Checks if is cavalry.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is a cavalry
	 */
	public boolean isCavalry(int x, int y) {
		return matrix[x][y] instanceof Cavalry;
	}
	
	/**
	 * Checks if is relay.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is a relay
	 */
	public boolean isRelay(int x, int y) {
		return (getUnit(x,y) instanceof Relay || getUnit(x,y) instanceof SwiftRelay);
	}
	
	/**
	 * Checks if is in board.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is in board
	 */
	public boolean inBoard(int x, int y) {
		return (x<WIDTH && y<HEIGHT && x>=0 && y>=0);
	}
	
	/**
	 * Checks if empty square.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) is an empty square
	 */
	public boolean emptySquare(int x, int y) {
		return matrix[x][y] == null;
	}
	
	/**
	 * Checks if an unit can contain another unit.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) can contain an unit
	 */
	public boolean canContain(int x, int y) {
		return matrix[x][y] instanceof UnmovableEntity && matrix[x][y].canContain();
	}
	
	/**
	 * Can contain but empty.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if (x,y) can contain an unit and is empty
	 */
	public boolean canContainButEmpty(int x, int y) {
		return canContain(x,y) && ((UnmovableEntity)matrix[x][y]).isEmpty();
	}
	
	/**
	 * Contains friendly unit.
	 *
	 * @param x the x
	 * @param y the y
	 * @param team the team
	 * @return true, if (x,y) contains a friendly unit
	 */
	public boolean containsFriendlyUnit(int x, int y, int team) {
		return ((UnmovableEntity)matrix[x][y]).getEntity().getOwner() == team;
	}
	
	/**
	 * Checks if is friendly unit.
	 *
	 * @param x the x
	 * @param y the y
	 * @param team the team
	 * @return true, if is friendly unit
	 */
	public boolean isFriendlyUnit(int x, int y, int team) {
		return (matrix[x][y] instanceof MovableEntity && matrix[x][y].getOwner() == team) || (canContain(x,y) && containsFriendlyUnit(x,y,team));
	}
	
	/**
	 * Gets the unit at (x,y) even if it is in a container
	 *
	 * @param x the x
	 * @param y the y
	 * @return the unit at (x,y)
	 */
	public MovableEntity getUnit(int x, int y) {
		if(canContain(x,y))
			return ((UnmovableEntity)matrix[x][y]).getEntity();
		
		if(matrix[x][y] instanceof UnmovableEntity)
			return null;
		
		return (MovableEntity)matrix[x][y];
	}
	
	/**
	 * Gets the neighbouring movable entities.
	 *
	 * @param x the x
	 * @param y the y
	 * @param team the team
	 * @return the list of all movable entities neighbouring (x,y)
	 */
	public ArrayList<Fighter> getNeighbours(int x, int y, int team) {

		
		ArrayList<Fighter> listNeighbours = new ArrayList<Fighter>();
		
		for(int i = -1 ; i <= 1 ; i++) {
			for(int j = -1 ; j <= 1 ; j++) {
				if(i != 0 || j != 0)
					if(inBoard(x+i,y+j) && getUnit(x+i,y+j) instanceof Fighter && isFriendlyUnit(x+i,y+j,team))
						listNeighbours.add((Fighter)getUnit(x+i,y+j));
			}	
		}
		return listNeighbours;
	}
	
	/**
	 * Checks for connected neighbour.
	 *
	 * @param f the fighter
	 * @return true, if f has a connected neighbour
	 */
	public boolean hasConnectedNeighbour(Fighter f) {
		
		ArrayList<Fighter> lf = getNeighbours(f.getCoord().x, f.getCoord().y, f.getOwner());
		
		for(Fighter fn : lf)
			if(fn.isConnected())
				return true;
		return false;
	}
	
	/**
	 * Computes arsenals communications.
	 */
	public void computeArsenalsCommunications() {

		int team;
		
		for(Coord c : coord_arsenals) {
			
			team = matrix[c.x][c.y].getOwner();
			computeCommunications(c.x, c.y, team);
		}
	}
	
	/**
	 * Checks if is obstacle.
	 *
	 * @param x the x
	 * @param y the y
	 * @param team the team
	 * @return true, if the entity at (x,y) is an obstacle
	 */
	public boolean isObstacle(int x, int y, int team) {
		if(!inBoard(x,y) || (!emptySquare(x,y) && (isMountain(x,y) || (!isRelay(x,y) && (!canContainButEmpty(x,y) && getUnit(x,y).getOwner() != team ))))) 
			return true;
		
		return false;
	}
	
	/**
	 * Compute communications for the given team.
	 *
	 * @param x the x
	 * @param y the y
	 * @param team the team
	 */
	public void computeCommunications(int x, int y, int team) {
				
		int i = 0;
		boolean north = true, south = true, east = true, west = true, northest = true, northwest = true, southeast = true, southwest = true;
				
		while(north || south || east || west || northest || northwest || southeast || southwest) {
			
			if(east && !isObstacle(x+i,y,team)) {
				communications.get(team).add(new Coord(x+i,y));
			} else {
				east = false;
			}
			
			if(south && !isObstacle(x,y+i,team)) {
				communications.get(team).add(new Coord(x,y+i));
			} else {
				south = false;
			}
			
			if(west && !isObstacle(x-i,y,team)) {
				communications.get(team).add(new Coord(x-i,y));
			} else {
				west = false;
			}
			
			if(north && !isObstacle(x,y-i,team)) {
				communications.get(team).add(new Coord(x,y-i));
			} else {
				north = false;
			}
			
			if(southeast && !isObstacle(x+i,y+i,team)) {
				communications.get(team).add(new Coord(x+i,y+i));
			} else {
				southeast = false;
			}
			
			if(northwest && !isObstacle(x-i,y-i,team)) {
				communications.get(team).add(new Coord(x-i,y-i));
			} else {
				northwest = false;
			}
			
			if(northest && !isObstacle(x+i,y-i,team)) {
				communications.get(team).add(new Coord(x+i,y-i));

			} else {
				northest = false;
			}
			
			if(southwest && !isObstacle(x-i,y+i,team)) {
				communications.get(team).add(new Coord(x-i,y+i));
			} else {
				southwest = false;
			}
			
			i++;
		}
	}		
}
