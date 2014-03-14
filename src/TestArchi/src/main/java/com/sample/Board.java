package com.sample;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private Entity board[][];
	private Boolean isInit = false;
	
	public static int WIDTH = 25;
	public static int HEIGHT = 20;
	
	public Board(){
		board = new Entity[HEIGHT][WIDTH];
	}
	
	public Board(String filename){
		board = new Entity[HEIGHT][WIDTH];
		new EntityLoader(this, filename);
	}
	
	public void placeEntity(int i, int j, Entity e){
		board[i][j] = e;
	}
	
	public void display(int x, int y){
		
		List<Coord> possibleMovement = new ArrayList<Coord>();
		
		if (board[x][y] instanceof MovableEntity){
			possibleMovement = ((MovableEntity)board[x][y]).getPossibleMovement();
		}
		
		System.out.println(possibleMovement.size());

		System.out.println("____________________________________________________");
		for(int i=0; i<HEIGHT; i++){
			for(int j=0; j<WIDTH; j++){
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
	
	public List<Entity> getMovableEntity(){
		List<Entity> movableEntity = new ArrayList<Entity>();
		
		for(int i=0; i<HEIGHT; i++){
			for(int j=0; j<WIDTH; j++){
				if (board[i][j] instanceof MovableEntity)
					movableEntity.add(board[i][j]);
			}
		}
		
		return movableEntity;
	}
	
	public Boolean isValidSquare(int i, int j){
		if (j < 0 || j > WIDTH || i < 0 || i > HEIGHT)
			return false;
		
		/*
		 * Authorize if there is a fortress, mountain pass...
		 */
		if (board[i][j] != null){
			return false;
		}
			
		
		
		return true;
	}
	
	public void updatePossibleMoves()
	{
		for(int i=0; i<Board.HEIGHT; i++){
			for(int j=0; j<Board.WIDTH; j++){
			
				if (this.board[i][j] instanceof MovableEntity){

					MovableEntity entity = (MovableEntity)this.board[i][j];
					int speed = entity.getSpeed();
					entity.resetPossibleMovement();

					List<Coord> possibleMovements = new ArrayList<Coord>();

					possibleMovements.add(new Coord(i,j));
					for(int s = 0; s<speed; s++)
					{
						for(Coord c : possibleMovements){
							for(int k=-1; k<=1; k++){
								for(int l=-1; l<=1; l++){
								
									if (this.isValidSquare(c.x+k, c.y+l)){
										entity.addPossibleMovement(new Coord(c.x+k, c.y+l));
									}
								}
							}
						}
						
						possibleMovements = (ArrayList<Coord>)entity.getPossibleMovement().clone();
					}
				}
			}
		}
	}
	
}
