package nl.utwente.iapc.IAPConnect4.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import nl.utwente.iapc.IAPConnect4.core.Config;
import nl.utwente.iapc.IAPConnect4.core.Game;
import nl.utwente.iapc.IAPConnect4.core.game.Player;
import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;
import nl.utwente.iapc.IAPConnect4.core.networking.ProtocolError;

public class ClientHandler extends Thread{
	
	private Socket sock;
	private Server server;
	private BufferedReader reader;
	private BufferedWriter writer;
	private Player player;
	private Game game;
	private boolean ready;
	private boolean exit;
	
	public ClientHandler(Socket sock, Server server) {
		this.server = server;
		this.sock = sock;
		game = null;
		exit = false;
		try {
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			exit = true;
		}
		ready = false;
	}
	
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
					exit = true;
					throw new InvalidCommandException();
				}
				player = new ServerPlayer(this, playerName);
				
				sendCommand(new Command(Protocol.ACCEPT, "" + Config.GROUP_NUMBER));
				System.out.println("Client logged in: " + player.getName());
			} catch (InvalidCommandException | IOException | NumberFormatException e) {
				e.printStackTrace();
				System.err.println("ERROR: Invalid login command");
			}
		}
	}
	
	public void newGame(Game game) {
		this.game = game;
	}
	
	public void requestMove() {
		server.broadcastCommand(new Command(Protocol.REQUEST_MOVE, player.getName()));
		System.out.println("Requested move from: " + player.getName());
	}
	
	public void endGame() {
		game = null;
		ready = false;
	}
	
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
	
	public void sendCommand(Command c) {
		try {
			writer.write(c.getCommand());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Game getGame() {
		return game;
	}
	
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
}
