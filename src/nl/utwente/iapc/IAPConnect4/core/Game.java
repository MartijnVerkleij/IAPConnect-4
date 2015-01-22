package nl.utwente.iapc.IAPConnect4.core;

import java.util.ArrayList;

import nl.utwente.iapc.IAPConnect4.core.game.InvalidMoveException;
import nl.utwente.iapc.IAPConnect4.core.game.NetworkPlayer;
import nl.utwente.iapc.IAPConnect4.core.game.Player;
import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;
import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;

public class Game extends Thread{

	private ArrayList<Player> players;
	private BoardModel board;
	private int playerToMove; 
	
	public Game(Player player1, Player player2) {
		players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		board = new BoardModel();
		playerToMove = 1;
	}
	
	public void run() {
		while(board.getWinner() == 0) {
			try {
				Player player = players.get(playerToMove - 1);
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
				System.err.println("ERROR: Invalid move done");
				System.err.println("Here's your board " + e.getBoard().toString());
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
			BoardModel newBoard = board.move(move, playerToMove);
			board = newBoard;
			playerToMove = 1 + ((playerToMove) % players.size());
		} else {
			throw new InvalidMoveException(player, board);
		}
	}
}
