package test.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class JunitTest {
	@Test
	public void assertEqualsTest() {
		assertEquals(new Integer(19), new Integer(19));
	}
	
	@Test
	public void assertNotEqualsTest() {
		assertNotEquals(new Integer(19), 20);
	}
}
