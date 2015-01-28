package nl.utwente.iapc.IAPConnect4.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import nl.utwente.iapc.IAPConnect4.core.Config;
import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;
import nl.utwente.iapc.IAPConnect4.core.networking.ProtocolError;

public class ClientHandler extends Thread {

	private Server server;
	private BufferedReader reader;
	private BufferedWriter writer;
	private ServerPlayer player;
	private Game game;
	private boolean ready;
	private boolean exit;

	/**
	 * ClientHandler handles all commands coming from a Client in a separate
	 * Thread and interacts with it. It can have a Game assigned to it by the
	 * Server, which is used to play a game.
	 * 
	 * This constructor creates a BufferedReader and BufferedWriter, or enables
	 * exit.
	 * 
	 * One may actually start communications by starting this Thread with
	 * start().
	 * 
	 * @param sockArg
	 *            Socket belonging to the Client.
	 * @param serverArg
	 *            The server from which this ClientHandler was invoked, used to
	 *            communicate with the Server for starting games etc.
	 */
	// @ requires sockArg != null;
	// @ ensures !isExit() ==> getReader() != null && getWriter() != null;
	public ClientHandler(Socket sockArg, Server serverArg) {
		this.server = serverArg;
		game = null;
		exit = false;
		try {
			reader = new BufferedReader(new InputStreamReader(
					sockArg.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(
					sockArg.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.err
					.println("ERROR: Connection could not be succesfully established. Exiting.");
			exit = true;
		}
		ready = false;
	}

	/**
	 * Starts this server by letting the Client log in in with login() and then
	 * continuously invoking parseCommand until exit is true.
	 */
	// @ requires !isExit() ==> getReader() != null && getWriter() != null;
	// @ ensures isExit();
	public void run() {
		if (!exit) {
			login();
			while (!exit) {
				parseCommand();
			}
		}
		System.out.println("Client Disconnect");
		closeConnection();
	}

	/**
	 * Handles logging in of the Client. It creates a NetworkPlayer on succesful
	 * login.
	 */
	// @ requires getReader() != null && getWriter() != null;
	// @ ensures !isExit() ==> player != null;
	private void login() {
		String playerName = null;
		int groupNumber = -1;
		while (playerName == null || groupNumber == -1) {
			try {
				Command join = Command.parse(reader.readLine());
				System.out.println(join.toString());
				if (!join.getArgument(0).equals(Protocol.JOIN.toString())) {
					throw new InvalidCommandException();
				}
				playerName = join.getArgument(1);
				groupNumber = Integer.parseInt(join.getArgument(2));
				if (groupNumber < 0 || groupNumber > 100) {
					throw new InvalidCommandException();
				}

				if (server.findClient(playerName) != null) {
					throw new InvalidCommandException();
				}
				player = new ServerPlayer(this, playerName);

				sendCommand(new Command(Protocol.ACCEPT, ""
								+ Config.GROUP_NUMBER));
				System.out.println("Client logged in: " + player.getName());
			} catch (InvalidCommandException | IOException
					| NumberFormatException e) {
				e.printStackTrace();
				System.err.println("ERROR: Invalid login command");
				sendCommand(new Command(Protocol.ERROR, "002"));
				exit = true;
				playerName = "";
				groupNumber = 666;
			}
		}
	}

	/**
	 * Register the Game the client is a player of. This can be called to do
	 * moves on.
	 * 
	 * @param gameArg
	 *            Game the player is a part of.
	 */
	// @ requires gameArg != null;
	// @ ensures getGame() != null;
	public void newGame(Game gameArg) {
		this.game = gameArg;
	}

	/**
	 * Removes the Game from this Player, since it has ended. Unsets the ready
	 * state for this Client.
	 */
	// @ ensures getGame() == null && !isReady();
	public void endGame() {
		game = null;
		ready = false;
	}

	/**
	 * Parses one Command from the Client, calling the appropriate actions as
	 * described in the INF-3 Protocol.
	 */
	// @ requires getReader() != null && getWriter() != null;
	private void parseCommand() {
		try {
			Command command = Command.parse(reader.readLine());
			System.out.println(command.toString());
			String action = command.getArgument(0);
			if (action.equals(Protocol.READY_FOR_GAME.toString())) {
				System.out.println("Client ready to play: " + player.getName());
				ready = true;
				server.checkForNewGame();
			} else if (action.equals(Protocol.DO_MOVE.toString())) {
				try {
					player.doMove(Integer.parseInt(command.getArgument(1)));
				} catch (NumberFormatException e) {
					sendCommand(new Command(Protocol.ERROR,
									ProtocolError.INVALID_MOVE));
				}
			}

		} catch (InvalidCommandException | IOException | NumberFormatException e) {
			System.err.println("ERROR: Invalid command from client");
			exit = true;
		}
	}

	/**
	 * Send a Command to the Client associated with this ClientHandler.
	 * 
	 * @param c
	 *            Command to send.
	 */
	//@ requires c.getArgument(0) != null;
	public void sendCommand(Command c) {
		try {
			writer.write(c.getCommand());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for the Player object.
	 * 
	 * @return ServerPlayer instance
	 */
	// @pure
	public ServerPlayer getPlayer() {
		return player;
	}

	/**
	 * Getter for the Game Object.
	 * 
	 * @return Game instance
	 */
	// @pure
	public Game getGame() {
		return game;
	}

	/**
	 * Getter for the ready state of the Client.
	 * 
	 * @return whether the player is ready to play a new Game
	 */
	// @pure
	public boolean isReady() {
		return ready;
	}

	/**
	 * Closes the connection with the Client, disassociating all resources
	 * associated with this Client, gracefully ending an eventual Game the
	 * Client is part of, and subtly closing the connection. It also orders the
	 * Server to remove this ClientHandler from the list of ClientHandlers,
	 * effectively unlinking this Object.
	 */
	// @ requires getWriter() != null && getReader() != null;
	public void closeConnection() {
		try {
			if (game != null) {
				System.out.println("Handling win");
				game.handleWin();
			}
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error while closing connections");
		}
		server.removeClient(this);
	}

	/**
	 * Getter for the Reader, JML.
	 * 
	 * @return Reader
	 */
	// @pure
	public BufferedReader getReader() {
		return reader;
	}

	/**
	 * Getter for the Writer, JML.
	 * 
	 * @return Writer
	 */
	// @pure
	public BufferedWriter getWriter() {
		return writer;
	}

	/**
	 * Returns whether the client will exit, leading to a closeConnection()
	 * invocation. JML
	 * @return whether the ClientHandler is in the exit state.
	 */
	// @pure
	public boolean isExit() {
		return exit;
	}
}