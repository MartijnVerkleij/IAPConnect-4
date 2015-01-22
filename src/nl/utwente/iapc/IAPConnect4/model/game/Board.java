package nl.utwente.iapc.IAPConnect4.model.game;

import java.util.Arrays;

import nl.utwente.iapc.IAPConnect4.exception.InvalidMoveException;

/**
 * A connect-4 board with variable width and height.
 * @author Martijn Verkleij & Axel Vugts
 *
 */
public class Board {

	public static final int BOARDHEIGHT = 6;
	public static final int BOARDWIDTH = 7;
	public static final int WINLENGTH = 4;
		
	/* TODO Optimisation code
	private int[] columnStoneCount;
	*/
	private final int[][] board;
	private final int[] lastMove;
	
	/**
	 * Create a Connect 4 Board with a size of <code>BOARDHEIGHT</code>*<code>BOARDWIDTH</code>.
	 * @param numberOfPlayers Number of players
	 */
	public Board() {
		board = new int[BOARDWIDTH][BOARDHEIGHT];
		lastMove = new int[] {0,0};
	}
	
	/**
	 * Create a Connect 4 Board with a variable Board size.
	 * @param numberOfPlayers Number of players
	 * @param boardWidth Width of the Board
	 * @param boardHeight Height of the Board
	 */
	public Board(int boardWidth, int boardHeight) {
		board = new int[boardWidth][boardHeight];
		lastMove = new int[] {0,0};
	}
	
	/**
	 * Protected constructor, used to advance in or end the game. 
	 * @param board
	 * @param currentPlayer
	 * @param playerCount
	 */
	protected Board(int[][] board, int moveDone) {
		this.board = board;
		this.lastMove = new int[] {moveDone,(this.getEmptyFields(moveDone)-1)};
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
				int moveDone = move;
				System.out.println("moveDone: ["+move+","+(getEmptyFields(move)-1)+"]");
				board[move][(getEmptyFields(move)-1)] = (player);
				Board newBoard = new Board(board, moveDone);
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
	 * @param column Column
	 * @return size of a column
	 */
	public int getEmptyFields (int column){
		int size = 0;
		for (size = 0; size < BOARDHEIGHT; size++)
		{
		    if (getField(column,size) > 0)
		    	break;
		} 
		return size;
	}
	
	/**
	 * Is the board full?
	 * @return true for a full board, or false if not
	 */
	public boolean isFull (){
		boolean full = false;
		int column = 0;
		// TODO: check < or <=
		while (column < BOARDWIDTH) 
		{
			full = full && (getEmptyFields(column) == 0);
			column++;
		}
		return full;
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
		// TODO: For debug
		System.out.println("lastMove: " + Arrays.toString(lastMove));
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
		while((x < 1) || (y < 1)) {
			x++; y++;
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
		
		//x = lastMove[0];
		//y = lastMove[1];
		//check diagonal downleft -> upperright
		
		while((x > 1) && (y < getBoardHeight() - 2)) {
			x--; y++;
		}
		while((x < 1) || (y > getBoardHeight() - 2)) {
			x++; y--;
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
		// check for draw
		if (winner == 0 && isFull()) {
			winner = -1;
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
		for (int i = 0; i < (BOARDHEIGHT); i++)
		{
			for (int j = 0; j < (BOARDWIDTH); j++)
			{
				returnString += "|"+getField(j, i);
			}
			returnString += "|\n";
		}
		returnString += "'-'-'-'-'-'-'-'\n"; 
		return returnString;
	}
}
