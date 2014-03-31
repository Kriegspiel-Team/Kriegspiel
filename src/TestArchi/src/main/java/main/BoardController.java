package main;


import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import view.BoardDisplayer;
import evaluator.InfluenceArea;
import evaluator.Potentials;

public class BoardController {

	private Engine engine;
	private Board board;
	private Potentials potentials;
	private BoardDisplayer boardDisplayer;
	
	public BoardController() {
		board = new Board();
		engine = new Engine(board);
		potentials = new Potentials(board);
	}
	
	/*
	 * Tries to load a board and if successful,
	 * initializes the rules engine and
	 * computes everything
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
		
      	//engine.computePossibleMoves();
		InfluenceArea.runInfluenceArea(board);
		
      	engine.computeAttackDefence();
      	
      	potentials.computePotentials();
      	
      	engine.computeDeath();
	}
	
	public void loadDefaultBoard() {
		loadNewBoard("src/main/resources/board/Sample3.txt");
	}
	
	public void loadNewBoard(String file) {	
		loadBoard(file);	
    	
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
				boardDisplayer.drawEntities();
				boardDisplayer.displayGUI();	
		    }
	    });
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Potentials getPotentials() {
		return potentials;
	}
	
	public void setBoardDisplayer(BoardDisplayer bd) {
		this.boardDisplayer = bd;
	}
	
}
