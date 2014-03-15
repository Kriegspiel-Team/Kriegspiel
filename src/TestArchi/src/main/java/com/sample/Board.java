package com.sample;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private Entity board[][];
	private Boolean isInit = false;
	
	public static int WIDTH = 25;
	public static int HEIGHT = 20;
	
	public Board(){
		board = new Entity[WIDTH][HEIGHT];
	}
	

	
	public void loadBoardWithFile(String filename){
		new EntityLoader(this, filename);
	}
	
	public void placeEntity(int x, int y, Entity e){
		if (isValidSquare(x, y)){
			e.setCoord(new Coord(x, y));
			
			if(board[x][y]!=null && board[x][y].canContain())
			{
				((UnmovableEntity)board[x][y]).setEntity((MovableEntity)e);
			}
			else
			{
				board[x][y] = e;
			}
		}
	}
	
	
	
	public Entity[][] getBoard(){
		return board;
	}
	
	public void setBoard(Entity[][] board){
		this.board = board;
	}

	public Boolean getIsInit() {
		return isInit;
	}

	public void setIsInit(Boolean isInit) {
		this.isInit = isInit;
	}
	
	public List<MovableEntity> getMovableEntity(){
		List<MovableEntity> movableEntity = new ArrayList<MovableEntity>();
		
		for(int j=0; j<HEIGHT; j++){
			for(int i=0; i<WIDTH; i++){
				if (board[i][j] instanceof MovableEntity)
					movableEntity.add((MovableEntity)board[i][j]);
				else if (board[i][j] != null && board[i][j].canContain() && !((UnmovableEntity)board[i][j]).isEmpty())
					movableEntity.add(((UnmovableEntity)board[i][j]).getEntity());
			}
		}
		
		return movableEntity;
	}
	
	public Boolean isValidSquare(int x, int y){
		if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT)
			return false;
		
		/*
		 * Authorize if there is a fortress, mountain pass...
		 */
		if (board[x][y] != null && !board[x][y].canContain()){
			return false;
		}
		
		return true;
	}
	
}
