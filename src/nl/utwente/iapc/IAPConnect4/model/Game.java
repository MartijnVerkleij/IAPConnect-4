package nl.utwente.iapc.IAPConnect4.model;

public class Game {

	private Player[] players;
	private Board board;
	
	public Game(Player[] argPlayers) {
		players = argPlayers;
		board = new Board(players.length);
		
	}
	
	

}