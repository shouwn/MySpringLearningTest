package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyScanner<T> implements AutoCloseable{

	//표준 입출력에서 마지막 끝을 나타내는 에러가 있음 잡기 바람

	private BufferedReader reader;

	private Pattern integerPattern = Pattern.compile("([-+]?[0-9]+)");
	private Pattern pattern;
	private Matcher matcher;
	
	private Pattern currentPattern;

	private Function<String, T> nexter;

	public void setReader(Reader reader) {
		this.reader = new BufferedReader(reader);
	}

	public void compile(String regex, Function<String, T> nexter) {
		this.pattern = Pattern.compile(regex);
		this.nexter = nexter;
	}

	// 정규식에 해당하는 문자열이 있는지 확인하는 메소드
	private boolean find(Pattern pattern){

		if(matcher != null && pattern != this.currentPattern) {
			matcher.usePattern(pattern);
			this.currentPattern = pattern;
		}
		
		try {
			while(this.matcher == null || !this.matcher.find()) {
				String line = reader.readLine();
				if(line == null)
					return false;
				this.matcher = pattern.matcher(line);
			}

			return true;
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	// 찾은 문자열을 return 해주는 메소드
	public T next() {
		if(this.find(this.pattern))
			return this.nexter.apply(this.matcher.group(0));

		return null;
	}

	// 찾은 문자열을 return 해주는 메소드
	public Integer nextInt() {
		if(this.find(this.integerPattern))
			return Integer.valueOf(this.matcher.group(0));

		return null;
	}

	@Override
	public void close() throws IOException {
		this.reader.close();
	}
}

