package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import main.Coord;
import model.Canon;
import model.Cavalry;
import model.Infantry;
import model.Relay;
import model.SwiftCanon;
import model.SwiftRelay;

import org.junit.Test;

public class MovableEntityTest {

	@Test
	public void testGetSpeed() {
		Infantry i = new Infantry(0);
		Cavalry c = new Cavalry(0);
		
		assertTrue(i.getSpeed() == 1);
		assertTrue(c.getSpeed() == 2);
	}

	@Test
	public void testResetPossibleMovement() {
		Infantry i = new Infantry(0);
		i.addPossibleMovement(new Coord(10,10));
		i.addPossibleMovement(new Coord(2,10));
		i.addPossibleMovement(new Coord(4,10));
		i.addPossibleMovement(new Coord(18,10));
		
		i.resetPossibleMovement();
		assertTrue(i.getPossibleMovement().size() == 0);
	}

	@Test
	public void testAddPossibleMovement() {
		Infantry i = new Infantry(0);
		i.addPossibleMovement(new Coord(10,10));
		i.addPossibleMovement(new Coord(2,10));
		i.addPossibleMovement(new Coord(4,10));
		
		assertTrue(i.getPossibleMovement().contains(new Coord(10,10)));
		assertTrue(i.getPossibleMovement().contains(new Coord(2,10)));
		assertTrue(i.getPossibleMovement().contains(new Coord(4,10)));
	}

	@Test
	public void testGetPossibleMovement() {
		Infantry i = new Infantry(0);
		Coord c0 = new Coord(10,10);
		Coord c1 = new Coord(2,10);
		
		Set<Coord> liste = new HashSet<Coord>();
		liste.add(c0);
		liste.add(c1);
		
		i.addPossibleMovement(c0);
		i.addPossibleMovement(c1);
		
		assertTrue(i.getPossibleMovement().equals(liste));
	}

	@Test
	public void testGetAttack() {
		Canon c = new Canon(0);
		SwiftCanon sc = new SwiftCanon(0);
		Cavalry ca = new Cavalry(0);
		Infantry i = new Infantry(0);
		Relay r = new Relay(0);
		SwiftRelay sr = new SwiftRelay(0);
		
		assertTrue(c.getAttack() == 5);
		assertTrue(sc.getAttack() == 5);
		assertTrue(ca.getAttack() == 4);
		assertTrue(i.getAttack() == 4);
		assertTrue(r.getAttack() == 0);
		assertTrue(sr.getAttack() == 0);
	}

	@Test
	public void testGetDefence() {
		Canon c = new Canon(0);
		SwiftCanon sc = new SwiftCanon(0);
		Cavalry ca = new Cavalry(0);
		Infantry i = new Infantry(0);
		Relay r = new Relay(0);
		SwiftRelay sr = new SwiftRelay(0);
		
		assertTrue(c.getDefence() == 8);
		assertTrue(sc.getDefence() == 8);
		assertTrue(ca.getDefence() == 5);
		assertTrue(i.getDefence() == 6);
		assertTrue(r.getDefence() == 1);
		assertTrue(sr.getDefence() == 1);
	}

	@Test
	public void testSetAttackNull() {
		Infantry i = new Infantry(0);
		i.setAttackNull();
		assertTrue(i.getAttack() == 0);
	}

	@Test
	public void testSetDefence() {
		Infantry i = new Infantry(0);
		i.setAttackNull();
		assertTrue(i.getAttack() == 0);
	}

	@Test
	public void testGetRange() {
		Canon c = new Canon(0);
		SwiftCanon sc = new SwiftCanon(0);
		Cavalry ca = new Cavalry(0);
		Infantry i = new Infantry(0);
		Relay r = new Relay(0);
		SwiftRelay sr = new SwiftRelay(0);
		
		assertTrue(c.getRange() == 1);
		assertTrue(sc.getRange() == 2);
		assertTrue(ca.getRange() == 2);
		assertTrue(i.getRange() == 1);
		assertTrue(r.getRange() == 1);
		assertTrue(sr.getRange() == 2);
	}

	@Test
	public void testGetEnemyAttack() {
		Infantry i = new Infantry(0);
		i.setEnemyAttack(10);
		assertTrue(i.getEnemyAttack() == 10);
		i.setEnemyAttack(-5240);
		assertTrue(i.getEnemyAttack() == -5240);
	}

	@Test
	public void testSetEnemyAttack() {
		Infantry i = new Infantry(0);
		i.setEnemyAttack(10);
		assertTrue(i.getEnemyAttack() == 10);
		i.setEnemyAttack(-5240);
		assertTrue(i.getEnemyAttack() == -5240);
	}

	@Test
	public void testGetAllyDefence() {
		Infantry i = new Infantry(0);
		i.setAllyDefence(84);
		assertTrue(i.getAllyDefence() == 84);
		i.setAllyDefence(-5);
		assertTrue(i.getAllyDefence() == -5);
	}

	@Test
	public void testSetAllyDefence() {
		Infantry i = new Infantry(0);
		i.setAllyDefence(84);
		assertTrue(i.getAllyDefence() == 84);
		i.setAllyDefence(-5);
		assertTrue(i.getAllyDefence() == -5);
	}

	@Test
	public void testCanBeKilled() {
		Infantry i = new Infantry(0);
		i.setCanBeKilled(true);
		assertTrue(i.canBeKilled());
	}

	@Test
	public void testSetCanBeKilled() {
		Infantry i = new Infantry(0);
		i.setCanBeKilled(true);
		assertTrue(i.canBeKilled());
	}

	@Test
	public void testMustRetreat() {
		Infantry i = new Infantry(0);
		i.setMustRetreat(true);
		assertTrue(i.mustRetreat());
	}

	@Test
	public void testSetMustRetreat() {
		Infantry i = new Infantry(0);
		i.setMustRetreat(true);
		assertTrue(i.mustRetreat());
	}

}
