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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import evaluator.Potentials;
import main.Board;
import main.BoardController;
import main.Coord;
import model.Entity;
import model.Mountain;
import model.MovableEntity;
import model.Fighter;

/**
 * The BoardDisplayer.
 */
@SuppressWarnings("serial")
public class BoardDisplayer extends JFrame {
	
	/** The board. */
	private Board board;
	
	/** The potential. */
	private Potentials potential;
	
	/** The controller. */
	private BoardController controller;
	
	/** The matrix. */
	private Entity[][] matrix;
	
	/** The window height. */
	private int windowHeight;
	
	/** The window width. */
	private int windowWidth;
	
	/** The squares. */
	private JPanel squares[][];
	
	/** The selected square. */
	private Coord selectedSquare = null;
	
	/** The board panel. */
	private JPanel boardPanel;
	
	/** The player0 com lines state. */
	private boolean p0Coms = true;
	
	/** The player1 com lines state. */
	private boolean p1Coms = true;
	
	/** The Display mode DISPLAY_UNITS. */
	public static final int DISPLAY_UNITS = 0;
	
	/** The Display mode DISPLAY_ATTACK. */
	public static final int DISPLAY_ATTACK = 1;
	
	/** The Display mode DISPLAY_DEFENCE. */
	public static final int DISPLAY_DEFENCE = 2;
	
	/** The Display mode DISPLAY_DEFENCE_MINUS_ATTACK. */
	public static final int DISPLAY_DEFENCE_MINUS_ATTACK = 3;
	
	/** The Display mode DISPLAY_PREVAILING0. */
	public static final int DISPLAY_ATTACK_EVAL_TEAM0 = 4;
	
	/** The Display mode DISPLAY_PREVAILING1. */
	public static final int DISPLAY_ATTACK_EVAL_TEAM1 = 5;
	
	public static final int DISPLAY_DEFENCE_EVAL_TEAM0 = 6;
	
	public static final int DISPLAY_DEFENCE_MINUS_ATTACK_EVAL_TEAM0 = 7;
	
	public static final int DISPLAY_DEFENCE_EVAL_TEAM1 = 8;
	
	public static final int DISPLAY_DEFENCE_MINUS_ATTACK_EVAL_TEAM1 = 9;

	
	public static final int MENU_WIDTH = 150;
	
	/** The current display mode. */
	private int displayMode = DISPLAY_UNITS;
	
	/** Player0's units' color while disconnected. */
	private static final Color COLOR_PLAYER0 = new Color(100,150,255);
	
	/** Player0's units' color while connected to the com network. */
	private static final Color COLOR_COM_PLAYER0 = new Color(50,100,200);
	
	/** Player1's units' color while disconnected. */
	private static final Color COLOR_PLAYER1 = new Color(255,100,100);
	
	/** Player1's units' color while connected to the com network. */
	private static final Color COLOR_COM_PLAYER1 = new Color(200,50,50);
//	private static final Color COLOR_COM_INTERSECT = new Color(200,50,200);
	/** The mountains' color */
	private static final Color COLOR_MOUTAIN = new Color(200,200,200);
	
	/** The color to use to display possible moves. */
	private static final Color COLOR_POSSIBLEMOVE = new Color(50,255,50);
	
	/** The color of empty squares. */
	private static final Color COLOR_EMPTY = new Color(255,255,255);
		
	/**
	 * Instantiates a new board displayer.
	 *
	 * @param controller the controller
	 */
	public BoardDisplayer(BoardController controller) {
		this.controller = controller;
		this.board = controller.getBoard();
		this.potential = controller.getPotentials();
		this.matrix = this.board.getMatrix();
		initGUI();			
	}
	
	/**
	 * Sets whether to display player0's com lines.
	 *
	 * @param b whether to display player0's com lines
	 */
	public void setP0Coms(boolean b){
		p0Coms = b;
	}
	
	/**
	 * Sets whether to display player1's com lines.
	 *
	 * @param b whether to display player1's com lines
	 */
	public void setP1Coms(boolean b){
		p1Coms = b;
	}
	
