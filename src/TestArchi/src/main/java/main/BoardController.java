package main;


import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import view.BoardDisplayer;
import evaluator.InfluenceArea;
import evaluator.Potentials;

/**
 * The Class BoardController.
 */
public class BoardController {

	/** The engine. */
	private Engine engine;
	
	/** The board. */
	private Board board;
	
	/** The potentials. */
	private Potentials potentials;
	
	/** The board displayer. */
	private BoardDisplayer boardDisplayer;
	
	/**
	 * Instantiates a new board controller.
	 */
	public BoardController() {
		board = new Board();
		engine = new Engine(board);
		potentials = new Potentials(board);
	}
	
	/**
	 * Tries to load a board and if successful,
	 * initializes the rules engine and
	 * computes everything
	 *
	 * @param file the file
	 */
	private void loadBoard(String file) {		
		EntityLoader loader = new EntityLoader(board, file);
		
		if (!loader.isValidFormat()){
			boardDisplayer.displayPopup("Board loading failed :(", "An error occured", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		board.resetBoard();
		
		boardDisplayer.resetSelectedSquare();
		
		engine.initSession();
		engine.placeFixedEntities();
		
		board.loadBoardWithFile(loader);
						
		engine.computeCommunications();
		
		InfluenceArea.runInfluenceArea(board);
		
      	engine.computeAttackDefence();
      	
      	potentials.computePotentials();
      	
      	engine.computeDeath();
      	
      	engine.computeWin();
	}
	
	private void checkWinner() {
		if (board.getWinner() == 0) {
			boardDisplayer.displayPopup("Blue player win", "There is a winner!", JOptionPane.DEFAULT_OPTION);
		} else if (board.getWinner() == 1) {
			boardDisplayer.displayPopup("Red player win", "There is a winner!", JOptionPane.DEFAULT_OPTION);
		}
	}
	
	/**
	 * Load default board.
	 */
	public void loadDefaultBoard() {
		loadNewBoard("src/main/resources/board/Sample4.txt");
	}
	
	/**
	 * Load new board.
	 *
	 * @param file the file from which to load the board data
	 */
	public void loadNewBoard(String file) {	
		loadBoard(file);	
    	
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
				boardDisplayer.drawEntities();
				boardDisplayer.displayGUI();	
				checkWinner();
		    }
	    });
		
	}
	
	/**
	 * Gets the board.
	 *
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Gets the potentials.
	 *
	 * @return the potentials
	 */
	public Potentials getPotentials() {
		return potentials;
	}
	
	/**
	 * Sets the board displayer.
	 *
	 * @param bd the new board displayer
	 */
	public void setBoardDisplayer(BoardDisplayer bd) {
		this.boardDisplayer = bd;
	}
	
}
