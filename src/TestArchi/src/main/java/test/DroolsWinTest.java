package test;

import static org.junit.Assert.assertEquals;
import main.Board;
import main.BoardFileFormatException;
import main.Engine;
import main.EntityLoader;

import org.junit.Test;



public class DroolsWinTest {
	
	@Test
	public void testWinWhenNoArsenal() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/NoArsenalForPlayer0.txt", "src/main/resources/board/Map1.txt");
		try {
			loader.loadMovableEntities();
		} catch (BoardFileFormatException e1) {
			e1.printStackTrace();
		}
		
		e.computeCommunications();
		
		e.computeWin();
		
		assertEquals(b.getWinner(), 1);
	}
	
}
