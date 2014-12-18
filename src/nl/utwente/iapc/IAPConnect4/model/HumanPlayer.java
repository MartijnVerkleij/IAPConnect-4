package nl.utwente.iapc.IAPConnect4.model;

public class HumanPlayer implements Player {

	/**
	 * A human player with a Name.
	 * @author Axel Vugts
	 *
	 */
	
	private String name;
	private int number;
	
	/**
	 * A Human Player
	 * @param name Name for the new human player
	 * @param number Number of the new player
	 */
	public HumanPlayer (String name, int number) {
		this.name = name;
		this.number = number;
	}
	
	@Override
	public Move nextMove() {
		// TODO
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getPlayerNumber() {
		return number;
	}

}
