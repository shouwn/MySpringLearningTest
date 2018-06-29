package test.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class JunitTest {
	
	private static final List<JunitTest> beforeEachList = new ArrayList<>();
	private static JunitTest beforeAllTest;
	private static int newingCount = 0;
	private static int beforeAllCount = 0;
	
	@BeforeAll
	public static void beforeAllTest() {
		beforeAllTest = new JunitTest();
		beforeAllCount += 1;
	}
	
	@BeforeEach
	public void beforeEachTest() {
		beforeEachList.add(this);
		newingCount++;
	}

	@Test
	public void beforeEachTestContext() {
		Assertions.assertNotEquals(0, beforeEachList.size());
		Assertions.assertFalse(() -> {
			
			int i = 1;
			for(JunitTest t1 : beforeEachList) {
				for(JunitTest t2 : beforeEachList.subList(i++, beforeEachList.size()))
					if(t1 == t2)
						return true;
			}
			return false;
		});
	}	
	
	@Test
	public void beforeAllTestContext() {
		Assertions.assertEquals(1, beforeAllCount);
		Assertions.assertNotNull(beforeAllTest);
	}
	
	@Test
	public void assertFalseBooleanSupplier() {
		Assertions.assertFalse(() -> false);
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
