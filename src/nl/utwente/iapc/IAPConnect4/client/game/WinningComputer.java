package nl.utwente.iapc.IAPConnect4.client.game;

import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;

public class WinningComputer extends ComputerPlayer {

	public WinningComputer(String name) {
		super(name);
	}
	
	@Override
	public int nextMove (BoardModel board) {
		// TODO
		int determinedMove = 0;
		// First move in center
		if (board.getField(3, 5) == 0)
			determinedMove = 3;
		// else if(true)
			
		return determinedMove;
	}
	
}
