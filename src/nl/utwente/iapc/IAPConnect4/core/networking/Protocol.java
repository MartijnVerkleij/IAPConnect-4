package nl.utwente.iapc.IAPConnect4.core.networking;

public enum Protocol {
	JOIN, READY, DO_MOVE, ERROR, ACCEPT, START_GAME, REQUEST_MOVE, DONE_MOVE, GAME_END;
	
	public String toString() {
		return this.name().toLowerCase();
	}
	public static String[] getAllCommands() {
		String[] rString = new String[Protocol.values().length];
		for (int i = 0; i < Protocol.values().length; i++) {
			rString[i] = Protocol.values()[i].toString();
		}
		return rString;
	}
	
	
}
