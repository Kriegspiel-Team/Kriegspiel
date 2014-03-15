package com.sample;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board {
	private Entity board[][];
	private Boolean isInit = false;
	
	public static int WIDTH = 25;
	public static int HEIGHT = 20;
	public static int SQUARESIZE = 50;
	
	private ArrayList<Coord> coord_arsenals;
	private ArrayList<Coord> communications[];
	

	@SuppressWarnings("unchecked")
	public Board(){
		board = new Entity[WIDTH][HEIGHT];
		coord_arsenals = new ArrayList<Coord>();
		
		communications = new ArrayList[2];
		ArrayList<Coord> array0 = new ArrayList<Coord>();
		ArrayList<Coord> array1 = new ArrayList<Coord>();
		communications[0] = array0;
		communications[1] = array1;
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
	
	public void display(int x, int y, boolean graphical){
		
		List<Coord> possibleMoves = new ArrayList<Coord>();
		if (board[x][y] instanceof MovableEntity || (board[x][y] != null && board[x][y].canContain() && !((UnmovableEntity)board[x][y]).isEmpty())){
			if (board[x][y].canContain()){
				possibleMoves = ((UnmovableEntity)board[x][y]).getEntity().getPossibleMovement();
			}else{
				possibleMoves = ((MovableEntity)board[x][y]).getPossibleMovement();
			}
		}
		
		if(graphical)
		{
			//Random r = new Random();
			JFrame window = new JFrame("Board display");
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setSize(SQUARESIZE*WIDTH, SQUARESIZE*HEIGHT);
			Container content = window.getContentPane();
			content.setLayout(new GridLayout(HEIGHT, WIDTH, 3, 3));
			content.setBackground(new Color(0, 0, 0));
			JPanel squares[][] = new JPanel[WIDTH][HEIGHT];
			for(int j=0 ; j<HEIGHT ; j++)
			{
				for(int i=0 ; i<WIDTH ; i++)
				{
					squares[i][j] = new JPanel();
					squares[i][j].setSize(SQUARESIZE, SQUARESIZE);
					//squares[i][j].setBackground(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
					if(board[i][j] == null)
						squares[i][j].setBackground(new Color(255, 255, 255));
					else
					{
						int owner = board[i][j].getOwner();
						JLabel tmp = new JLabel(""+board[i][j].getSymbol());
						tmp.setFont(new Font("Serif", Font.PLAIN, 32));
						squares[i][j].add(tmp);
						if(board[i][j] instanceof Mountain)
							squares[i][j].setBackground(new Color(200,200,200));
						
						if(board[i][j].canContain() && !((UnmovableEntity)board[i][j]).isEmpty())
							owner = ((UnmovableEntity)board[i][j]).getEntity().getOwner();
						
						switch(owner) {
							case 1:
								squares[i][j].setBackground(new Color(100,150,255));
								break;
							case 2:
								squares[i][j].setBackground(new Color(255,100,100));
								break;
						}
					}
					content.add(squares[i][j]);
				}
			}
			for(Coord c : possibleMoves)
			{
				squares[c.x][c.y].setBackground(new Color(50,255,50));
			}
			
			window.setVisible(true);
		}
		else
		{
			System.out.println(" -----------------------------------------------------------------------------------------------------");
			for(int i=0; i<HEIGHT; i++){
				for(int j=0; j<WIDTH; j++){
					System.out.print(" | ");
					
					Coord currentCoord = new Coord(i,j);
					Boolean isPossibleMovement = false;
					for(Coord c : possibleMoves){
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
				System.out.println(" | ");
				System.out.println(" -----------------------------------------------------------------------------------------------------");
			}
		}
		
		for(Coord c : possibleMoves){
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
	
	public ArrayList<Coord>[] getCommunications() {
		return communications;
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
	
	public ArrayList<MovableEntity> getNeighboursMovableEntity(int x, int y, int team) {
		
		ArrayList<MovableEntity> listNeighbours = new ArrayList<MovableEntity>();
		
		for(int i = -1 ; i <= 1 ; i++)
		{
			for(int j = -1 ; j <= 1 ; j++)
			{
				if(board[x+i][y+j]!=null && board[x+i][y+j] instanceof MovableEntity && board[x+i][y+j].getOwner() == team)
				{
					listNeighbours.add((MovableEntity)board[x+i][y+j]);
				}
				
			}	
		}
		
		return listNeighbours;
	}
	
	public void calculateArsenalsCommunications() {
		int team;
		
		for(Coord c : coord_arsenals) {
			
			team = board[c.x][c.y].getOwner();
			calculateCommunications(c.x, c.y, team);
		}
	}
	
	public void calculateCommunications(int x, int y, int team) {
			
		int i = 0;
		boolean north = true, south = true, east = true, west = true, northest = true, northwest = true, southeast = true, southwest = true;
				
		while(north || south || east || west || northest || northwest || southeast || southwest) {
			
			if(east && x+i<WIDTH && (board[x+i][y]==null || (board[x+i][y].canContain() && ((UnmovableEntity)board[x+i][y]).isEmpty()))) {
				communications[team].add(new Coord(x+i,y));
			} else {
				east = false;
			}
			
			if(south && y+i<HEIGHT && (board[x][y+i]==null || (board[x][y+i].canContain() && ((UnmovableEntity)board[x][y+i]).isEmpty()))) {
				communications[team].add(new Coord(x,y+i));
			} else {
				south = false;
			}
			
			if(west && x-i>=0 && (board[x-i][y]==null || (board[x-i][y].canContain() && ((UnmovableEntity)board[x-i][y]).isEmpty()))) {
				communications[team].add(new Coord(x-i,y));
			} else {
				west = false;
			}
			
			if(north && y-i>=0 && (board[x][y-i]==null || (board[x][y-i].canContain() && ((UnmovableEntity)board[x][y-i]).isEmpty()))) {
				communications[team].add(new Coord(x,y-i));
			} else {
				north = false;
			}
			
			if(southeast && (x+i<WIDTH && y+i<HEIGHT) && (board[x+i][y+i]==null || (board[x+i][y+i].canContain() && ((UnmovableEntity)board[x+i][y+i]).isEmpty()))) {
				communications[team].add(new Coord(x+i,y+i));
			} else {
				southeast = false;
			}
			
			if(northwest && (x-i>=0 && y-i>=0) && (board[x-i][y-i]==null || (board[x-i][y-i].canContain() && ((UnmovableEntity)board[x-i][y-i]).isEmpty()))) {
				communications[team].add(new Coord(x-i,y-i));
			} else {
				northwest = false;
			}
			
			if(northest && (x+i<WIDTH && y-i>=0) && (board[x+i][y-i]==null || (board[x+i][y-i].canContain() && ((UnmovableEntity)board[x+i][y-i]).isEmpty()))) {
				communications[team].add(new Coord(x+i,y-i));
			} else {
				northest = false;
			}
			
			if(southwest && (x-i>=0 && y+i<HEIGHT) && (board[x-i][y+i]==null || (board[x-i][y+i].canContain() && ((UnmovableEntity)board[x-i][y+i]).isEmpty()))) {
				communications[team].add(new Coord(x-i,y+i));
			} else {
				southwest = false;
			}
			
			i++;
		}
	}	
		
}