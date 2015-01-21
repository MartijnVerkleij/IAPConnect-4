package nl.utwente.iapc.IAPConnect4.exception;

import nl.utwente.iapc.IAPConnect4.model.game.Player;

public class InvalidMoveException extends Exception {

	Player player;
	int playerInt;
	
	public InvalidMoveException(Player player) {
		this.player = player;
	}
	
	public InvalidMoveException(int player) {
		this.playerInt = player;
	}
	
	public Player getPlayer() {
		return player;
	}

}
