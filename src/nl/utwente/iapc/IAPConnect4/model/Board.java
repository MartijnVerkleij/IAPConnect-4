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
	 * @return BoardResult, which can be a new Board or a Board that has a winner.
	 */
	
	public Board move(int move, Player player) {
		if (isLegalMove(move))
			{
				//TODO Update current player
				int[] moveDone = new int[] {move, board[move].length - getColumnSize(move)};
				board[moveDone[0]][moveDone[1]] = player.getPlayerNumber();
				Board newBoard = new Board(board, moveDone, playerCount);
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
	
	public int getWinner() {
		int winner = 0;
		//vertical check
		int countFichesInRow = 0;
		for (int x = 0; x < BOARDWIDTH; x++) {
			for (int y = 1; y < BOARDHEIGHT; y++) {
				if (board[x][y] == board[x][y - 1]) {
					countFichesInRow++;
				} else {
					countFichesInRow = 0;
				}
				if (countFichesInRow >= WINLENGTH - 1) {
					winner = board[x][y];
				}
			}
		}
		//horizontal check
		for (int y = 0; y < BOARDWIDTH; y++) {
			for (int x = 1; x < BOARDHEIGHT; x++) {
				if (board[x][y] == board[x][y - 1]) {
					countFichesInRow++;
				} else {
					countFichesInRow = 0;
				}
				if (countFichesInRow >= WINLENGTH - 1) {
					winner = board[x][y];
				}
			}
		}
		
		/* diagonal check:
		 *  
		 *    |***╱╱╱╱|
		 *    |**╱╱╱╱╱|
		 *    |*╱╱╱╱╱╱|
		 * 0,3|╱╱╱╱╱╱*|
		 * 0,4|╱╱╱╱╱**|
		 * 0,5|╱╱╱╱***|
		 * ------------
		 *      |\\
		 *      | \\
		 *     1,5 \\
		 *         | \
		 *        2,5 \
		 *           3,5  
		 */
		
		//int[][] startPos = new int[][] {new int[] {0,3}, new int[] {0,4},new int[] {0,5},new int[] {1,5},new int[] {2,5},new int[] {3,5}};
		
		// List all fields in left and lower edge
		
		int[][] startPositions = new int[getBoardWidth() + getBoardHeight() - 1][2];
		for (int i = 0 ; i < getBoardHeight(); i++) {
			startPositions[i] = new int[]{0,i};
		}
		for (int j = 1 ; j < getBoardWidth(); j++) {
			startPositions[j - 2 + getBoardHeight()] = new int[]{getBoardHeight() - 1,j};
		}
		
		//check for more than 4 fields of a player in a row.
		
		for (int[] coordinate : startPositions) {
			int x = coordinate[0] + 1;
			int y = coordinate[1] + 1;
			while (x < getBoardWidth() && y < getBoardHeight()) {
				if (board[x][y] == board[x - 1][y - 1]) {
					countFichesInRow++;
				} else {
					countFichesInRow = 0;
				}
				if (countFichesInRow >= WINLENGTH - 1) {
					winner = board[x][y];
				}
				x++;
				y++;
			}
			countFichesInRow = 0;
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
