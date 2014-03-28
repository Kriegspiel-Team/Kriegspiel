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
	}
}
