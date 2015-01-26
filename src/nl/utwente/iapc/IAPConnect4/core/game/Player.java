package nl.utwente.iapc.IAPConnect4.core.game;

import nl.utwente.iapc.IAPConnect4.core.Game;


/**
 * Interface describing a Player playing the Connect 4 game.
 * @author Martijn Verkleij
 *
 */

public interface Player {
	
	/**
	 * Do a move on the given Game.
	 * @return Move object with column to do a move in.
	 */
	public void doMove(int move);
	
	/**
	 * Returns the Player name.
	 * @return player name.
	 */
	public String getName();
	
	/**
	 * Notify the Player of the winner of a Game.
	 * @param player Player that won the game
	 */
	public void result(Player player);
	
	/**
	 * Adds the Game object to the Player.
	 *  @param game Game to add
	 */
	
	public void addGame(Game game);
}
