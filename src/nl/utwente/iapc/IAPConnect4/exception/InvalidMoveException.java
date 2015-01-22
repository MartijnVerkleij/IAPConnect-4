package nl.utwente.iapc.IAPConnect4.exception;

import nl.utwente.iapc.IAPConnect4.model.game.Player;
import nl.utwente.iapc.IAPConnect4.model.game.Board;

public class InvalidMoveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8593386180456593769L;
	Player player;
	int playerInt;
	Board brd;
	
	public InvalidMoveException(Player player, Board board) {
		this.player = player;
		this.brd = board;
	}
	
	public InvalidMoveException(int player) {
		this.playerInt = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	public Board getBoard() {
		return brd;
	}

}
