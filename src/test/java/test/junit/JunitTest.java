package test.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class JunitTest {
	
	@BeforeAll
	public void beforeAllTest() {
		
	}
	
	@BeforeEach
	public void beforeEachTest() {
		
	}
	
	@Test
	public void assertEqualsTest() {
		assertEquals(new Integer(19), new Integer(19));
	}
	
	@Test
	public void assertNotEqualsTest() {
		assertNotEquals(new Integer(19), 20);
	}
	
	@Test
	public void assertThrowsTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> Integer.valueOf("!"));
	}
	
	@Test
	public void assertThrowsTestWithMethod() {
		Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> assertThrowsTestMethod());
	}
	
	public void assertThrowsTestMethod() {
		throw new ArrayIndexOutOfBoundsException();
	}
}
