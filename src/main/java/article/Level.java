package article;

public enum Level {
	POPULAR(3, null), COMMON(2, POPULAR), NEW(1, COMMON);
	
	private final int value;
	private final Level next;
	
	private Level(int value, Level next){
		this.value = value;
		this.next = next;
	}
	
	public int intValue() { return value; }
	
	public Level nextLevel() {
		return this.next;
	}
	
	public static Level valueOf(int value) {
		switch(value) {
		case 1: return NEW;
		case 2: return COMMON;
		case 3: return POPULAR;
		default: throw new AssertionError("Unknown value: " + value);
		}
	}
}
