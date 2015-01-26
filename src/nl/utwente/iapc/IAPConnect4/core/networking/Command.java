package nl.utwente.iapc.IAPConnect4.core.networking;

import java.util.Arrays;

public class Command {
	
	private final Protocol command;
	private final String[] arguments;
	
	public Command(Protocol p) {
		command = p;
		arguments = new String[]{};
	}
	
	public Command(Protocol p, String arg1) {
		command = p;
		arguments = new String[]{arg1};
	}
	
	public Command(Protocol p, String arg1, String arg2) {
		command = p;
		arguments = new String[]{arg1, arg2};
	}
	
	private Command(Protocol p, String[] args) {
		command = p;
		arguments = args;
	}
	
	public String getArgument(int index) {
		if (index == 0) {
			return command.toString();
		} else if (index > 0 && index <= arguments.length) {
			return arguments[index - 1];
		} else {
			return null;
		}
	}
	
	public String getCommand() {
		String rString = command.toString();
		for (String s : arguments) {
			rString += " " + s;
		}
		rString += "\r\n";
		return rString;
	}
	
	public static Command parse(String command) throws InvalidCommandException {
		if (command == null) {
			throw new InvalidCommandException();
		}
		String[] splitCommand = command.split(" ");
		Protocol arg0 = null;
		for (Protocol p : Protocol.values()) {
			if (p.toString().equals(splitCommand[0])) {
				arg0 = p;
			}
		}
		
		if (arg0 == null) {
			throw new InvalidCommandException();
		}
		
		String[] args = new String[splitCommand.length - 1];
		for (int i = 1; i < splitCommand.length; i++) {
			args[i - 1] = splitCommand[i];
		}
		
		return new Command(arg0, args);
	}
	
	public String toString() {
		return "command: \"" + command + "\"\narguments = \"" + Arrays.toString(arguments) + "\""; 
	}

}
