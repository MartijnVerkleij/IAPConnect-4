package nl.utwente.iapc.IAPConnect4.model;

import nl.utwente.iapc.IAPConnect4.exception.InvalidMoveException;

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
	
	public Board move(int move, Player player) {
		if (isLegalMove(move))
			{
				//TODO Update current player
				board[move][board[move].length - getColumnSize(move)] = player.getPlayerNumber();
				Board newBoard = new Board(board, currentPlayer, playerCount);
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
	
	public String toString() {
		String returnString = ".-.-.-.-.-.-.-.\n";
		returnString += "|"+board[0][0]+"|"+board[0][1]+"|"+board[0][2]+"|"+board[0][3]+"|"+board[0][4]+"|"+board[0][5]+"|"+board[0][6]+"|\n";
		returnString += "|"+board[1][0]+"|"+board[1][1]+"|"+board[1][2]+"|"+board[1][3]+"|"+board[1][4]+"|"+board[1][5]+"|"+board[1][6]+"|\n";
		returnString += "|"+board[2][0]+"|"+board[2][1]+"|"+board[2][2]+"|"+board[2][3]+"|"+board[2][4]+"|"+board[2][5]+"|"+board[2][6]+"|\n";
		returnString += "|"+board[3][0]+"|"+board[3][1]+"|"+board[3][2]+"|"+board[3][3]+"|"+board[3][4]+"|"+board[3][5]+"|"+board[3][6]+"|\n";
		returnString += "|"+board[4][0]+"|"+board[4][1]+"|"+board[4][2]+"|"+board[4][3]+"|"+board[4][4]+"|"+board[4][5]+"|"+board[4][6]+"|\n";
		returnString += "|"+board[5][0]+"|"+board[5][1]+"|"+board[5][2]+"|"+board[5][3]+"|"+board[5][4]+"|"+board[5][5]+"|"+board[5][6]+"|\n";
		returnString += "'-'-'-'-'-'-'-'\n"; 
		return returnString;
	}
}
