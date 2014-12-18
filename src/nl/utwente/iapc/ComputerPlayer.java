package nl.utwente.iapc;

public abstract class ComputerPlayer implements Player {

	protected String name;
	protected int number;
	
	public ComputerPlayer (String name, int number) {
		this.name = name;
		this.number = number;
	}
	@Override
	public Move nextMove() {
		// TODO 
		return null;
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
