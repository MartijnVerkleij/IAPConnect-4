package nl.utwente.iapc.IAPConnect4.core.networking;

import java.util.Arrays;

public class Command {

	// @ invariant getArgument(0) != null;

	private final Protocol command;
	private final String[] arguments;

	/**
	 * Constructs a new {@link Command} object with a command string and no
	 * argument strings.
	 * 
	 * @param p
	 *            instance of {@link Protocol}.
	 */
	// @ requires p != null;
	// @ ensures getArgument(0).equals(p.toString());
	public Command(Protocol p) {
		command = p;
		arguments = new String[] {};
	}

	/**
	 * Constructs a new {@link Command} object with a command string and one
	 * argument string.
	 * 
	 * @param p
	 *            instance of {@link Protocol}.
	 * @param arg1
	 *            {@link String} of the first argument.
	 */
	// @ requires p != null && arg1 != null;
	// @ ensures getArgument(0).equals(p.toString()) && getArgument(1) == arg1;
	public Command(Protocol p, String arg1) {
		command = p;
		arguments = new String[] {arg1 };
	}

	/**
	 * Constructs a new {@link Command} object with a command string and two
	 * argument strings.
	 * 
	 * @param p
	 *            instance of {@link Protocol}.
	 * @param arg1
	 *            {@link String} of the first argument.
	 * @param arg2
	 *            {@link String} of the second argument.
	 */
	// @ requires p != null && arg1 != null && arg2 != null;
	// @ ensures getArgument(0).equals(p.toString()) && getArgument(1) == arg1
	// && getArgument(2) == arg2;
	public Command(Protocol p, String arg1, String arg2) {
		command = p;
		arguments = new String[] {arg1, arg2 };
	}

	/**
	 * Constructs a new {@link Command} object with a command string and n
	 * argument strings.
	 * 
	 * @param p
	 *            instance of {@link Protocol}.
	 * @param args
	 *            {@link String}[] of argument Strings.
	 */
	// @ requires p != null && args != null;
	// @ ensures getArgument(0).equals(p.toString());
	private Command(Protocol p, String[] args) {
		command = p;
		arguments = args;
	}

	/**
	 * Returns the nth argument of the {@link Command}, with 0 being the command
	 * itself, subset of {@link Protocol}.values() in {@link String} form.
	 * 
	 * @param index
	 *            index of the requested argument.
	 * @return {@link String} represetation of the argument.
	 */
	//@pure
	public String getArgument(int index) {
		if (index == 0) {
			return command.toString();
		} else if (index > 0 && index <= arguments.length) {
			return arguments[index - 1];
		} else {
			return null;
		}
	}

	/**
	 * Returns the whole command in {@link String} form, for sending it over a
	 * {@link Socket} through a {@link BufferedReader} or {@link BufferedWriter}
	 * .
	 * 
	 * @return {@link String} representation of the command, including a
	 *         <code>\r\n</code>
	 */
	// @ ensures \result != null;
	// @ pure
	public String getCommand() {
		String rString = command.toString();
		for (String s : arguments) {
			rString += " " + s;
		}
		rString += "\r\n";
		return rString;
	}

	/**
	 * Parse a {@link String} representation of a command, splitting it into a
	 * command, instanceof {@link Protocol}, and {@link String} arguments.
	 * 
	 * @param command
	 *            {@link String} command.
	 * @return instance of {@link Command}.
	 * @throws InvalidCommandException
	 *             On <code>null</code> on input and on the first word not being
	 *             a recognised command.
	 */
	// @ ensures command != null ==> \result.getArgument(0) != null;
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

	/**
	 * Debug representation of the command in {@link String} form. Not suitable
	 * for sending the Command over the Internet, use getCommand() instead.
	 * 
	 * @return Debug representation of a {@link Command} in {@link String} form.
	 */
	public String toString() {
		return "command: \"" + command + "\"\narguments = \""
				+ Arrays.toString(arguments) + "\"";
	}

}
