package main;

import java.nio.file.Paths;

import javax.swing.JOptionPane;

import view.BoardDisplayer;
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
	
	private void loadBoard(String file) {
		
		if (!board.loadBoardWithFile(Paths.get(file).toAbsolutePath().toString())) {
			boardDisplayer.displayPopup("The file format is incorrect !", "An error occured", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		engine.initSession();
		engine.placeFixedEntities();
				
		engine.computeCommunications();
      	engine.computePossibleMoves();
      	engine.computeAttackDefence();
      	
      	potentials.computePotentials();
      	
      	engine.computeDeath();
	}
	
	public void loadDefaultBoard() {
		loadBoard("src/main/resources/board/Sample1.txt");
	}
	
	public void loadNewBoard(String file) {				
		loadBoard(file);		
		boardDisplayer.drawEntities();
		boardDisplayer.displayGUI();	
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
