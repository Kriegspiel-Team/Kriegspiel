package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Board;
import main.Coord;
import model.Arsenal;
import model.Entity;
import model.Fortress;
import model.Mountain;
import model.MovableEntity;

@SuppressWarnings("serial")
public class BoardDisplayer extends JFrame {
	private Board board;
	private Entity[][] matrix;
	private int windowHeight;
	private int windowWidth;
	private JPanel squares[][];
	private Coord selectedSquare = null;
	
	private boolean p0Coms = true;
	private boolean p1Coms = true;
	
	public static final int DISPLAY_UNITS = 0;
	public static final int DISPLAY_ATTACK = 1;
	public static final int DISPLAY_DEFENCE = 2;
	
	private int displayMode = DISPLAY_UNITS;
	
	private static final Color COLOR_PLAYER0 = new Color(100,150,255);
	private static final Color COLOR_COM_PLAYER0 = new Color(50,100,200);
	private static final Color COLOR_PLAYER1 = new Color(255,100,100);
	private static final Color COLOR_COM_PLAYER1 = new Color(200,50,50);
//	private static final Color COLOR_COM_INTERSECT = new Color(200,50,200);
	private static final Color COLOR_MOUTAIN = new Color(200,200,200);
	private static final Color COLOR_POSSIBLEMOVE = new Color(50,255,50);
	private static final Color COLOR_EMPTY = new Color(255,255,255);
	
	public BoardDisplayer(Board board)
	{
		this.board = board;
		this.matrix = this.board.getMatrix();
		initGUI();			
	}
	
	public void setP0Coms(boolean b){
		p0Coms = b;
	}
	
	public void setP1Coms(boolean b){
		p1Coms = b;
	}
	
	public void switchPOComs(){
		p0Coms = !p0Coms;
	}
	
	public void switchP1Coms(){
		p1Coms = !p1Coms;
	}
	
	public void setDisplayMode(int mode){
		displayMode = mode;
	}
	
	public void initGUI()
	{
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		DisplayMode dm = gs[0].getDisplayMode();
		windowHeight = dm.getHeight()-50;
		windowWidth = windowHeight*Board.WIDTH/Board.HEIGHT;
		this.setTitle("Kriegspiel Board Display");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(windowWidth, windowHeight));
		this.setResizable(false);