	/**
	 * Gets whether to display player0's com lines.
	 *
	 * @return whether to display player0's com lines
	 */
	public boolean getP0Coms(){
		return p0Coms;
	}
	
	/**
	 * Gets whether to display player1's com lines.
	 *
	 * @return whether to display player1's com lines
	 */
	public boolean getP1Coms(){
		return p1Coms;
	}
	
	/**
	 * Switch player0 com lines display state.
	 */
	public void switchPOComs(){
		p0Coms = !p0Coms;
	}
	
	/**
	 * Switch player1 com lines display state.
	 */
	public void switchP1Coms(){
		p1Coms = !p1Coms;
	}
	
	/**
	 * Sets the display mode.
	 *
	 * @param mode the new display mode
	 */
	public void setDisplayMode(int mode){
		displayMode = mode;
	}
			
	/**
	 * Draws the entities.
	 */
	public void drawEntities(){
		this.matrix = this.board.getMatrix();
					
		boardPanel.removeAll();
		for(int j=0 ; j<Board.HEIGHT ; j++)
		{
			for(int i=0 ; i<Board.WIDTH ; i++)
			{
				squares[i][j] = new JPanel();
				squares[i][j].setLayout(new FlowLayout(FlowLayout.CENTER));
				
				squares[i][j].removeAll();
								
				if (board.isMovableEntity(i,j) || board.isFortress(i,j) || board.isArsenal(i,j) || board.isMountainPass(i,j))
					squares[i][j].addMouseListener(new CellMouseListener(i, j));
				
				if (!board.emptySquare(i, j) && (board.isFortress(i,j) || board.isArsenal(i,j))){
					squares[i][j].setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f, 1.0f, false));
				}
				
				boardPanel.add(squares[i][j]);
			}
		}
	}
	
	/**
	 * Initializes the GUI.
	 */
	public void initGUI() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		DisplayMode dm = gs[0].getDisplayMode();
		windowHeight = dm.getHeight()-50;
		windowWidth = ((windowHeight*Board.WIDTH)/Board.HEIGHT)+MENU_WIDTH;
		this.setTitle("Kriegspiel Board Display");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(windowWidth, windowHeight));
		this.setResizable(false);

		Container content = this.getContentPane();		
		
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(Board.HEIGHT, Board.WIDTH, windowHeight/500, windowHeight/500));
		JPanel menuPanel = new MenuDisplayer(this, controller);
		content.setLayout(new BorderLayout());
		
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(menuPanel, BorderLayout.EAST);
		
		boardPanel.setBackground(new Color(0, 0, 0));			
		
		squares = new JPanel[Board.WIDTH][Board.HEIGHT];		
	}
		
	/**
	 * Clears possible movement display.
	 */
	private void clearPossibleMovement() {
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
	
	/**
	 * Colors every unit depending on its owner.
	 *
	 * @param x the x
	 * @param y the y
	 */
	private void colorSquareByOwner(int x, int y) {
		JPanel currentSquare = squares[x][y];
		Entity currentEntity = matrix[x][y];
				
		if(board.isFortress(x, y) || board.isMountainPass(x, y))
		{
			if(board.getUnit(x, y) == null)
			{
				currentSquare.setBackground(COLOR_EMPTY);
				return;
			}
			else
			{
				currentEntity = board.getUnit(x, y);
			}
		}
		int owner = currentEntity.getOwner();
		
		switch(owner) {
			case 0:
				if(!board.isArsenal(x, y) && (board.isFighter(x, y) && !((Fighter)currentEntity).isConnected()))
					currentSquare.setBackground(COLOR_PLAYER0);
				else
					currentSquare.setBackground(COLOR_COM_PLAYER0);
				break;
			case 1:
				if(!board.isArsenal(x, y) && (board.isFighter(x, y) && !((Fighter)currentEntity).isConnected()))
					currentSquare.setBackground(COLOR_PLAYER1);
				else
					currentSquare.setBackground(COLOR_COM_PLAYER1);
				break;
		}
	}
	
	/**
	 * Draw possible movement.
	 *
	 * @param x the x
	 * @param y the y
	 */
	private void drawPossibleMovement(int x, int y) {		
		if(x >= 0 && y >= 0 && x < Board.WIDTH && y < Board.HEIGHT){
			clearPossibleMovement();
			
			Set<Coord> possibleMoves = getPossibleMoves(x, y);
			for(Coord c : possibleMoves) {
				squares[c.x][c.y].setBackground(COLOR_POSSIBLEMOVE);
			}
			
			if (possibleMoves.size() > 0)
				selectedSquare = new Coord(x, y);
			
			repaint();
		}
	}
	
	/**
	 * Displays the GUI.
	 */
	public void displayGUI() {			
		for(int j=0 ; j<Board.HEIGHT ; j++) {
			for(int i=0 ; i<Board.WIDTH ; i++) {
				
				JPanel currentSquare = squares[i][j];
				Entity currentEntity = matrix[i][j];
				//squares[i][j].setBackground(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
				currentSquare.removeAll();
				
				if(currentEntity == null)
					currentSquare.setBackground(COLOR_EMPTY);
				else {				
					switch(displayMode) {
						case DISPLAY_UNITS:
							displayUnit(i, j);
							break;
						case DISPLAY_ATTACK:
							resetSelectedSquare();
							displayAttackPotential(i, j);
							break;
						case DISPLAY_DEFENCE:
							resetSelectedSquare();
							displayDefencePotential(i, j);
							break;
						case DISPLAY_DEFENCE_MINUS_ATTACK:
							resetSelectedSquare();
							displayDefenceMinusAttackPotential(i, j);
							break;
						default:
							resetSelectedSquare();
							break;
					}
					
					if(currentEntity instanceof Mountain)
						currentSquare.setBackground(COLOR_MOUTAIN);
					
					if(currentEntity.canContain())
						currentEntity = board.getUnit(i,j);
					
					if(displayMode != DISPLAY_DEFENCE_MINUS_ATTACK)
						colorSquareByOwner(i, j);
					if(currentEntity instanceof MovableEntity && displayMode != DISPLAY_ATTACK_EVAL_TEAM0 && displayMode != DISPLAY_ATTACK_EVAL_TEAM1 
							&& displayMode != DISPLAY_DEFENCE_EVAL_TEAM0 && displayMode != DISPLAY_DEFENCE_EVAL_TEAM1 && displayMode != DISPLAY_DEFENCE_MINUS_ATTACK_EVAL_TEAM0 
							&& displayMode != DISPLAY_DEFENCE_MINUS_ATTACK_EVAL_TEAM1) {
						MovableEntity currentMovable = ((MovableEntity)currentEntity);
						if(currentMovable.canBeKilled()) {
							((JLabel)currentSquare.getComponent(0)).setText("("+((JLabel)currentSquare.getComponent(0)).getText()+")");
						}
						if(currentMovable.mustRetreat()) {
							((JLabel)currentSquare.getComponent(0)).setText("["+((JLabel)currentSquare.getComponent(0)).getText()+"]");
						}
					}
				}
				
			}
		}
				
		if (selectedSquare != null)
			drawPossibleMovement(selectedSquare.x, selectedSquare.y);
		else
			drawCommunications();
		
		if (displayMode == DISPLAY_ATTACK_EVAL_TEAM0) 
			displayAttackEvaluator(0);
		if (displayMode == DISPLAY_ATTACK_EVAL_TEAM1) 
			displayAttackEvaluator(1);
		
		if (displayMode == DISPLAY_DEFENCE_EVAL_TEAM0) 
			displayDefenceEvaluator(0);
		if (displayMode == DISPLAY_DEFENCE_EVAL_TEAM1) 
			displayDefenceEvaluator(1);
		if (displayMode == DISPLAY_DEFENCE_MINUS_ATTACK_EVAL_TEAM0)
			displayDefenceMinusAttackEvaluator(0);
		if (displayMode == DISPLAY_DEFENCE_MINUS_ATTACK_EVAL_TEAM1)
			displayDefenceMinusAttackEvaluator(1);
		
		this.repaint();
		this.setVisible(true);
	}

	/**
	 * Draws the communication lines.
	 */
	private void drawCommunications() {		
		Font fnt = new Font("Serif", Font.PLAIN, windowHeight/60);
		Set<Coord> com;
		if(p0Coms) {
			com = board.getCommunications(0);
						
			for (Coord c : com)
				if(matrix[c.x][c.y] == null) {
					JLabel tmp = new JLabel("o", SwingConstants.CENTER);
					tmp.setFont(fnt);
					tmp.setForeground(COLOR_COM_PLAYER0);
					squares[c.x][c.y].add(tmp);
				}
		}
		if(p1Coms) {
			com = board.getCommunications(1);
			for (Coord c : com) {
				if(matrix[c.x][c.y] == null) {
					JLabel tmp = new JLabel("o", SwingConstants.CENTER);
					tmp.setFont(fnt);
					tmp.setForeground(COLOR_COM_PLAYER1);
					squares[c.x][c.y].add(tmp);
				}
			}
		}
	}
	
	/**
	 * Display the attack matrix of a team.
	 *
	 * @param team the team
	 */
	private void displayAttackEvaluator(int team) {
		
		Integer[][] matrix = this.potential.matrix_attack.get(team);
		
		for(int y = 0 ; y < Board.HEIGHT ; y++) {
			for(int x = 0 ; x < Board.WIDTH ; x++) {
				int attack = matrix[x][y];
				Font fnt = new Font("Serif", Font.PLAIN, windowHeight/50);
				JLabel tmp = new JLabel(Integer.toString(attack), SwingConstants.RIGHT);
				tmp.setFont(fnt);
				squares[x][y].add(tmp);
				
				if(attack>0)
					squares[x][y].setBackground(new Color(255, Math.min(255, Math.max(0, 255 - 7*attack)), Math.min(255, Math.max(0, 255 - 7*attack))));
				else {
					attack = - attack;
					squares[x][y].setBackground(new Color(Math.min(255, Math.max(0, 255 - 7*attack)), 255, Math.min(255, Math.max(0, 255 - 7*attack))));
				}
			}
		}
		
	}
	
	/**
	 * Displays the defence matrix of a team.
	 * 
	 * @param team the team
	 */
	private void displayDefenceEvaluator(int team) {
		
		Integer[][] matrix = this.potential.matrix_defence.get(team);
		
		for(int y = 0 ; y < Board.HEIGHT ; y++) {
			for(int x = 0 ; x < Board.WIDTH ; x++) {
				int defence = matrix[x][y];
				Font fnt = new Font("Serif", Font.PLAIN, windowHeight/50);
				JLabel tmp = new JLabel(Integer.toString(defence), SwingConstants.RIGHT);
				tmp.setFont(fnt);
				squares[x][y].add(tmp);
				
				squares[x][y].setBackground(new Color(Math.min(255, Math.max(0, 255 - 6*defence)), 255, Math.min(255, Math.max(0, 255 - 6*defence))));
			}
		}
		
	}
	
	/**
	 * Displays weakpoints for a team.
	 * @param team the team
	 */
	private void displayDefenceMinusAttackEvaluator(int team) {
		
		Integer[][] matrixDef = this.potential.matrix_defence.get(team);
		Integer[][] matrixAtt = this.potential.matrix_attack.get((team+1)%2);
		
		for(int y = 0 ; y < Board.HEIGHT ; y++) {
			for(int x = 0 ; x < Board.WIDTH ; x++) {
				if(matrixDef[x][y] != 0 || matrixAtt[x][y] != 0) {
					int diff = matrixDef[x][y] - matrixAtt[x][y];
					Font fnt = new Font("Serif", Font.PLAIN, windowHeight/50);
					JLabel tmp = new JLabel(Integer.toString(diff), SwingConstants.RIGHT);
					tmp.setFont(fnt);
					squares[x][y].add(tmp);
					
					//squares[x][y].setBackground(new Color(200, Math.min(255, Math.max(0, 150 + 6 * diff)) ,50));
					squares[x][y].setBackground(Color.getHSBColor((float)Math.min(100, Math.max(10, 65 + 1.5 * diff))/360, 0.84f, 0.99f));
				} else {
					squares[x][y].setBackground(COLOR_EMPTY);
				}
			}
		}
		
	}
	
	/**
	 * Displays the total attack potential on a square.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void displayAttackPotential(int x, int y) {
		if(matrix[x][y] != null && !(matrix[x][y] instanceof Mountain)) {
			MovableEntity unit = board.getUnit(x, y);
			if(unit != null) {
				Font fnt = new Font("Serif", Font.PLAIN, windowHeight/50);

				JLabel tmp = new JLabel(Integer.toString(unit.getEnemyAttack()), SwingConstants.RIGHT);
				tmp.setFont(fnt);
				squares[x][y].add(tmp);
			}
		}
	}
	
	/**
	 * Displays the defence potential on a square.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void displayDefencePotential(int x, int y) {
		if(matrix[x][y] != null && !(matrix[x][y] instanceof Mountain)) {
			MovableEntity unit = board.getUnit(x, y);
			if(unit != null) {
				Font fnt = new Font("Serif", Font.PLAIN, windowHeight/50);

				JLabel tmp = new JLabel(Integer.toString(unit.getAllyDefence()), SwingConstants.RIGHT);
				tmp.setFont(fnt);
				squares[x][y].add(tmp);
			}
		}
	}
	
	/**
	 * Display defence minus attack potential on a square.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void displayDefenceMinusAttackPotential(int x, int y) {
		if(matrix[x][y] != null && !(matrix[x][y] instanceof Mountain)) {
			MovableEntity unit = board.getUnit(x, y);
			if(unit != null) {
				Font fnt = new Font("Serif", Font.PLAIN, windowHeight/50);
				
				int diff = (unit.getAllyDefence() - unit.getEnemyAttack());
				JLabel tmp = new JLabel(Integer.toString(diff), SwingConstants.RIGHT);
				tmp.setFont(fnt);
				squares[x][y].add(tmp);
				if(board.getUnit(x,y).getOwner() == 0)
					squares[x][y].setBackground(new Color(Math.min(240, Math.max(0, 75 + 4 * diff)), Math.min(240, Math.max(0, 75 + 4 * diff)), 255));
				else
					squares[x][y].setBackground(new Color(255, Math.min(240, Math.max(0, 75 + 4 * diff)), Math.min(240, Math.max(0, 75 + 4 * diff))));
			}
		}
	}
	
	/**
	 * Displays an entity's symbol in its square.
	 *
	 * @param x the x
	 * @param y the y
	 */
	private void displayUnit(int x, int y) {
		JLabel tmp = new JLabel(Character.toString(matrix[x][y].getSymbol()));
		tmp.setFont(new Font("Serif", Font.PLAIN, windowHeight/40));
		squares[x][y].add(tmp);
	}
	
	/**
	 * Display a pop-up.
	 *
	 * @param text the text
	 * @param title the title
	 * @param logo the logo
	 */
	public void displayPopup(String text, String title, int logo) {
		JOptionPane.showMessageDialog(this, text, title, logo);
	}
	

	/**
	 * Gets the possible moves of the unit at (x,y).
	 *
	 * @param x the x
	 * @param y the y
	 * @return the possible moves
	 */
	private Set<Coord> getPossibleMoves(int x, int y) {
		if(board.getUnit(x, y) instanceof MovableEntity)
			return board.getUnit(x,y).getPossibleMovement();
		return new HashSet<Coord>();
	}
	
	/**
	 * Resets the selected square.
	 */
	public void resetSelectedSquare(){
		selectedSquare = null;
	}

	/**
	 * The listener interface for receiving cellMouse events.
	 * The class that is interested in processing a cellMouse
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addCellMouseListener<code> method. When
	 * the cellMouse event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see CellMouseEvent
	 */
	private class CellMouseListener implements MouseListener {
	
		/** The x. */
		private int x;
		
		/** The y. */
		private int y;
		
		/**
		 * Instantiates a new cell mouse listener.
		 *
		 * @param x the x
		 * @param y the y
		 */
		public CellMouseListener(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1 && displayMode == DISPLAY_UNITS)
				drawPossibleMovement(x, y);
			if(e.getButton() == MouseEvent.BUTTON3 && displayMode == DISPLAY_UNITS)
				clearPossibleMovement();
		}
	
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseExited(MouseEvent e) {}	
	}
	
}
