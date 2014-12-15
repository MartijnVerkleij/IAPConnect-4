package nl.utwente.iapc;

/**
 * A connect-4 board with variable width and height.
 * @author Martijn Verkleij
 *
 */
public class Board {

	public static final int BOARDHEIGHT = 6;
	public static final int BOARDWIDTH = 7;
	
	private final int currentPlayer;
	private final int playerCount;
	
	/* TODO Optimisation code
	private int[] columnStoneCount;
	*/
	private final int[][] board;
	
	
	
	/**
	 * Create a Connect 4 Board with a size of <code>BOARDHEIGHT</code>*<code>BOARDWIDTH</code>.
	 * @param numberOfPlayers Number of players
	 */
	public Board(int playerCount) {
		board = new int[BOARDWIDTH][BOARDHEIGHT];
		this.playerCount = playerCount;
		currentPlayer = 1;
		/* TODO Optimisation code
		columnStoneCount = new int[BOARDWIDTH];
		for (int i : columnStoneCount) {
			columnStoneCount[i] = 0;
		}
		*/
	}
	
	/**
	 * Create a Connect 4 Board with a variable Board size.
	 * @param numberOfPlayers Number of players
	 * @param boardWidth Width of the Board
	 * @param boardHeight Height of the Board
	 */
	public Board(int playerCount, int boardWidth, int boardHeight) {
		board = new int[boardWidth][boardHeight];
		this.playerCount = playerCount;
		currentPlayer = 1;
	}
	
	/**
	 * Private constructor, used to advanced in the game. 
	 * @param board
	 * @param currentPlayer
	 * @param playerCount
	 */
	
	private Board(int[][] board, int currentPlayer, int playerCount) {
		this.board = board;
		this.playerCount = playerCount;
		this.currentPlayer = currentPlayer;
	}
	
	public BoardResult move(Move move) {
		return null; 
	}
	
	

}
