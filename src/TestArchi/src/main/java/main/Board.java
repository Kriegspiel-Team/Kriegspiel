package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.Cavalry;
import model.Entity;
import model.Fortress;
import model.Mountain;
import model.MountainPass;
import model.MovableEntity;
import model.Relay;
import model.SwiftRelay;
import model.UnmovableEntity;

public class Board {
	private Entity matrix[][];
	
	public static int WIDTH = 25;
	public static int HEIGHT = 20;
	
	private ArrayList<Coord> coord_arsenals;
	private HashMap<Integer,HashSet<Coord>> communications;
	

	public Board() {
		matrix = new Entity[WIDTH][HEIGHT];
		coord_arsenals = new ArrayList<Coord>();
		
		communications = new HashMap<Integer,HashSet<Coord>>();
		communications.put(0, new HashSet<Coord>());
		communications.put(1, new HashSet<Coord>());
	}
	
	public void loadBoardWithFile(String filename) {
		new EntityLoader(this, filename);
	}
	
	public void resetBoard(){
		matrix = new Entity[WIDTH][HEIGHT];
		coord_arsenals.clear();
		communications.get(0).clear();
		communications.get(1).clear();
	}
	
	public void saveArsenalPlacement(int x, int y) {
		coord_arsenals.add(new Coord(x,y));
		
	}
	
	public void placeEntity(int x, int y, Entity e) {
		if (isValidSquare(x, y)){
			e.setCoord(new Coord(x, y));
			
			if(matrix[x][y]!=null && matrix[x][y].canContain()) {
				System.out.println(e.getClass() + " : " + x + "," + y);
				((UnmovableEntity)matrix[x][y]).setEntity((MovableEntity)e);
			}
			else {
				matrix[x][y] = e;
			}
		}
	}
	
	public Entity[][] getMatrix() {
		return matrix;
	}
	
	public void setMatrix(Entity[][] board) {
		this.matrix = board;
	}
	
	public HashSet<Coord> getCommunications(int team) {
		return communications.get(team);
	}
	
	public boolean isOnCommunications(Coord coord, int team) {
		return getCommunications(team).contains(coord);
	}
	
	public List<MovableEntity> getMovableEntity() {
		List<MovableEntity> movableEntity = new ArrayList<MovableEntity>();
		
		for(int j=0; j<HEIGHT; j++) {
			for(int i=0; i<WIDTH; i++) {
				if (matrix[i][j] instanceof MovableEntity)
					movableEntity.add((MovableEntity)matrix[i][j]);
				else if (matrix[i][j] != null && matrix[i][j].canContain() && !((UnmovableEntity)matrix[i][j]).isEmpty())
					movableEntity.add(((UnmovableEntity)matrix[i][j]).getEntity());
			}
		}
		
		return movableEntity;
	}
	
	public boolean isValidSquare(int x, int y) {
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
			return false;
		
		if (matrix[x][y] != null && (!matrix[x][y].canContain() || !((UnmovableEntity)matrix[x][y]).isEmpty()))
			return false;
		
		return true;
	}
	
	public boolean isMovableEntity(int x, int y) {
		return matrix[x][y] instanceof MovableEntity;
	}
	
	public boolean isMountain(int x, int y) {
		return matrix[x][y] instanceof Mountain;
	}
	
	public boolean isMountainPass(int x, int y) {
		return matrix[x][y] instanceof MountainPass;
	}
	
	public boolean isFortress(int x, int y) {
		return matrix[x][y] instanceof Fortress;
	}
	
	public boolean isCavalry(int x, int y) {
		return matrix[x][y] instanceof Cavalry;
	}
	
	public boolean isRelay(int x, int y) {
		return (getUnit(x,y) instanceof Relay || getUnit(x,y) instanceof SwiftRelay);
	}
	
	public boolean inBoard(int x, int y) {
		return (x<WIDTH && y<HEIGHT && x>=0 && y>=0);
	}
	
	public boolean emptySquare(int x, int y)
	{
		return matrix[x][y] == null;
	}
	
	public boolean canContain(int x, int y) {
		return matrix[x][y] instanceof UnmovableEntity && matrix[x][y].canContain();
	}
	
	public boolean canContainButEmpty(int x, int y) {
		return canContain(x,y) && ((UnmovableEntity)matrix[x][y]).isEmpty();
	}
	
	public boolean containsFriendlyUnit(int x, int y, int team)
	{
		return ((UnmovableEntity)matrix[x][y]).getEntity().getOwner() == team;
	}
	
	public boolean isFriendlyUnit(int x, int y, int team)
	{
		return (matrix[x][y] instanceof MovableEntity && matrix[x][y].getOwner() == team) || (canContain(x,y) && containsFriendlyUnit(x,y,team));
	}
	
	public MovableEntity getUnit(int x, int y)
	{
		if(canContain(x,y))
			return ((UnmovableEntity)matrix[x][y]).getEntity();
		
		if(matrix[x][y] instanceof UnmovableEntity)
			return null;
		
		return (MovableEntity)matrix[x][y];
	}
	
	public ArrayList<MovableEntity> getNeighboursMovableEntity(int x, int y, int team) {
		
		ArrayList<MovableEntity> listNeighbours = new ArrayList<MovableEntity>();
		
		for(int i = -1 ; i <= 1 ; i++) {
			for(int j = -1 ; j <= 1 ; j++) {
				if(inBoard(x+i,y+j) && !emptySquare(x+i,y+j) && isFriendlyUnit(x+i,y+j,team)) {
					listNeighbours.add(getUnit(x+i,y+j));
				}
			}	
		}
		
		return listNeighbours;
	}
	
	public void calculateArsenalsCommunications() {
		int team;
		
		for(Coord c : coord_arsenals) {
			
			team = matrix[c.x][c.y].getOwner();
			computeCommunications(c.x, c.y, team);
		}
	}
	
	public boolean isObstacle(int x, int y, int team) {
		if(!inBoard(x,y) || (!emptySquare(x,y) && (isMountain(x,y) || (!isRelay(x,y) && (!canContainButEmpty(x,y) && getUnit(x,y).getOwner() != team ))))) 
			return true;
		
		return false;
	}
	
	public void computeCommunications(int x, int y, int team) {
		
		System.out.println(team);
		
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
