package nl.utwente.iapc.IAPConnect4.core.game;

/**
 * A connect-4 board with variable width and height.
 * @author Martijn Verkleij & Axel Vugts
 *
 */
public class Board {

	public static final int BOARDHEIGHT = 6;
	public static final int BOARDWIDTH = 7;
	public static final int WINLENGTH = 4;
		
	private final int[][] board;
	private final int[] lastMove;
	
	/**
	 * Create a Connect 4 Board with a size of <code>BOARDHEIGHT</code>*<code>BOARDWIDTH</code>.
	 * @param numberOfPlayers Number of players
	 */
	public Board() {
		board = new int[BOARDWIDTH][BOARDHEIGHT];
		lastMove = new int[] {0, 0};
	}
	
	/**
	 * Create a Connect 4 Board with a variable Board size.
	 * @param numberOfPlayers Number of players
	 * @param boardWidth Width of the Board
	 * @param boardHeight Height of the Board
	 */
	public Board(int boardWidth, int boardHeight) {
		board = new int[boardWidth][boardHeight];
		lastMove = new int[] {0, 0};
	}
	
	/**
	 * Protected constructor, used to advance in or end the game. 
	 * @param boardArg
	 * @param currentPlayer
	 * @param playerCount
	 */
	protected Board(int[][] boardArg, int moveDone) {
		this.board = boardArg;
		if (this.getEmptyFields(moveDone) == 0) {
			this.lastMove = new int[] {moveDone, this.getEmptyFields(moveDone)};
		} else {
			this.lastMove = new int[] {moveDone, this.getEmptyFields(moveDone)};
		}
	}
	
	/**
	 * Do a move. Returns a new Board.
	 * @param move Move to be done, integer between 0 and board.length - 1.
	 * @param player Player that does the move
	 * @return Board A new Board.
	 */
	public Board move(int move, int player) throws InvalidMoveException {
		if (isLegalMove(move)) {
			System.out.println("moveDone: [" + move + "," + (getEmptyFields(move) - 1) 
							+ "] by " + player);
			board[move][getEmptyFields(move) - 1] = player;
			Board newBoard = new Board(board, move);
			return newBoard;
		} else {
			throw new InvalidMoveException(this);
		}
	}
	
	/**
	 * Check if the suggested Move is valid.
	 * @param move Move to be made
	 * @return Validity of the move
	 */
	
	public boolean isLegalMove(int move) {
		boolean legal = false;
		if (getField(move, 0) == 0) {
			legal = true;
		}
		return legal;
	}
	/**
	 * Returns the current state of a field. -1 if invalid index.
	 * @param x x-position
	 * @param y y-position
	 * @return 0 for empty field, or currentPlayer
	 */
	
	public int getField(int x, int y) {
		int field = -1;
		if (x >= 0 && x <= getBoardWidth() - 1 && y >= 0 && y <= getBoardHeight() - 1) {
			field = board[x][y];
		}
		return field;
	}
	
	/**
	 * Returns the amount of currently unused fields in a column.
	 * @param column Column
	 * @return size of a column
	 */
	public int getEmptyFields(int column) {
		int size = 0;
		for (size = 0; size < BOARDHEIGHT; size++) {
		    if (getField(column, size) > 0) {
		    	break;
		    }
		    	
		} 
		return size;
	}
	
	/**
	 * Is the board full?
	 * @return true for a full board, or false if not
	 */
	public boolean isFull() {
		boolean full = false;
		int column = 0;
		while (column < BOARDWIDTH) {
			full = full && (getEmptyFields(column) == 0);
			column++;
		}
		return full;
	}
	
	/**
	 * Returns how many consecutive fields of a given player are found in a certain direction.
	 * @param x Base position x
	 * @param y Base position y
	 * @param dx direction in x-axis
	 * @param dy direction in y-axis
	 * @param player player to look for.
	 * @return number of consecutive fields from Player player.
	 */
	
	private int count(int x, int y, int dx, int dy, int player) {
		if (getField(x, y) == player) {
			return 1 + count(x + dx, y + dy, dx, dy, player);
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns the winning player number, if there is one. 0 is not 
	 * a player number, so will be returned when there is no winner. 
	 * -1 is draw
	 * @return player number, 0, or -1.
	 */
	public int getWinner() {
		int winner = 0;
		int[][] directions = {new int[]{0, 1}, new int[]{1, 0}, new int[]{1, 1}, new int[]{-1, 1}};
		
		int x = lastMove[0];
		int y = lastMove[1];
		int field = getField(x, y);

		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			
			if (count(x, y, dx, dy, field) + 
							count(x - dx, y - dy, -dx, -dy, field) > 3) {
				winner = field;
			}
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
		for (int y = 0; y < BOARDHEIGHT; y++) {
			for (int x = 0; x < BOARDWIDTH; x++) {
				String field = (getField(x, y) != 0) ? "" + getField(x, y) :  " ";
				returnString += "|" + field;
			}
			returnString += "|\n";
		}
		returnString += "'-'-'-'-'-'-'-'\n"; 
		return returnString;
	}
}
