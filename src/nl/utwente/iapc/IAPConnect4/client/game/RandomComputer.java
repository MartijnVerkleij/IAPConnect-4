package nl.utwente.iapc.IAPConnect4.client.game;

import java.util.Random;

import nl.utwente.iapc.IAPConnect4.core.game.Board;

public class RandomComputer extends ComputerPlayer {

	public RandomComputer(String name) {
		super(name);
	}
	
	
	@Override
	public int nextMove(Board board) {
		Random rand = new Random();
		int determinedMove = -1;
		while (!board.isLegalMove(determinedMove)) {
			determinedMove = rand.nextInt((board.getBoardWidth()) + 1);
		}
		return determinedMove;
	}


	@Override
	public void doMove(int move) {
		
	}
	
}
