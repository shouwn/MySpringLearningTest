package test.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatternMatcherTest {
	
	@Test
	public void notFindAndGroup() {
		String s = "I'm hero";
		
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(s);
		
		matcher.find();
		Assertions.assertThrows(IllegalStateException.class, () -> matcher.group(0));
	}
}
