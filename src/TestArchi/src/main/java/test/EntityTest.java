package test;

import static org.junit.Assert.*;
import main.Coord;
import model.*;

import org.junit.Test;

public class EntityTest {

	@Test
	public void testCanContain() {
		Arsenal a = new Arsenal(0);
		Infantry i = new Infantry(0);
		
		assertTrue(a.canContain());
		assertTrue(i.canContain());
	}

	@Test
	public void testGetSymbol() {
		Arsenal a = new Arsenal(0);
		Fortress f = new Fortress();
		Mountain m = new Mountain();
		MountainPass mp = new MountainPass();
		Canon c = new Canon(0);
		SwiftCanon sc = new SwiftCanon(0);
		Cavalry ca = new Cavalry(0);
		Infantry i = new Infantry(0);
		Relay r = new Relay(0);
		SwiftRelay sr = new SwiftRelay(0);
		
		assertTrue(a.getSymbol() == 'A');
		assertTrue(f.getSymbol() == 'F');
		assertTrue(m.getSymbol() == '^');
		assertTrue(mp.getSymbol() == ' ');
		assertTrue(c.getSymbol() == 'c');
		assertTrue(sc.getSymbol() == 'C');
		assertTrue(ca.getSymbol() == 'H');
		assertTrue(i.getSymbol() == 'i');
		assertTrue(r.getSymbol() == 'r');
		assertTrue(sr.getSymbol() == 'R');
	}

	@Test
	public void testSetCoord() {
		Infantry i = new Infantry(0);
		i.setCoord(new Coord(10,10));
		Coord c = new Coord(10,10);
		assertTrue(i.getCoord().equals(c));
	}

	@Test
	public void testGetCoord() {
		Infantry i = new Infantry(0);
		i.setCoord(new Coord(10,10));
		Coord c = new Coord(10,10);
		assertTrue(i.getCoord().equals(c));
	}

	@Test
	public void testGetOwner() {
		Infantry i = new Infantry(0);
		assertTrue(i.getOwner() == 0);
	}

}
