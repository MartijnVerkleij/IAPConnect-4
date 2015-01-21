package nl.utwente.iapc.IAPConnect4.model;

public abstract class ComputerPlayer implements Player {

	protected String name;
	
	public ComputerPlayer (String name) {
		this.name = name;
	}
	@Override
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
