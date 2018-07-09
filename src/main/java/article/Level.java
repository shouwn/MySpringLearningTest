package article;

public enum Level {
	NEW(1), COMMON(2), POPULAR(3);
	
	private final int value;
	
	private Level(int value){
		this.value = value;
	}
	
	public int intValue() { return value; }
	
	public static Level valueOf(int value) {
		switch(value) {
		case 1: return NEW;
		case 2: return COMMON;
		case 3: return POPULAR;
		default: throw new AssertionError("Unknown value: " + value);
		}
	}
}
