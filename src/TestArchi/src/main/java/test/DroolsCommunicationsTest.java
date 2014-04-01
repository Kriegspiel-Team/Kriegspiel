package test;

import java.util.HashSet;

import org.junit.Test;

import main.Board;
import main.BoardFileFormatException;
import main.Coord;
import main.Engine;
import main.EntityLoader;
import model.Arsenal;
import model.Infantry;
import model.Mountain;
import model.UnmovableEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class DroolsCommunicationsTest {
	
	@Test
	public void testMoutainBlocking() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		
		HashSet<Coord> comPlayer0 = b.getCommunications(0);
		
		assertFalse("No communication @ (10,3)", comPlayer0.contains(new Coord(10, 3)));		
	}
	
	@Test
	public void testSpreadCommunication() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		
		HashSet<Coord> comPlayer0 = b.getCommunications(0);
		
		assertFalse("No communication @ (10,3)", comPlayer0.contains(new Coord(10, 3)));		
	}
	
	
}
