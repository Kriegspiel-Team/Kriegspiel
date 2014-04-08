package test;

import static org.junit.Assert.*;
import model.Infantry;

import org.junit.Test;

public class FighterTest {

	@Test
	public void testIsConnected() {
		Infantry i = new Infantry(0);
		i.setConnected(true);
		assertTrue(i.isConnected());
	}

	@Test
	public void testSetConnected() {
		Infantry i = new Infantry(0);
		i.setConnected(true);
		assertTrue(i.isConnected());
	}

}
