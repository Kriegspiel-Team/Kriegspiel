package main;


import view.BoardDisplayer;

public class Main {

	public static void main(String[] args) {	
		final BoardController controller = new BoardController();	//The Controller

    	BoardDisplayer bd = new BoardDisplayer(controller);	//The View
        controller.setBoardDisplayer(bd);
		controller.loadDefaultBoard();
	}
}
