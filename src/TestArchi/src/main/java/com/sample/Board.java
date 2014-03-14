package com.sample;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board {
	private Entity board[][];
	private Boolean isInit = false;
	
	public static int WIDTH = 25;
	public static int HEIGHT = 20;
	public static int SQUARESIZE = 50;
	
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
	
	public void display(int x, int y, boolean graphical){
		
		List<Coord> possibleMoves = new ArrayList<Coord>();
		
		if (board[x][y] instanceof MovableEntity){
			possibleMoves = ((MovableEntity)board[x][y]).getPossibleMovement();
		}
		
		System.out.println(possibleMoves.size());
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
					if(board[j][i] == null)
						squares[i][j].setBackground(new Color(255, 255, 255));
					else
					{
						JLabel tmp = new JLabel(""+board[j][i].getSymbol());
						tmp.setFont(new Font("Serif", Font.PLAIN, 32));
						squares[i][j].add(tmp);
						if(board[j][i] instanceof Mountain)
							squares[i][j].setBackground(new Color(200,200,200));
					}
					content.add(squares[i][j]);
				}
			}
			for(Coord c : possibleMoves)
			{
				squares[c.y][c.x].setBackground(new Color(50,255,50));
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
	
}
