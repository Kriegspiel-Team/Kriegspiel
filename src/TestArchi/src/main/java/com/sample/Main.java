package com.sample;

import java.nio.file.Paths;

import view.BoardDisplayer;

public class Main {

	public static void main(String[] args) {
    	
    	Board b = new Board();
    	BoardDisplayer bd = new BoardDisplayer(b);
    	Engine e = new Engine(b);
    	e.placeFixedEntities();
    	b.loadBoardWithFile(Paths.get("src/main/resources/board/Sample1.txt").toAbsolutePath().toString());
    	e.computePossibleMoves();
        bd.displayGUI(-1, -1);
	}

}
