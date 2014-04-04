package main;


import view.BoardDisplayer;

/**
 * The Main Class.
 */
public class Main {

	/**
	 * The program's entry point.
	 */
	public static void main(String[] args) {
		
		final BoardController controller = new BoardController();

    	BoardDisplayer bd = new BoardDisplayer(controller);
        controller.setBoardDisplayer(bd);
		controller.loadDefaultBoard();
	}
}
