package nl.utwente.iapc.IAPConnect4.client.game;

import nl.utwente.iapc.IAPConnect4.core.game.Board;
import nl.utwente.iapc.IAPConnect4.server.Game;

public class WinningComputer extends ComputerPlayer {

	RandomComputer backup;
	
	public WinningComputer(String name) {
		super(name);
		backup = new RandomComputer("backup");
	}
	
	@Override
	public int nextMove(Board board) {
		// TODO
		int determinedMove = 0;
		// First move in center
		if (board.getField(3, 5) == 0) {
			determinedMove = 3;
		} else if (board.isLegalMove(3)) {
			determinedMove = 3;
		} else if (board.isLegalMove(6)) {
			determinedMove = 6;
		} else { 
			determinedMove = backup.nextMove(board);
		}
		
		return determinedMove;
	}

	@Override
	public void doMove(int move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGame(Game game) {
		// TODO Auto-generated method stub
		
	}
	
}
