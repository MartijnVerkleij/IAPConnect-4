package nl.utwente.iapc.IAPConnect4.model;
import java.util.Random;

public class RandomComputer extends ComputerPlayer {

	public RandomComputer(String name, int number) {
		super(name, number);
	}
	
	@Override
	public int nextMove (Board board) {
		// TODO
		Random rand = new Random();
	    int determinedMove = rand.nextInt((board.BOARDWIDTH) + 1);
		return determinedMove;
	}
	
}