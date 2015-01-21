package nl.utwente.iapc.IAPConnect4.model;

public class HumanPlayer implements Player {

	/**
	 * A human player with a Name.
	 * @author Axel Vugts
	 *
	 */
	
	private String name;
	
	/**
	 * A Human Player
	 * @param name Name for the new human player
	 * @param number Number of the new player
	 */
	public HumanPlayer (String name) {
		this.name = name;
	}
	
	@Override
	public int nextMove(Board board) {
		// TODO
		return 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void result(Player player) {
		// TODO Auto-generated method stub
		
	}

}
