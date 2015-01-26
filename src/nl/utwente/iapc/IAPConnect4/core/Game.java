package nl.utwente.iapc.IAPConnect4.core;

import java.util.ArrayList;

import nl.utwente.iapc.IAPConnect4.core.game.InvalidMoveException;
import nl.utwente.iapc.IAPConnect4.core.game.Player;
import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;
import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;
import nl.utwente.iapc.IAPConnect4.server.ServerPlayer;

public class Game  {

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
	
	public void startGame() {
		notifyMove();
	}
	
	public void doMove(Player player, int move) throws InvalidMoveException {
		if (players.indexOf(player) + 1 == playerToMove) {
			BoardModel newBoard = board.move(move, playerToMove);
			board = newBoard;
			playerToMove = 1 + (playerToMove % players.size());
			
			for (Player p : players) {
				if (p instanceof ServerPlayer) {
					((ServerPlayer) p).sendCommand(new Command(Protocol.DONE_MOVE, 
									player.getName(), "" + move));
				}
			}
			
			if (board.getWinner() > 0) {
				handleWin();
			} else {
				notifyMove();
			}
			
			System.out.println(board.toString());
		} else {
			throw new InvalidMoveException(player, board);
		}
	}
	
	public void notifyMove() {
		for (Player p : players) {
			if (p instanceof ServerPlayer) {
				((ServerPlayer) p).sendCommand(new Command(Protocol.REQUEST_MOVE, 
								players.get(playerToMove - 1).getName()));
			}
		}
	}
	
	public void handleWin() {
		for (Player p : players) {
			if (board.getWinner() > 0) {
				p.result(players.get(board.getWinner() - 1));
			} else {
				p.result(null);
			}
		}
		playerToMove = -1; 
	}
	
}
