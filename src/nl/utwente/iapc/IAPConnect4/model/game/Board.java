package nl.utwente.iapc.IAPConnect4.model.game;

import nl.utwente.iapc.IAPConnect4.exception.InvalidMoveException;

/**
 * A connect-4 board with variable width and height.
 * @author Martijn Verkleij
 *
 */
public class Board {

	public static final int BOARDHEIGHT = 6;
	public static final int BOARDWIDTH = 7;
	public static final int WINLENGTH = 4;
	
	private final int playerCount;
	
	/* TODO Optimisation code
	private int[] columnStoneCount;
	*/
	private final int[][] board;
	private final int[] lastMove;
	
	/**
	 * Create a Connect 4 Board with a size of <code>BOARDHEIGHT</code>*<code>BOARDWIDTH</code>.
	 * @param numberOfPlayers Number of players
	 */
	public Board(int playerCount) {
		board = new int[BOARDWIDTH][BOARDHEIGHT];
		this.playerCount = playerCount;
		lastMove = null;
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
		lastMove = null;
	}
	
	/**
	 * Protected constructor, used to advance in or end the game. 
	 * @param board
	 * @param currentPlayer
	 * @param playerCount
	 */
	protected Board(int[][] board, int[] lastMove, int playerCount) {
		this.board = board;
		this.playerCount = playerCount;
		this.lastMove = lastMove;
	}
	
	/**
	 * Do a move. Returns a new Board or TODO .
	 * @param move Move to be done, integer between 0 and board.length - 1.
	 * @param player Player that does the move
	 * @return Board A new Board.
	 */
	public Board move(int move, int player) throws InvalidMoveException {
		if (isLegalMove(move))
			{
				//TODO Update current player
				int[] moveDone = new int[] {move, board[move].length - getColumnSize(move)};
				board[moveDone[0]][moveDone[1]] = player;
				Board newBoard = new Board(board, moveDone, playerCount);
				return newBoard;
			} else {
				throw new InvalidMoveException(player);
			}
	}
	
	/**
	 * Check if the suggested Move is valid.
	 * @param move Move to be made
	 * @return Validity of the move
	 */
	
	public boolean isLegalMove(int move) {
		boolean legal = false;
		// TODO move player check to Game
		if (getField(move,0) == 0) {
			legal = true;
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
	 * Returns the amount of currently unused fields in a column.
	 * @param x Column
	 * @return size of a column
	 */
	public int getColumnSize (int x){
		int size = 0;
		for (size = 0; size < board[x].length; size++)
		{
		    if (getField(x,size) > 0)
		    	break;
		}
		return size;
	}
	
	/**
	 * Returns the winning player number, if there is one. 0 is not 
	 * a player number, so will be returned when there is no winner.
	 * @return player number or 0.
	 */
	public int getWinner() {
		int winner = 0;
		
		int x;
		int y;
		//check horizontal
		y = lastMove[1];
			int recurrence = 0; 
			for (x = 1; x < getBoardWidth(); x++) {
				if (board[x-1][y] == board[x][y]) {
					recurrence++;
				} else {
					recurrence = 0;
				}
				if (recurrence >= WINLENGTH) {
					winner = board[x][y];
				}
			}
		//check vertical
		x = lastMove[0];
			recurrence = 0;
			for (y = 1; y < getBoardHeight(); y++) {
				if (board[x][y-1] == board[x][y]) {
					recurrence++;
				} else {
					recurrence = 0;
				}
				if (recurrence >= WINLENGTH) {
					winner = board[x][y];
				}
			}
		x = lastMove[0];
		y = lastMove[1];
		//check diagonal upperleft -> downright
		while((x > 1) && (y > 1)) {
			x--; y--;
		}
		recurrence = 0;
		while((x < getBoardWidth()) && (y < getBoardHeight())) {
			if (board[x-1][y-1] == board[x][y]) {
				recurrence++;
			} else {
				recurrence = 0;
			}
			if (recurrence >= WINLENGTH) {
				winner = board[x][y];
			}
			x++; y++;
		}
		
		//check diagonal downleft -> upperright
		
		while((x > 1) && (y < getBoardHeight() - 1)) {
			x--; y++;
		}
		recurrence = 0;
		while((x < getBoardWidth()) && (y >= 0)) {
			if (board[x-1][y+1] == board[x][y]) {
				recurrence++;
			} else {
				recurrence = 0;
			}
			if (recurrence >= WINLENGTH) {
				winner = board[x][y];
			}
			x++; y--;
		}
		return winner;
	}
	
	public int getBoardWidth() {
		return board.length;
	}
	
	public int getBoardHeight() {
		return board[0].length;
	}
	
	public String toString() {
		String returnString = ".-.-.-.-.-.-.-.\n";
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				returnString += "|"+getField(i, j);
			}
			returnString += "|\n";
		}
		returnString += "'-'-'-'-'-'-'-'\n"; 
		return returnString;
	}
}
