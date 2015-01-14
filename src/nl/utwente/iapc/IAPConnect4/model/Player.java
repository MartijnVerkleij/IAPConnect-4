package nl.utwente.iapc.IAPConnect4.model;

/**
 * Interface describing a Player playing the Connect 4 game;
 * @author Martijn Verkleij
 *
 */

public interface Player {
	
	/**
	 * Retrieve the desired Move from the Player.
	 * @return Move object with column to do a move in.
	 */
	public int nextMove(Board board);
	
	/**
	 * Returns the Player name;
	 * @return player name.
	 */
	public String getName();
	
	/**
	 * Player number getter. 
	 * @param playerNumber Player numer used by the Board.
	 */
	
	public int getPlayerNumber();
	
	
}
