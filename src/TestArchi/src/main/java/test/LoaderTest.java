package test;

import org.junit.Test;

import main.Board;
import main.BoardFileFormatException;
import main.EntityLoader;
import model.Infantry;
import static org.junit.Assert.assertEquals;



public class LoaderTest {

	@Test(expected = BoardFileFormatException.class)
	public void testExceptionIsThrown() throws BoardFileFormatException {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/ErrSample.txt", "src/main/resources/board/Map1.txt");
		loader.loadMovableEntities();
	}
	
	@Test
	public void testEntityPlacement() {
		Board b = new Board();
		EntityLoader loader = new EntityLoader(b, "src/main/resources/board/SingleInfantry.txt", "src/main/resources/board/Map1.txt");
		try {
			loader.loadMovableEntities();
		} catch (BoardFileFormatException e) {
			e.printStackTrace();
		}
		
		assertEquals("Infantry placed in (7,3)", Infantry.class, b.getMatrix()[7][3].getClass());
		assertEquals("Own to player 0", 0, b.getMatrix()[7][3].getOwner());
	}
	
	
}
