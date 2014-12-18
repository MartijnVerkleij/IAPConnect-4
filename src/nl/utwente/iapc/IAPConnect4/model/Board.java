package nl.utwente.iapc.IAPConnect4.model;

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
	 * Protected constructor, used to advance in or end the game. 
	 * @param board
	 * @param currentPlayer
	 * @param playerCount
	 */
	
	protected Board(int[][] board, int currentPlayer, int playerCount) {
		this.board = board;
		this.playerCount = playerCount;
		this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Do a move. Returns a new Board or TODO .
	 * @param move move to be done
	 * @return BoardResult, which can be a new Board or a Board that has a winner.
	 */
	
	public Board move(Move move) {
		if (isLegalMove(move))
			{
				//TODO Update current player
				Board newBoard = new Board(board, currentPlayer, playerCount);
				board[move.getColumn()][board[move.getColumn()].length - getColumnSize(move.getColumn())] = move.getPlayer();
				return newBoard;
			} else {
				return null;
			}
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
			if (getField(move.getColumn(),0) == 0) {
				legal = true;
			}
		}
		return legal;
	}
	/**
	 * Returns the current state of a field.
	 * @param x x-position
	 * @param y y-position
	 * @return 0 for empty field, or currentPlayer
	 */
	
	public int getField(int x, int y) {
		return board[x][y];
	}
	
	/**
	 * Returns the amount of currently used fields in a column.
	 * @param x Column
	 * @return size of a column
	 */
	public int getColumnSize (int x){
		int size = 0;
		for (size = 0; size < board[x].length; ++size)
		{
		    if (getField(x,size) > 0)
		    	break;
		}
		return size;
	}
}
