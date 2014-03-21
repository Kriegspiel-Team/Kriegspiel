package main;

import java.nio.file.Paths;

import javax.swing.SwingUtilities;

import evaluator.Potentials;
import view.BoardDisplayer;

public class Main {

	
	public static void main(String[] args) {
    	
    	final Board b = new Board();
    	
    	Engine e = new Engine(b);
    	e.placeFixedEntities();
    	b.loadBoardWithFile(Paths.get("src/main/resources/board/Sample3.txt").toAbsolutePath().toString());
    	e.computeCommunications();
      	e.computePossibleMoves();
      	e.computeDefenceBonuses();
      	
      	Potentials p = new Potentials(b);
      	p.UnityPotentials();
    	
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	BoardDisplayer bd = new BoardDisplayer(b);
                bd.displayGUI();
            }
        });
    	
	}
}
