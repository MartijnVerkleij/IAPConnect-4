package nl.utwente.iapc.IAPConnect4.model;

public abstract class ComputerPlayer implements Player {

	protected String name;
	protected int number;
	
	public ComputerPlayer (String name, int number) {
		this.name = name;
		this.number = number;
	}
	@Override
	public int nextMove() {
		// TODO 
		return 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getPlayerNumber() {
		return number;
	}

}
