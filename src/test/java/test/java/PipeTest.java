package test.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PipeTest {

	@Test
	public void pipeWriteRead() throws IOException {
		try(PipedWriter pw = new PipedWriter();
				PipedReader pr = new PipedReader(pw);
				BufferedReader br = new BufferedReader(pr)){
			
			String test ="TEST";
			
			pw.write(test);
			pw.write("\n");
			
			Assertions.assertEquals(test, br.readLine());
		}
	}
}
