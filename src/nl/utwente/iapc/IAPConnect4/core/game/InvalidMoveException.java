package nl.utwente.iapc.IAPConnect4.core.game;

public class InvalidMoveException extends Exception {

	private static final long serialVersionUID = -8593386180456593769L;
	Player player;
	int playerInt;
	BoardModel brd;
	
	public InvalidMoveException(Player player, BoardModel board) {
		this.player = player;
		this.brd = board;
	}
	public InvalidMoveException(BoardModel board) {
		this.player = null;
		this.brd = board;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public BoardModel getBoard() {
		return brd;
	}

}
