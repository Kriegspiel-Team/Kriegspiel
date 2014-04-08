package test;

import static org.junit.Assert.*;
import model.SwiftRelay;

import org.junit.Test;

public class SwiftRelayTest {

	@Test
	public void testSwiftRelay() {
		try {
			new SwiftRelay(0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIsOnCommunications() {
		SwiftRelay r = new SwiftRelay(0);
		r.setOnCommunications(true);
		assertTrue(r.isOnCommunications());
	}

	@Test
	public void testSetOnCommunications() {
		SwiftRelay r = new SwiftRelay(0);
		r.setOnCommunications(true);
		assertTrue(r.isOnCommunications());
	}

}
