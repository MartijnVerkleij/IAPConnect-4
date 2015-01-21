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
		
		start();
	}
	
	private void start() {
		while(board.getWinner() == 0) {
			try {
				doMove(players.get(playerToMove),players.get(playerToMove).nextMove(board));
			} catch (InvalidMoveException e) {
				e.printStackTrace();
				System.err.println("ERROR: Invalid move done by: " + e.getPlayer().getName());
			}
		}
		
		for(Player p : players) {
			p.result(players.get(board.getWinner()));
		}
	}
	
	
	
	private void doMove(Player player, int move) throws InvalidMoveException {
		if(players.indexOf(player) == playerToMove) {
			board.move(move, playerToMove);
			playerToMove = (playerToMove + 1) % players.size();
		} else {
			throw new InvalidMoveException(player);
		}
	}
	
	public Board getBoard() {
		return board;
	}
	
	
}
