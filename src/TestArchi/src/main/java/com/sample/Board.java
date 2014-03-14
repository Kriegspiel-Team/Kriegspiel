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
			board[x][y] = e;
		}
	}
	
	public void display(int x, int y){
		
		List<Coord> possibleMovement = new ArrayList<Coord>();
		
		if (board[x][y] instanceof MovableEntity){
			possibleMovement = ((MovableEntity)board[x][y]).getPossibleMovement();
		}
		
		System.out.println(possibleMovement.size());

		System.out.println("____________________________________________________");
		for(int j=0; j<HEIGHT; j++){
			for(int i=0; i<WIDTH; i++){
				System.out.print("|");
				
				Coord currentCoord = new Coord(i,j);
				Boolean isPossibleMovement = false;
				for(Coord c : possibleMovement){
					if (c.equals(currentCoord)){
						isPossibleMovement = true;
						break;
					}
				}
				
				
				if (isPossibleMovement)
					System.out.print("*");
				else{
				
					if (board[i][j] == null)
						System.out.print(" ");
					else
						System.out.print(board[i][j].getSymbol());
				}
			}
			System.out.println("|");
			System.out.println("____________________________________________________");
		}
		
		for(Coord c : possibleMovement){
			System.out.println(c.x + "," + c.y);
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
		if (board[x][y] != null){
			return false;
		}
			
		
		
		return true;
	}
	
}
