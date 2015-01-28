package nl.utwente.iapc.IAPConnect4.test;

import static org.junit.Assert.*;
import nl.utwente.iapc.IAPConnect4.core.game.Board;
import nl.utwente.iapc.IAPConnect4.core.game.InvalidMoveException;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	public Board board;

	@Before
	public void prepareBoard() {
		board = new Board();
	}

	@Test
	public void testInvalidMove() {
		for (int i = 0; i < 6; i++) {
			try {
				board.move(1, 1);
			} catch (InvalidMoveException e) {
				fail("Invalid move given too early");
				break;
			}
		}
		try {
			board.move(1, 1);
			fail("Invalid move not properly given");
		} catch (InvalidMoveException e) {
			
		}
	}
	
	@Test
	public void testIsLegalMove() {
		for (int i = 0; i < 6; i++) {
			if (!board.isLegalMove(i)) {
				fail("Invalid move given too early");
			}
		}
		if (board.isLegalMove(7)) {
			fail("Invalid move not properly given");
		}
	}
	
	
	
	
}
