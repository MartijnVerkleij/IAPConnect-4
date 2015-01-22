package nl.utwente.iapc.IAPConnect4.controller;

import java.util.ArrayList;

import nl.utwente.iapc.IAPConnect4.exception.InvalidMoveException;
import nl.utwente.iapc.IAPConnect4.model.game.Board;
import nl.utwente.iapc.IAPConnect4.model.game.NetworkPlayer;
import nl.utwente.iapc.IAPConnect4.model.game.Player;
import nl.utwente.iapc.IAPConnect4.model.networking.Command;
import nl.utwente.iapc.IAPConnect4.util.Protocol;

public class Game {

	private ArrayList<Player> players;
	private Board board;
	private int playerToMove; 
	
	public Game(Player player1, Player player2) {
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		board = new Board();
		playerToMove = 1;
	}
	
	public void start() {
		while(board.getWinner() == 0) {
			try {
				Player player = players.get(playerToMove);
				int move = player.nextMove(board);
				doMove(player,move);
				for(Player p : players) {
					if(p instanceof NetworkPlayer) {
						((NetworkPlayer) p).sendCommand(new Command(Protocol.DONE_MOVE, player.getName(), "" + move));
					}
				}
				System.out.println(board.toString());
			} catch (InvalidMoveException e) {
				e.printStackTrace();
				System.err.println("Here's your board " + e.getBoard().toString());
				System.err.println("ERROR: Invalid move done by: " + e.getPlayer().getName());
			}
			finally {
			}
		}
		
		for(Player p : players) {
			if (board.getWinner() >= 0) {
				// TODO Implement draws (-1)
				p.result((players.get(board.getWinner()-1)));
			} else {
				p.result(null);
			}
		}
		playerToMove = -1; 
	}
	
	
	
	private void doMove(Player player, int move) throws InvalidMoveException {
		if(players.indexOf(player) + 1 == playerToMove) {
			Board newBoard = board.move(move, playerToMove);
			board = newBoard;
			playerToMove = 1 + ((playerToMove + 1) % players.size());
		} else {
			throw new InvalidMoveException(player, board);
		}
	}
	
	//public Board getBoard() {
	//	return board;
	//}
	
	
}
