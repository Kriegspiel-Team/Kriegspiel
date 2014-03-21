package main;

import javax.swing.SwingUtilities;

import view.BoardDisplayer;

public class Main {

	
	public static void main(String[] args) {
    	
    	final Board b = new Board();
    	
    	/*Engine e = new Engine(b);
    	e.placeFixedEntities();
    	b.loadBoardWithFile(Paths.get("src/main/resources/board/Sample2.txt").toAbsolutePath().toString());
    	e.computeCommunications();
      	e.computePossibleMoves();
      	
      	Potentials p = new Potentials(b);
      	p.UnityPotentials();*/
    	
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	BoardDisplayer bd = new BoardDisplayer(b);
                bd.displayGUI();
            }
        });
    	
	}
}
