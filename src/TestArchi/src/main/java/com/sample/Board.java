package com.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
	private Entity matrix[][];
	private Boolean isInit = false;
	
	public static int WIDTH = 25;
	public static int HEIGHT = 20;
	
	private ArrayList<Coord> coord_arsenals;
	private HashMap<Integer,ArrayList<Coord>> communications;
	

	public Board(){
		matrix = new Entity[WIDTH][HEIGHT];
		coord_arsenals = new ArrayList<Coord>();
		
		communications = new HashMap<Integer,ArrayList<Coord>>();
		communications.put(0, new ArrayList<Coord>());
		communications.put(1, new ArrayList<Coord>());
	}
	
	public void loadBoardWithFile(String filename){
		new EntityLoader(this, filename);
	}
	
	public void saveArsenalPlacement(int x, int y)
	{
		coord_arsenals.add(new Coord(x,y));
		
	}
	
	public void placeEntity(int x, int y, Entity e){
		if (isValidSquare(x, y)){
			e.setCoord(new Coord(x, y));
			
			if(matrix[x][y]!=null && matrix[x][y].canContain())
			{
				((UnmovableEntity)matrix[x][y]).setEntity((MovableEntity)e);
			}
			else
			{
				matrix[x][y] = e;
			}
		}
	}
	
	
	
	public Entity[][] getMatrix(){
		return matrix;
	}
	
	public void setMatrix(Entity[][] board){
		this.matrix = board;
	}

	public Boolean getIsInit() {
		return isInit;
	}

	public void setIsInit(Boolean isInit) {
		this.isInit = isInit;
	}
	
	public ArrayList<Coord> getCommunications(int team) {
		return communications.get(team);
	}
	
	public List<MovableEntity> getMovableEntity(){
		List<MovableEntity> movableEntity = new ArrayList<MovableEntity>();
		
		for(int j=0; j<HEIGHT; j++){
			for(int i=0; i<WIDTH; i++){
				if (matrix[i][j] instanceof MovableEntity)
					movableEntity.add((MovableEntity)matrix[i][j]);
				else if (matrix[i][j] != null && matrix[i][j].canContain() && !((UnmovableEntity)matrix[i][j]).isEmpty())
					movableEntity.add(((UnmovableEntity)matrix[i][j]).getEntity());
			}
		}
		
		return movableEntity;
	}
	
	public Boolean isValidSquare(int x, int y){
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
			return false;
		
		if (matrix[x][y] != null && (!matrix[x][y].canContain() || !((UnmovableEntity)matrix[x][y]).isEmpty())){
			return false;
		}
		
		return true;
	}
	
	public ArrayList<MovableEntity> getNeighboursMovableEntity(int x, int y, int team) {
		
		ArrayList<MovableEntity> listNeighbours = new ArrayList<MovableEntity>();
		
		for(int i = -1 ; i <= 1 ; i++)
		{
			for(int j = -1 ; j <= 1 ; j++)
			{
				if(matrix[x+i][y+j]!=null && matrix[x+i][y+j] instanceof MovableEntity && matrix[x+i][y+j].getOwner() == team)
				{
					listNeighbours.add((MovableEntity)matrix[x+i][y+j]);
				}
				
			}	
		}
		
		return listNeighbours;
	}
	
	public void calculateArsenalsCommunications() {
		int team;
		
		for(Coord c : coord_arsenals) {
			
			team = matrix[c.x][c.y].getOwner();
			calculateCommunications(c.x, c.y, team);
		}
	}
	
	public void calculateCommunications(int x, int y, int team) {
			
		System.out.println(team);
		
		int i = 0;
		boolean north = true, south = true, east = true, west = true, northest = true, northwest = true, southeast = true, southwest = true;
				
		while(north || south || east || west || northest || northwest || southeast || southwest) {
			
			if(east && x+i<WIDTH && (matrix[x+i][y]==null || (matrix[x+i][y].canContain() && ((UnmovableEntity)matrix[x+i][y]).isEmpty()))) {
				communications.get(team).add(new Coord(x+i,y));
			} else {
				east = false;
			}
			
			if(south && y+i<HEIGHT && (matrix[x][y+i]==null || (matrix[x][y+i].canContain() && ((UnmovableEntity)matrix[x][y+i]).isEmpty()))) {
				communications.get(team).add(new Coord(x,y+i));
			} else {
				south = false;
			}
			
			if(west && x-i>=0 && (matrix[x-i][y]==null || (matrix[x-i][y].canContain() && ((UnmovableEntity)matrix[x-i][y]).isEmpty()))) {
				communications.get(team).add(new Coord(x-i,y));
			} else {
				west = false;
			}
			
			if(north && y-i>=0 && (matrix[x][y-i]==null || (matrix[x][y-i].canContain() && ((UnmovableEntity)matrix[x][y-i]).isEmpty()))) {
				communications.get(team).add(new Coord(x,y-i));
			} else {
				north = false;
			}
			
			if(southeast && (x+i<WIDTH && y+i<HEIGHT) && (matrix[x+i][y+i]==null || (matrix[x+i][y+i].canContain() && ((UnmovableEntity)matrix[x+i][y+i]).isEmpty()))) {
				communications.get(team).add(new Coord(x+i,y+i));
			} else {
				southeast = false;
			}
			
			if(northwest && (x-i>=0 && y-i>=0) && (matrix[x-i][y-i]==null || (matrix[x-i][y-i].canContain() && ((UnmovableEntity)matrix[x-i][y-i]).isEmpty()))) {
				communications.get(team).add(new Coord(x-i,y-i));
			} else {
				northwest = false;
			}
			
			if(northest && (x+i<WIDTH && y-i>=0) && (matrix[x+i][y-i]==null || (matrix[x+i][y-i].canContain() && ((UnmovableEntity)matrix[x+i][y-i]).isEmpty()))) {
				communications.get(team).add(new Coord(x+i,y-i));
			} else {
				northest = false;
			}
			
			if(southwest && (x-i>=0 && y+i<HEIGHT) && (matrix[x-i][y+i]==null || (matrix[x-i][y+i].canContain() && ((UnmovableEntity)matrix[x-i][y+i]).isEmpty()))) {
				communications.get(team).add(new Coord(x-i,y+i));
			} else {
				southwest = false;
			}
			
			i++;
		}
	}	
		
}
