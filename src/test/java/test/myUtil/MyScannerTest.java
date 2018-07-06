package test.myUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import util.MyScanner;

public class MyScannerTest {
	
	private static String testFilePath;
	private static String testShakePath;
	
	@BeforeAll
	public static void testFilePath() {
		testFilePath = MyScannerTest.class.getResource("test.txt").getPath();
		testShakePath = MyScannerTest.class.getResource("shakespeare.txt").getPath();
	}

	@Test
	public void nextTestInteger() throws IOException {
		
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
	
	@Test
	public void nextTestString() throws IOException {
		try(MyScanner<String> scan = new MyScanner<>();
				BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {

			reader.readLine(); // 첫 번째 줄은 숫자 테스트
			String[] expected = reader.readLine().split(" "); // 두 번쨰 줄은 영어 소문자
			
			Assertions.assertArrayEquals(new String[] {
					"apple", "ball", "cat", "dead", "eat", "fake", "goal"
			}, expected);
			
			scan.setReader(new FileReader(testFilePath));
			scan.compile("[a-zA-z]+", s -> s);

			List<String> list = new ArrayList<>();
			
			String i;
			while((i = scan.next()) != null) {
				list.add(i);
			}
			
			Assertions.assertArrayEquals(expected, list.toArray(new String[0]));
		}
	}
	
	@Test
	public void alreadyClose() throws IOException {
		try(MyScanner<Integer> scan = new MyScanner<>()){
			InputStreamReader rd = new InputStreamReader(System.in);
			scan.setReader(rd);
			
			rd.close();
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
	
	@Test
	public void compareReaderTime() throws Throwable {
		long brTime = timeTemplate(this::doBufferedReader);
		long scanTime = timeTemplate(this::doScanner);
		long myScanTime = timeTemplate(this::doMyScanner);
		
		System.out.println("compareReaderTime");
		System.out.println("    BufferedReader: " + brTime);
		System.out.println("    Scanner: " + scanTime);
		System.out.println("    MyScanner: " + myScanTime);
	}
	
	public long timeTemplate(Executable executable) throws Throwable { // org.junit.jupiter.api.function.Executable;
		long time = System.currentTimeMillis();
		executable.execute();
		return System.currentTimeMillis() - time;
	}
	
	public void doBufferedReader() throws FileNotFoundException, IOException {

		try(BufferedReader br = new BufferedReader(new FileReader(testShakePath))){
			String input;
			while((input = br.readLine()) != null)
				input.split(" ");
		}
		
	}
	
	public void doScanner() throws FileNotFoundException {
		try(Scanner scan = new Scanner(new BufferedReader(new FileReader(testShakePath)))){
			while(scan.hasNext())
				scan.next();
		}
	}
	
	public void doMyScanner() throws IOException {
		try(MyScanner<String> scan = new MyScanner<>()){
			scan.setReader(new FileReader(testShakePath));
			scan.compile("[a-zA-Z]+", s -> s);
			
			while(scan.next() != null);
		}
	}
}
