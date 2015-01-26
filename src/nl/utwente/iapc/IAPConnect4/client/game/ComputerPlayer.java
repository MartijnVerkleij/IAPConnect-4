package nl.utwente.iapc.IAPConnect4.client.game;

import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;
import nl.utwente.iapc.IAPConnect4.core.game.Player;


public abstract class ComputerPlayer implements Player {

	protected String name;
	
	public ComputerPlayer (String name) {
		this.name = name;
	}
	
	public int nextMove(BoardModel board) {
		// TODO 
		return 0;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void result(Player player) {
		//TODO
	}
}
