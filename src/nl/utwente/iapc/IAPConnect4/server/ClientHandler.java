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
	
	//@ requires sockArg != null;
	//@ ensures !isExit() ==> getReader() != null && getWriter() != null;
	public ClientHandler(Socket sockArg, Server serverArg) {
		this.server = serverArg;
		game = null;
		exit = false;
		try {
			reader = new BufferedReader(new InputStreamReader(sockArg.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(sockArg.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			exit = true;
		}
		ready = false;
	}
	
	//@ requires !isExit() ==> getReader() != null && getWriter() != null;
	//@ ensures isExit();
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
	
	
	//@ requires getReader() != null && getWriter() != null;
	//@ ensures !isExit() ==> player != null;
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
				
				sendCommand(new Command(Protocol.ACCEPT, "" + Config.GROUP_NUMBER));
				System.out.println("Client logged in: " + player.getName());
			} catch (InvalidCommandException | IOException | NumberFormatException e) {
				e.printStackTrace();
				System.err.println("ERROR: Invalid login command");
				sendCommand(new Command(Protocol.ERROR, "002"));
				exit = true;
				playerName = "";
				groupNumber = 666;
			}
		}
	}
	
	//@ requires gameArg != null;
	//@ ensures getGame() != null;
	public void newGame(Game gameArg) {
		this.game = gameArg;
	}
	
	//@ ensures getGame() == null && !isReady();
	public void endGame() {
		game = null;
		ready = false;
	}
	
	
	//@ requires getReader() != null && getWriter() != null;
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
					sendCommand(new Command(Protocol.ERROR, ProtocolError.INVALID_MOVE));
				}
			}
			
		} catch (InvalidCommandException | IOException | NumberFormatException e) {
			System.err.println("ERROR: Invalid command from client");
			exit = true;
		}
	}
	
	//@ requires c.getArgument(0) != null; 
	public void sendCommand(Command c) {
		try {
			writer.write(c.getCommand());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ServerPlayer getPlayer() {
		return player;
	}
	
	//@pure
	public Game getGame() {
		return game;
	}
	
	//@pure
	public boolean isReady() {
		return ready;
	}
	
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
	//@pure
	public BufferedReader getReader() {
		return reader;
	}
	//@pure
	public BufferedWriter getWriter() {
		return writer;
	}
	//@pure
	public boolean isExit() {
		return exit;
	}
}