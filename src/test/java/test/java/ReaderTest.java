package test.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReaderTest {
	
	private static String path;
	
	@BeforeAll
	public static void initPath() {
		path = ReaderTest.class
				.getResource("readerTestTxt.txt")
				.getPath();
	}
	
	@Test
	public void scannerAndBufferedReader() throws FileNotFoundException {
		try(Scanner scan = new Scanner(new BufferedReader(new FileReader(path)))){
			
			Assertions.assertEquals(1, scan.nextInt());
		}
	}
	
	@Test
	public void scanner() throws FileNotFoundException {
		try(Scanner scan = new Scanner(new FileReader(path))){
			
			Assertions.assertEquals(1, scan.nextInt());
		}
	}
	
	@Test
	public void bufferedReader() throws NumberFormatException, IOException {
		try(BufferedReader rd = new BufferedReader(new FileReader(path))){
			
			Assertions.assertEquals(1, (int) Integer.valueOf(rd.readLine().split(" ")[0]));
		}
	}

}
