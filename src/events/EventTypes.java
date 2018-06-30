package events;

public enum EventTypes {
	
	FROM_SOURCE(0),
	FROM_WEB(1),
	FROM_MOBILE(2),
	FROM_DB(3);
	
	public final int value;
	
	private EventTypes(int value) {
		this.value = value;
	}

}
