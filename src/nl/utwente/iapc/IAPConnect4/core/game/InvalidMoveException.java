package nl.utwente.iapc.IAPConnect4.core.game;

public class InvalidMoveException extends Exception {

	private static final long serialVersionUID = -8593386180456593769L;
	Player player;
	int playerInt;
	Board brd;
	
	public InvalidMoveException(Player playerArg, Board board) {
		this.player = playerArg;
		this.brd = board;
	}
	public InvalidMoveException(Board board) {
		this.player = null;
		this.brd = board;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Board getBoard() {
		return brd;
	}

}
