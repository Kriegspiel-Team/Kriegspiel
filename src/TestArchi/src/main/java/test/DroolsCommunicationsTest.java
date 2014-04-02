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
import model.Relay;
import model.UnmovableEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class DroolsCommunicationsTest {
	
	@Test
	public void testMoutainBlocking() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		e.computeCommunications();
		
		HashSet<Coord> comPlayer0 = b.getCommunications(0);
		
		assertFalse("No communication @ (10,3) for player 0", comPlayer0.contains(new Coord(10, 3)));		
	}
	
	@Test
	public void testEnemyBlocking() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		
		b.placeEntity(14, 1, new Arsenal(0));
		b.saveArsenalPlacement(14, 1);
		
		b.placeEntity(14, 4, new Infantry(1));
		
		e.computeCommunications();
		
		HashSet<Coord> comPlayer0 = b.getCommunications(0);
		
		assertTrue("Communication @ (14,3) for player 0", comPlayer0.contains(new Coord(14, 3)));
		assertFalse("No communication @ (14,6) for player 0", comPlayer0.contains(new Coord(14, 6)));
	}
	
	@Test
	public void testSpreadCommunication() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		e.computeCommunications();
		
		HashSet<Coord> comPlayer0 = b.getCommunications(0);
		
		assertTrue("(14,3) connected - Spread vertically", comPlayer0.contains(new Coord(14, 3)));	
		assertTrue("(10,1) connected - Spread horizontally", comPlayer0.contains(new Coord(10, 1)));	
		assertTrue("(18,5) connected - Spread diagonally", comPlayer0.contains(new Coord(18, 5)));	
	}
	
	@Test
	public void testRelayReflectCommunication() {
		Board b = new Board();
		Engine e = new Engine(b);
		e.initSession();
		e.placeFixedEntities();
		b.placeEntity(14, 5, new Relay(0));
		e.computeCommunications();
		
		HashSet<Coord> comPlayer0 = b.getCommunications(0);
		
		assertTrue("(20,5) connected - Reflect vertically", comPlayer0.contains(new Coord(20, 5)));	
		assertTrue("(14,7) connected - Reflect horizontally", comPlayer0.contains(new Coord(14, 7)));	
		assertTrue("(17,8) connected - Reflect diagonally", comPlayer0.contains(new Coord(17, 8)));
	}
	
	
}
