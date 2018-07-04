package test.myUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
		
		try(MyScanner<Integer> myScan = new MyScanner<>();
				BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {

			myScan.setReader(new FileReader(testFilePath));
			myScan.compile("[0-9]+", s -> Integer.valueOf(s));

		}
	}
	
	@Test
	public void bufferedReaderTest() throws FileNotFoundException, IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(testFilePath))){
			int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
			String[] line = reader.readLine().split(" ");
			int[] actual = new int[line.length];
			
			for(int i = 0; i < actual.length; i++) 
				actual[i] = Integer.valueOf(line[i]);
			
			Assertions.assertArrayEquals(expected, actual);
		}
	}
	
	public int[] getArrayByBufferedReader() {
		try(BufferedReader reader = new BufferedReader(new FileReader(testFilePath))){
			int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
			String[] line = reader.readLine().split(" ");
			int[] actual = new int[line.length];
			
			for(int i = 0; i < actual.length; i++) 
				actual[i] = Integer.valueOf(line[i]);
			
			Assertions.assertArrayEquals(expected, actual);
		}
	}
}
