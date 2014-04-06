package parse;

public enum Type {
	LEGI(0), SPAM(1);
	
	int i;
	
	private Type(int i) {
		this.i = i;
	}
	
	public int getValue() {
		return i;
	}
	
	public String toString() {
		String toReturn;
		switch(this.i) {
		case 0: toReturn = "LEGI"; break;
		default: toReturn = "SPAM"; break;
		}
		return toReturn;
	}
}
