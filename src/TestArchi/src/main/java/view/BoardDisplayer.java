package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sample.Board;
import com.sample.Coord;
import com.sample.Entity;
import com.sample.Mountain;
import com.sample.MovableEntity;
import com.sample.UnmovableEntity;

@SuppressWarnings("serial")
public class BoardDisplayer extends JFrame{
	private Board board;
	private Entity[][] matrix;
	private int windowHeight;
	private int windowWidth;
	private JPanel squares[][];
	private Coord selectedSquare = null;
	
	public static Color COLOR_PLAYER1 = new Color(100,150,255);
	public static Color COLOR_PLAYER2 = new Color(255,100,100);
	public static Color COLOR_MOUTAIN = new Color(200,200,200);
	public static Color COLOR_POSSIBLEMOVE = new Color(50,255,50);
	public static Color COLOR_EMPTY = new Color(255,255,255);
	
	public BoardDisplayer(Board board)
	{
		this.board = board;
		this.matrix = this.board.getBoard();
		initGUI();
	}
	
	public void initGUI()
	{
		//Random r = new Random();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		DisplayMode dm = gs[0].getDisplayMode();
		windowHeight = dm.getHeight()-50;
		windowWidth = windowHeight*Board.WIDTH/Board.HEIGHT;
		this.setTitle("Kriegspiel Board Display");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(windowWidth, windowHeight));
		this.setResizable(false);
		this.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1)
					drawPossibleMovement(e.getX()*Board.WIDTH/windowWidth, e.getY()*Board.HEIGHT/windowHeight);
				if(e.getButton() == MouseEvent.BUTTON3)
					clearPossibleMovement();
			}
		});
		Container content = this.getContentPane();
		content.setLayout(new GridLayout(Board.HEIGHT, Board.WIDTH, windowHeight/500, windowHeight/500));
		content.setBackground(new Color(0, 0, 0));
		squares = new JPanel[Board.WIDTH][Board.HEIGHT];
		for(int j=0 ; j<Board.HEIGHT ; j++)
		{
			for(int i=0 ; i<Board.WIDTH ; i++)
			{
				squares[i][j] = new JPanel();
				content.add(squares[i][j]);
			}
		}
	}
		
	private void clearPossibleMovement(){
		if (selectedSquare == null)
			return;
		
		for(int j=0 ; j<Board.HEIGHT ; j++){
			for(int i=0 ; i<Board.WIDTH ; i++){
				JPanel currentSquare = squares[i][j];
				Entity currentEntity = matrix[i][j];
				
				if(currentEntity == null){
					currentSquare.removeAll();
					currentSquare.setBackground(COLOR_EMPTY);
				}
			}
		}
		
		colorSquareByOwner(selectedSquare.x, selectedSquare.y);
		
		repaint();
		
		selectedSquare = null;
	}
	
	private void colorSquareByOwner(int x, int y){
		JPanel currentSquare = squares[x][y];
		Entity currentEntity = matrix[x][y];
		int owner = currentEntity.getOwner();
		
		if(currentEntity.canContain() && !((UnmovableEntity)currentEntity).isEmpty())
			owner = ((UnmovableEntity)currentEntity).getEntity().getOwner();
		
		switch(owner) {
			case 0:
				currentSquare.setBackground(COLOR_PLAYER1);
				break;
			case 1:
				currentSquare.setBackground(COLOR_PLAYER2);
				break;
		}
	}
	
	private void drawPossibleMovement(int x, int y){		
		if(x >= 0 && y >= 0 && x < Board.WIDTH && y < Board.HEIGHT){
			clearPossibleMovement();
			
			List<Coord> possibleMoves = getPossibleMoves(x, y);
			for(Coord c : possibleMoves){
				squares[c.x][c.y].setBackground(COLOR_POSSIBLEMOVE);
			}
			
			if (possibleMoves.size() > 0)
				selectedSquare = new Coord(x, y);
			
			repaint();
		}
	}
	
	public void displayGUI(int x, int y){
		
		for(int j=0 ; j<Board.HEIGHT ; j++)
		{
			for(int i=0 ; i<Board.WIDTH ; i++)
			{
				JPanel currentSquare = squares[i][j];
				Entity currentEntity = matrix[i][j];
				//squares[i][j].setBackground(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
				currentSquare.removeAll();
				if(currentEntity == null)
					currentSquare.setBackground(COLOR_EMPTY);
				else
				{
					JLabel tmp = new JLabel(Character.toString(currentEntity.getSymbol()));
					tmp.setFont(new Font("Serif", Font.PLAIN, windowHeight/40));
					currentSquare.add(tmp);
					if(currentEntity instanceof Mountain)
						currentSquare.setBackground(COLOR_MOUTAIN);
					
					colorSquareByOwner(i, j);
				}
			}
		}
		
		this.setVisible(true);
	}
	
	/*public void displayASCII(int x, int y){
		List<Coord> possibleMoves = getPossibleMoves(x, y);
		System.out.println(" -----------------------------------------------------------------------------------------------------");
		for(int i=0; i<Board.HEIGHT; i++){
			for(int j=0; j<Board.WIDTH; j++){
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
				
					if (matrix[i][j] == null)
						System.out.print(" ");
					else
						System.out.print(matrix[i][j].getSymbol());
				}
			}
			System.out.println(" | ");
			System.out.println(" -----------------------------------------------------------------------------------------------------");
		}
	}*/

	private List<Coord> getPossibleMoves(int x, int y){
		List<Coord> possibleMoves = new ArrayList<Coord>();
		if (matrix[x][y] instanceof MovableEntity || 
				(matrix[x][y] != null && matrix[x][y].canContain() && !((UnmovableEntity)matrix[x][y]).isEmpty())){
			
			if (matrix[x][y].canContain()){
				possibleMoves = ((UnmovableEntity)matrix[x][y]).getEntity().getPossibleMovement();
			}else{
				possibleMoves = ((MovableEntity)matrix[x][y]).getPossibleMovement();
			}
		}
		return possibleMoves;
	}	
}
