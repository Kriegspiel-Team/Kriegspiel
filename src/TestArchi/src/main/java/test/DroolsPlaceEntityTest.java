package test;

import org.junit.Test;

import main.Board;
import main.BoardFileFormatException;
import main.Engine;
import main.EntityLoader;
import model.Arsenal;
import model.Infantry;
import model.Mountain;
import model.UnmovableEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DroolsPlaceEntityTest {
	
	@Test
	public void testArsenalPlacement() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		
		
		assertEquals("Arsenal @ (14,1)", Arsenal.class, b.getMatrix()[14][1].getClass());
		assertEquals("Arsenal @ (14,1) [player 0]", 0, b.getMatrix()[14][1].getOwner());
		assertEquals("Arsenal @ (7,3)", Arsenal.class, b.getMatrix()[7][3].getClass());
		assertEquals("Arsenal @ (7,3) [player 0]", 0, b.getMatrix()[7][3].getOwner());
		
		assertEquals("Arsenal @ (2,19)", Arsenal.class, b.getMatrix()[2][19].getClass());
		assertEquals("Arsenal @ (2,19) [player 1]", 1, b.getMatrix()[2][19].getOwner());
		assertEquals("Arsenal @ (22,19)", Arsenal.class, b.getMatrix()[22][19].getClass());
		assertEquals("Arsenal @ (22,19) [player 1]", 1, b.getMatrix()[22][19].getOwner());
	}
	
	@Test
	public void testPlaceEntityOutOfBoard() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		int sizeBefore = b.getMatrix().length;
		
		b.placeEntity(-2, -5, new Infantry(0));
		
		int sizeAfter = b.getMatrix().length;
		
		assertEquals(sizeBefore, sizeAfter);
	}
	
	@Test
	public void testPlaceEntityOnMontain() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		
		b.placeEntity(9, 2, new Infantry(0));
		
		assertEquals("(9,2) must be a Moutain", Mountain.class, b.getMatrix()[9][2].getClass());
	}
	
	@Test
	public void testPlaceEntityInFortress() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		
		b.placeEntity(2, 12, new Infantry(0));
				
		assertEquals("(2,12) must contains an Infantry", Infantry.class, ((UnmovableEntity)b.getMatrix()[2][12]).getEntity().getClass());
	}
	
	
}
