package nl.utwente.iapc.IAPConnect4.model.game;
import java.util.Random;

public class RandomComputer extends ComputerPlayer {

	public RandomComputer(String name) {
		super(name);
	}
	
	@Override
	public int nextMove (Board board) {
		// TODO
		Random rand = new Random();
	    int determinedMove = rand.nextInt((board.BOARDWIDTH) + 1);
		return determinedMove;
	}
	
}
