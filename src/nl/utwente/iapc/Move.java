package nl.utwente.iapc;

/**
 * A simple class for a move done on a Board. It contains the coordinates of the move and the number of the player that did it.
 * @author Martijn Verkleij
 *
 */

public class Move {

	private int player;
	private int column;
	
	/**
	 * A move done on a Board, containing coordinates and the player that the move is for.
	 * @param playerArg Player that did the move
	 * @param xArg width index of the board
	 * @param yArg height index of the board
	 */
	
	public Move(int player, int column) {
		this.player = player;
		this.column = column;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getPlayer() {
		return player;
	}
	
	

}
