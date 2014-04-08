package test;

import static org.junit.Assert.*;
import model.Cavalry;

import org.junit.Test;

public class CavalryTest {

	@Test
	public void testSetAttackNull() {
		Cavalry c = new Cavalry(0);
		c.setAttackNull();
		assertTrue(c.getAttack() == 0);
		assertTrue(c.getAttackCharge() == 0);
	}

	@Test
	public void testCavalry() {
		try {
			new Cavalry(0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetAttackCharge() {
		Cavalry c = new Cavalry(0);
		assertTrue(c.getAttackCharge() == 7);
	}

}
