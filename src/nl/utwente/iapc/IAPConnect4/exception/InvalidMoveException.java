package nl.utwente.iapc.IAPConnect4.exception;

import nl.utwente.iapc.IAPConnect4.model.Player;

public class InvalidMoveException extends Exception {

	Player player;
	
	public InvalidMoveException(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}

}
