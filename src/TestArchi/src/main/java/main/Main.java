package main;

import javax.swing.SwingUtilities;
import view.BoardDisplayer;

public class Main {

	public static void main(String[] args) {
		
		final BoardController controller = new BoardController();
		controller.loadDefaultBoard();
    	
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	BoardDisplayer bd = new BoardDisplayer(controller);
                bd.displayGUI();
                controller.setBoardDisplayer(bd);
            }
        });
		
		//OLD
    	/*final Board b = new Board();
    	
    	Engine e = new Engine(b);
    	
    	
    	e.placeFixedEntities();
    	
    	
    	
    	b.loadBoardWithFile(Paths.get("src/main/resources/board/Sample1.txt").toAbsolutePath().toString());
    	e.computeCommunications();
      	e.computePossibleMoves();
      	e.computeAttackDefence();
      	
      	final Potentials p = new Potentials(b);
      	p.computePotentials();
      	
      	e.computeDeath();
    	
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	BoardDisplayer bd = new BoardDisplayer(b, p);
                bd.displayGUI();
            }
        });*/
    	
	}
}