		Container content = this.getContentPane();		
		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(Board.HEIGHT, Board.WIDTH, windowHeight/500, windowHeight/500));
		JPanel menuPanel = new MenuDisplayer(this);
		content.setLayout(new BorderLayout());
		
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(menuPanel, BorderLayout.EAST);
		
		boardPanel.setBackground(new Color(0, 0, 0));			
		
		squares = new JPanel[Board.WIDTH][Board.HEIGHT];
		for(int j=0 ; j<Board.HEIGHT ; j++)
		{
			for(int i=0 ; i<Board.WIDTH ; i++)
			{
				squares[i][j] = new JPanel();
				squares[i][j].setLayout(new FlowLayout(FlowLayout.CENTER));
				
				if (matrix[i][j] instanceof MovableEntity)
					squares[i][j].addMouseListener(new CellMouseListener(i, j));
				
				if (!board.emptySquare(i, j) && board.canContain(i, j)){
					squares[i][j].setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f, 1.0f, false));
				}
				
				boardPanel.add(squares[i][j]);
			}
		}
	}
		
	private void clearPossibleMovement(){
		if (selectedSquare == null)
			return;

		Set<Coord> moves = getPossibleMoves(selectedSquare.x, selectedSquare.y);
		for (Coord c : moves)
				squares[c.x][c.y].setBackground(COLOR_EMPTY);
		
		colorSquareByOwner(selectedSquare.x, selectedSquare.y);
		drawCommunications();
		
		repaint();
		
		selectedSquare = null;
	}
	
	private void colorSquareByOwner(int x, int y){
		JPanel currentSquare = squares[x][y];
		Entity currentEntity = matrix[x][y];
		
		if(currentEntity instanceof Fortress && board.getUnit(x, y) != null)
			currentEntity = board.getUnit(x, y);
		
		int owner = currentEntity.getOwner();
		
		switch(owner) {
			case 0:
				if(currentEntity.isConnected() || currentEntity instanceof Arsenal)
					currentSquare.setBackground(COLOR_COM_PLAYER0);
				else
					currentSquare.setBackground(COLOR_PLAYER0);
				break;
			case 1:
				if(currentEntity.isConnected() || currentEntity instanceof Arsenal)
					currentSquare.setBackground(COLOR_COM_PLAYER1);
				else
					currentSquare.setBackground(COLOR_PLAYER1);
				break;
		}
	}
	
	private void drawPossibleMovement(int x, int y){		
		if(x >= 0 && y >= 0 && x < Board.WIDTH && y < Board.HEIGHT){
			clearPossibleMovement();
			
			Set<Coord> possibleMoves = getPossibleMoves(x, y);
			for(Coord c : possibleMoves){
				squares[c.x][c.y].setBackground(COLOR_POSSIBLEMOVE);
			}
			
			if (possibleMoves.size() > 0)
				selectedSquare = new Coord(x, y);
			
			repaint();
		}
	}
	
	public void displayGUI(){		
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
					switch(displayMode)
					{
						case DISPLAY_UNITS: 
							displayUnit(i, j);
							break;
						case DISPLAY_ATTACK:
							displayAttackPotential(i, j);
							break;
						case DISPLAY_DEFENCE:
							displayDefencePotential(i, j);
							break;
					}
					if(currentEntity instanceof Mountain)
						currentSquare.setBackground(COLOR_MOUTAIN);				
					colorSquareByOwner(i, j);
				}
				
			}
		}
				
		if (selectedSquare != null)
			drawPossibleMovement(selectedSquare.x, selectedSquare.y);
		else
			drawCommunications();
		
		
		
		this.repaint();
		this.setVisible(true);
	}
	
	private void drawCommunications()
	{
		/*List<Coord> com = board.getCommunications(0);
		for (Coord c : com)
			if(matrix[c.x][c.y] == null)
				squares[c.x][c.y].setBackground(COLOR_COM_PLAYER0);
		com = board.getCommunications(1);
		for (Coord c : com)
			if(matrix[c.x][c.y] == null)
			{
				if(squares[c.x][c.y].getBackground()==COLOR_COM_PLAYER0)
					squares[c.x][c.y].setBackground(COLOR_COM_INTERSECT);
				else
					squares[c.x][c.y].setBackground(COLOR_COM_PLAYER1);
			}*/
		
		Font fnt = new Font("Serif", Font.PLAIN, windowHeight/60);
		Set<Coord> com;
		if(p0Coms)
		{
			com = board.getCommunications(0);
						
			for (Coord c : com)
				if(matrix[c.x][c.y] == null)
				{
					JLabel tmp = new JLabel("o", JLabel.CENTER);
					tmp.setFont(fnt);
					tmp.setForeground(COLOR_COM_PLAYER0);
					squares[c.x][c.y].add(tmp);
				}
		}
		if(p1Coms)
		{
			com = board.getCommunications(1);
			for (Coord c : com)
			{
				if(matrix[c.x][c.y] == null)
				{
					JLabel tmp = new JLabel("o", JLabel.CENTER);
					tmp.setFont(fnt);
					tmp.setForeground(COLOR_COM_PLAYER1);
					squares[c.x][c.y].add(tmp);
				}
			}
		}
	}
	
	public void displayAttackPotential(int x, int y)
	{
		if(matrix[x][y] != null && !(matrix[x][y] instanceof Mountain))
		{
			MovableEntity unit = board.getUnit(x, y);
			if(unit != null)
			{
				Font fnt = new Font("Serif", Font.PLAIN, windowHeight/50);
				JLabel tmp = new JLabel(Integer.toString(unit.getAttack()), JLabel.RIGHT);
				tmp.setFont(fnt);
				squares[x][y].add(tmp);
			}
		}
	}
	
	public void displayDefencePotential(int x, int y)
	{
		if(matrix[x][y] != null && !(matrix[x][y] instanceof Mountain))
		{
			MovableEntity unit = board.getUnit(x, y);
			if(unit != null)
			{
				Font fnt = new Font("Serif", Font.PLAIN, windowHeight/50);
				JLabel tmp = new JLabel(Integer.toString(unit.getDefence()), JLabel.RIGHT);
				tmp.setFont(fnt);
				squares[x][y].add(tmp);
			}
		}
	}
	
	private void displayUnit(int x, int y)
	{
		JLabel tmp = new JLabel(Character.toString(matrix[x][y].getSymbol()));
		tmp.setFont(new Font("Serif", Font.PLAIN, windowHeight/40));
		squares[x][y].add(tmp);
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

	private Set<Coord> getPossibleMoves(int x, int y){
		if(board.getUnit(x, y) instanceof MovableEntity)
			return board.getUnit(x,y).getPossibleMovement();
		return new HashSet<Coord>();
	}

	private class CellMouseListener implements MouseListener{
	
		private int x;
		private int y;
		
		public CellMouseListener(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1)
				drawPossibleMovement(x, y);
			if(e.getButton() == MouseEvent.BUTTON3)
				clearPossibleMovement();
		}
	
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}	
	}
}
