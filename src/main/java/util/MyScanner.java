package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyScanner<T> implements AutoCloseable{

	private BufferedReader reader;
	private Pattern pattern;
	private Matcher matcher;
	private Function<String, T> nexter;

	public void setReader(Reader reader) {
		this.reader = new BufferedReader(reader);
	}
	public void compile(String regex, Function<String, T> nexter) throws IOException {
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(reader.readLine());
		this.nexter = nexter;
	}

	// 정규식에 해당하는 문자열이 있는지 확인하는 메소드
	private boolean find() throws IOException {

		while(!matcher.find()) {
			String line = reader.readLine();
			if(line == null)
				return false;
			matcher = pattern.matcher(line);
		}

		return true;
	}

	// 찾은 문자열을 return 해주는 메소드
	public T next() throws IOException{
		if(this.find())
			return nexter.apply(matcher.group(0));
		
		return null;
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}
}

