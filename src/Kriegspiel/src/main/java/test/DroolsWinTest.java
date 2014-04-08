package test;

import static org.junit.Assert.assertEquals;
import main.Board;
import main.BoardFileFormatException;
import main.Engine;
import main.EntityLoader;

import org.junit.Test;


public class DroolsWinTest {
	
	@Test
	public void testWinWhenNoArsenal() throws BoardFileFormatException {
		Board b = new Board();
		Engine e = new Engine(b);
		
		EntityLoader loader = new EntityLoader(b);
		loader.setMapFilename("src/main/resources/board/Map1.kmp");
		loader.setMovableEntityFilename("src/main/resources/board/NoArsenalForPlayer0.ksv");
		
		e.initSession();
		
		loader.loadMap();
				
		loader.loadMovableEntities();
		
		b.setMapLoaded(true);
		
		e.computeCommunications();	
		
		e.computeAttackDefence();
		
		e.computeDeath();
		
		e.computeWin();
		
		System.out.println(b.getWinner());
		
		assertEquals(b.getWinner(), 1);
	}
	
}
