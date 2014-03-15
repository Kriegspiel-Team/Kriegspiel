package com.sample;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] gs = ge.getScreenDevices();
			DisplayMode dm = gs[0].getDisplayMode();
			int windowheight = dm.getHeight()-50;
			int windowwidth = windowheight*5/4;
			JFrame window = new JFrame("Board display");
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setMinimumSize(new Dimension(windowwidth, windowheight));
			window.setResizable(false);
			Container content = window.getContentPane();
			content.setLayout(new GridLayout(HEIGHT, WIDTH, windowheight/500, windowheight/500));
			content.setBackground(new Color(0, 0, 0));
			JPanel squares[][] = new JPanel[WIDTH][HEIGHT];
			for(int j=0 ; j<HEIGHT ; j++)
			{
				for(int i=0 ; i<WIDTH ; i++)
				{
					squares[i][j] = new JPanel();
					//squares[i][j].setBackground(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
					if(board[i][j] == null)
						squares[i][j].setBackground(new Color(255, 255, 255));
					else
					{
						int owner = board[i][j].getOwner();
						JLabel tmp = new JLabel(""+board[i][j].getSymbol());
						tmp.setFont(new Font("Serif", Font.PLAIN, windowheight/40));
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
