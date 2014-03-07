package com.sample;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private Entity board[][];
	private Boolean isInit = false;
	
	
	public Board(){
		board = new Entity[20][25];
	}
	
	public void placeEntity(int i, int j, Entity e){
		board[i][j] = e;
	}
	
	public void display(int x, int y){
		
		List<Coord> possibleMovement = ((MovableEntity)board[x][y]).getPossibleMovement();
		System.out.println(possibleMovement.size());

		System.out.println("____________________________________________________");
		for(int i=0; i<20; i++){
			for(int j=0; j<25; j++){
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
	
	public List<Entity> getMovableEntity(){
		List<Entity> movableEntity = new ArrayList<Entity>();
		
		for(int i=0; i<20; i++){
			for(int j=0; j<25; j++){
				if (board[i][j] instanceof MovableEntity)
					movableEntity.add(board[i][j]);
			}
		}
		
		return movableEntity;
	}
	
	public Boolean isValidSquare(int i, int j){
		if (j < 0 || j > 25 || i < 0 || i > 20)
			return false;
		
		/*
		 * Authorize if there is a fortress, mountain pass...
		 */
		if (board[i][j] != null){
			System.out.println("NOT NULL");
			return false;
		}
			
		
		
		return true;
	}
	
}
