package nl.utwente.iapc;

/**
 * A connect-4 board with variable width and height.
 * @author Martijn Verkleij
 *
 */
public class Board {

	public static final int BOARDHEIGHT = 6;
	public static final int BOARDWIDTH = 7;
	
	/* TODO Optimisation code
	private int[] columnStoneCount;
	*/
	private int[][] board;
	
	
	
	/**
	 * A Connect 4 Board with a size of <code>BOARDHEIGHT</code>*<code>BOARDWIDTH</code>.
	 * @param numberOfPlayers Number of players
	 */
	public Board(int numberOfPlayers) {
		board = new int[BOARDWIDTH][BOARDHEIGHT];
		/* TODO Optimisation code
		columnStoneCount = new int[BOARDWIDTH];
		for (int i : columnStoneCount) {
			columnStoneCount[i] = 0;
		}
		*/
		
		
	}
	
	/**
	 * A Connect 4 Board with a variable Board size.
	 * @param numberOfPlayers Number of players
	 * @param boardWidth Width of the Board
	 * @param boardHeight Height of the Board
	 */
	public Board(int numberOfPlayers, int boardWidth, int boardHeight) {
		
	}
	
	
	public boolean move(Move move) {
		return false; //TODO implement function
	}
	
	

}
