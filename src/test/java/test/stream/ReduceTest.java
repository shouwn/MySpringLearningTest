package test.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.Test;

public class ReduceTest {
	
	@Test
	public void ReduceSumTest() {
		Stream<Integer> stream = Stream.iterate(1, n -> n+1).limit(10);
		assertEquals(stream.reduce((p, c) -> p + c).orElse(0), new Integer(55));
	}
}
