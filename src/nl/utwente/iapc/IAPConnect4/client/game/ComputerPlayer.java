package nl.utwente.iapc.IAPConnect4.client.game;

import nl.utwente.iapc.IAPConnect4.core.game.Board;
import nl.utwente.iapc.IAPConnect4.core.game.Player;


public abstract class ComputerPlayer implements Player {

	protected String name;
	
	public ComputerPlayer(String nameArg) {
		this.name = nameArg;
	}
	
	public int nextMove(Board board) {
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
