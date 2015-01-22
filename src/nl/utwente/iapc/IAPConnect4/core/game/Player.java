package nl.utwente.iapc.IAPConnect4.core.game;


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
	public int nextMove(BoardModel board);
	
	/**
	 * Returns the Player name;
	 * @return player name.
	 */
	public String getName();
	
	/**
	 * Notify the Player of the winner of a Game.
	 * @param player Player that won the game
	 */
	public void result(Player player);
	
}
