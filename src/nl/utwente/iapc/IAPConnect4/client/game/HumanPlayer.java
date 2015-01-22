package nl.utwente.iapc.IAPConnect4.client.game;

import nl.utwente.iapc.IAPConnect4.core.Game;
import nl.utwente.iapc.IAPConnect4.core.game.InvalidMoveException;
import nl.utwente.iapc.IAPConnect4.core.game.Player;
import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;

public class HumanPlayer implements Player {

	/**
	 * A human player with a Name.
	 * @author Axel Vugts
	 *
	 */
	
	private String name;
	private Game game;
	
	/**
	 * A Human Player
	 * @param name Name for the new human player
	 * @param number Number of the new player
	 */
	public HumanPlayer (String name) {
		this.name = name;
	}
	
	@Override
	public void doMove(int move) {
		try {
			game.doMove(this, move);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void result(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGame(Game game) {
		// TODO Auto-generated method stub
		
	}

}
