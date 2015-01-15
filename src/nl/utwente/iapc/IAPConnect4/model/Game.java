package nl.utwente.iapc.IAPConnect4.model;

import java.util.ArrayList;

import nl.utwente.iapc.IAPConnect4.exception.InvalidMoveException;

public class Game {

	private ArrayList<Player> players;
	private Board board;
	private int playerToMove; 
	
	public Game(Player player1, Player player2) {
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		board = new Board(players.size());
		playerToMove = 0;
	}
	
	public void start() {
		
	}
	
	public void doMove(Player player, int move) throws InvalidMoveException {
		if(players.indexOf(player) == playerToMove) {
			board.move(move, playerToMove);
			playerToMove++;
		} else {
			throw new InvalidMoveException();
		}
	}
	
	public Board getBoard() {
		return board;
	}
	
	
}
