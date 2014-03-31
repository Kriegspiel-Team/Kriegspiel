package main;


import view.BoardDisplayer;

/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 */
	public static void main(String[] args) {
		
		final BoardController controller = new BoardController();

    	BoardDisplayer bd = new BoardDisplayer(controller);
        controller.setBoardDisplayer(bd);
		controller.loadDefaultBoard();
	}
}
