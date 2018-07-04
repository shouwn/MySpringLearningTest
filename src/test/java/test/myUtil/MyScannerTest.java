package test.myUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import util.MyScanner;

public class MyScannerTest {
	
	private static String testFilePath;
	
	@BeforeAll
	public static void testFilePath() {
		testFilePath = MyScannerTest.class.getResource("test.txt").getPath();	
	}

	@Test
	public void nextTest() throws IOException {
		
		try(MyScanner<Integer> scan = new MyScanner<>()) {

			Integer[] expected = getArrayByBufferedReader();
			
			scan.setReader(new FileReader(testFilePath));
			scan.compile("[0-9]+", s -> Integer.valueOf(s));

			List<Integer> list = new ArrayList<>();
			
			Integer i;
			while((i = scan.next()) != null) {
				list.add(i);
			}
			
			Assertions.assertArrayEquals(expected, list.toArray(new Integer[0]));
		}
	}
	
	public Integer[] getArrayByBufferedReader() throws FileNotFoundException, IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(testFilePath))){
			Integer[] expected = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8};
			String[] line = reader.readLine().split(" ");
			Integer[] actual = new Integer[line.length];
			
			for(int i = 0; i < actual.length; i++) 
				actual[i] = Integer.valueOf(line[i]);
			
			Assertions.assertArrayEquals(expected, actual);
			
			return actual;
		}
	}
}
