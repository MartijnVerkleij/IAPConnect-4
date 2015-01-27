package nl.utwente.iapc.IAPConnect4.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;


import nl.utwente.iapc.IAPConnect4.core.Config;
import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;
import nl.utwente.iapc.IAPConnect4.core.game.InvalidMoveException;
import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;
import nl.utwente.iapc.IAPConnect4.core.networking.ProtocolError;

public class ServerHandler extends Thread {
	
	Socket sock;
	Client client;
	BufferedReader reader;
	BufferedWriter writer;
	String player;
	
	BoardModel board;
	LinkedList<String> gamePlayers;
	
	boolean ready;
	Semaphore lastMoveReady = new Semaphore(1);
	boolean exit;

	public ServerHandler(Socket sockArg, Client clientArg) {
		this.sock = sockArg;
		this.client = clientArg;
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
	
	public void run() {
		String playerName = "Test" + new Random().nextInt(50);
		if (!exit) {
			login(playerName);
			while (!exit) {
				parseCommand();
			}
		}
	}
	
	private void login(String playerName) {
		int groupNumber = Config.GROUP_NUMBER;
		try {
			Command join = new Command(Protocol.JOIN, playerName, groupNumber + "");
			System.out.println("join:\n" + join.toString());
			sendCommand(join);
			player = playerName;
			Command accept = Command.parse(reader.readLine());
			groupNumber = Integer.parseInt(accept.getArgument(1));
			if (!accept.getArgument(0).equals(Protocol.ACCEPT.toString()) || groupNumber <= 0) {
				throw new InvalidCommandException();
			}
			System.out.println("Logged in on server: " + groupNumber);
			
			sendCommand(new Command(Protocol.READY_FOR_GAME));
		} catch (InvalidCommandException | IOException | NumberFormatException e) {
			e.printStackTrace();
			System.err.println("ERROR: Not succesfully logged in");
			exit = true;
		}
	}
	
	private void parseCommand() {
		try {
			Command command = Command.parse(reader.readLine());
			System.out.println(command.toString());
			String action = command.getArgument(0);
			if (action.equals(Protocol.START_GAME.toString())) {
				LinkedList<String> tempPlayers = new LinkedList<String>();
				tempPlayers.add(command.getArgument(1));
				tempPlayers.add(command.getArgument(2));
				if (tempPlayers.indexOf(player) >= 0) {
					board = new BoardModel();
					gamePlayers = tempPlayers;
					client.newGame();
				}
				System.out.println("New game started with " + tempPlayers.get(0) + 
								" and " + tempPlayers.get(1));
				
			} else if (action.equals(Protocol.DONE_MOVE.toString())) {
				try {
					board = board.move(Integer.parseInt(command.getArgument(2)), 
									gamePlayers.indexOf(command.getArgument(1)));
				} catch (NumberFormatException | InvalidMoveException e) {
					sendCommand(new Command(Protocol.ERROR, ProtocolError.INVALID_MOVE));
				}
				
				//TODO disable UI on successful move
			} else if (action.equals(Protocol.REQUEST_MOVE.toString())) {
				client.requestMoveFromPlayer();
			} else if (action.equals(Protocol.GAME_END.toString())) {
				board = null;
				gamePlayers = null;
				
				//TODO show fancy win dialog
			}
			
			
		} catch (InvalidCommandException | IOException | NumberFormatException e) {
			System.err.println("ERROR: Invalid command from server");
			exit = true;
		}
	}
	
	
	
	public void sendCommand(Command c) throws InvalidCommandException {
		try {
			writer.write(c.getCommand());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BoardModel getBoard() {
		return board;
	}
}
