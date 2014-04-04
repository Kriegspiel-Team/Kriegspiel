package test;

import static org.junit.Assert.*;
import model.Fortress;
import model.Infantry;
import model.Mountain;

import org.junit.Test;

public class UnmovableEntityTest {

	@Test
	public void testSetEntity() {
		Fortress f = new Fortress();
		Mountain m = new Mountain(); 
		Infantry i = new Infantry(0);
		
		f.setEntity(i);
		m.setEntity(i);
		
		assertTrue(f.getEntity() == i);
		assertTrue(m.getEntity() == null);
	}

	@Test
	public void testGetEntity() {
		Fortress f = new Fortress();
		Mountain m = new Mountain(); 
		Infantry i = new Infantry(0);
		
		f.setEntity(i);
		m.setEntity(i);
		
		assertTrue(f.getEntity() == i);
		assertTrue(m.getEntity() == null);
	}

	@Test
	public void testIsEmpty() {
		Fortress f = new Fortress();
		Mountain m = new Mountain(); 
		Infantry i = new Infantry(0);
		
		f.setEntity(i);
		m.setEntity(i);
		
		assertFalse(f.isEmpty());
		assertTrue(m.isEmpty());
	}

}
