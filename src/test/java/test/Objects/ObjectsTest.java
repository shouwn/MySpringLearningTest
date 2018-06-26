package test.Objects;

import java.util.Objects;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ObjectsTest {
	
	@Test
	public void equalsTest() {
		String s1 = "test", s2 = "test";
		String s3 = "Test", s4 = null;
		
		Assertions.assertTrue(Objects.equals(s1, s2));
		Assertions.assertFalse(Objects.equals(s1, s3));
		Assertions.assertFalse(Objects.equals(s1, s4));
	}
}
