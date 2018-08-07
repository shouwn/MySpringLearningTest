package test.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.Test;

public class ReduceTest {

	@Test
	public void reduceSumTest() {
		Stream<Integer> stream = Stream.iterate(1, n -> n+1).limit(10);
		assertEquals(stream.reduce((p, c) -> p + c).orElse(0), new Integer(55));
	}

	@Test
	public void guguTest() {
		Stream.iterate(1, n -> n + 1).limit(18)
			.peek(i -> System.out.println( i + "단"))
			.flatMap(i -> Stream.iterate(1, n -> n + 1).limit(9)
				.map(j -> "" + i + " X " + j + " = " + i * j))
			.forEach(System.out::println);
	}
}
