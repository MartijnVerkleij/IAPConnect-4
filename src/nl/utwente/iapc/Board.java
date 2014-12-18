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
	private final short[][] board;
	
	
	
	
	/**
	 * Create a Connect 4 Board with a size of <code>BOARDHEIGHT</code>*<code>BOARDWIDTH</code>.
	 * @param numberOfPlayers Number of players
	 */
	public Board(int playerCount) {
		board = new short[BOARDWIDTH][BOARDHEIGHT];
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
	public Board(short playerCount, short boardWidth, short boardHeight) {
		board = new short[boardWidth][boardHeight];
		this.playerCount = playerCount;
		currentPlayer = 1;
	}
	
	/**
	 * Protected constructor, used to advance in or end the game. 
	 * @param board
	 * @param currentPlayer
	 * @param playerCount
	 */
	
	protected Board(short[][] board, short currentPlayer, short playerCount) {
		this.board = board;
		this.playerCount = playerCount;
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Do a move. Returns a new Board or TODO .
	 * @param move move to be done
	 * @return BoardResult, which can be a new Board or a Board that has a winner.
	 */
	
	public BoardResult move(Move move) {
		return null;
	}
	
	/**
	 * Check if the suggested Move is valid.
	 * @param move Move to be made
	 * @return Validity of the move
	 */
	
	public boolean isLegalMove(Move move) {
		boolean legal = false;
		// TODO move player check to Game
		if (move.getPlayer() == currentPlayer) {
			if (board[move.getColumn()][0] == 0) {
				legal = true;
			}
		}
		return legal;
	}
	/**
	 * Returns the current state of a field.
	 * @param x x-position
	 * @param y y-position
	 * @return Current field state
	 */
	
	public short getFieldState(short x, short y) {
		return board[x][y];
	}
	
}
