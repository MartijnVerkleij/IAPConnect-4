package nl.utwente.iapc;

public class Game {

	private Player[] players;
	private Board board;
	
	public Game(Player[] argPlayers) {
		players = argPlayers;
		board = new Board(players.length);
		
	}
	
	

}
