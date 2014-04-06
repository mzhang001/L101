package parse;

public enum State {
	BLANK(0), BODY(1),FROM(2),SUBJECT(3), EMBEDDED(4), BODYNORMAL(5);
	int i;
	private State(int i) {
		this.i = i;
	}
	
}
