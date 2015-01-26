package nl.utwente.iapc.IAPConnect4.client.game;

import java.util.Random;
import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;

public class RandomComputer extends ComputerPlayer {

	public RandomComputer(String name) {
		super(name);
	}
	
	
	public int nextMove (BoardModel board) {
		// TODO
		Random rand = new Random();
	    int determinedMove = rand.nextInt((board.getBoardWidth()) + 1);
		return determinedMove;
	}
	
}
