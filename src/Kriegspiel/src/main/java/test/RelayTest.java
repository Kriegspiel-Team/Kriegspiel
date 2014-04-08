package test;

import static org.junit.Assert.*;
import model.Relay;

import org.junit.Test;

public class RelayTest {

	@Test
	public void testRelay() {
		try {
			new Relay(0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIsOnCommunications() {
		Relay r = new Relay(0);
		r.setOnCommunications(true);
		assertTrue(r.isOnCommunications());
	}

	@Test
	public void testSetOnCommunications() {
		Relay r = new Relay(0);
		r.setOnCommunications(true);
		assertTrue(r.isOnCommunications());
	}

}
