package test.java;

import java.util.function.Supplier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MethodReferenceTest {
	
	private static final int ANS = 1000;
	
	@Test
	public void staticMethod() {
		Assertions.assertEquals(ANS, template(MethodReferenceTest::Method1));
	}
	
	public int template(Supplier<Integer> supplier) {
		return supplier.get();
	}
	
	
	public static int Method1() {
		return ANS;
	}
}
