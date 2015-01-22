package nl.utwente.iapc.IAPConnect4.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import nl.utwente.iapc.IAPConnect4.exception.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.model.game.NetworkPlayer;
import nl.utwente.iapc.IAPConnect4.model.game.Player;
import nl.utwente.iapc.IAPConnect4.model.networking.Command;
import nl.utwente.iapc.IAPConnect4.model.networking.Server;
import nl.utwente.iapc.IAPConnect4.util.Config;
import nl.utwente.iapc.IAPConnect4.util.Protocol;
import nl.utwente.iapc.IAPConnect4.util.ProtocolError;

public class ClientHandler extends Thread{
	
	Socket sock;
	Server server;
	BufferedReader reader;
	BufferedWriter writer;
	Player player;
	Game game;
	boolean ready;
	Semaphore lastMoveReady = new Semaphore(1);
	boolean exit;
	
	
	int lastMove;
	
	
	public ClientHandler(Socket sock, Server server) {
		this.server = server;
		game = null;
		exit = false;
		lastMove = -1;
		try {
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			exit = true;
		}
		boolean ready = false;
	}
	
	public void run() {
		if (!exit) {
			login();
			while(!exit) {
				parseCommand();
			}
		}
	}

	private void login() {
		String playerName = null;
		int groupNumber = -1;
		while(playerName == null || groupNumber == -1) {
			try {
				Command join = Command.parse(reader.readLine());
				System.out.println(join.toString());
				if (!join.getArgument(0).equals(Protocol.JOIN.toString()) ) {
					throw new InvalidCommandException();
				}
				playerName = join.getArgument(1);
				groupNumber = Integer.parseInt(join.getArgument(2));
				if (groupNumber < 0 || groupNumber> 100) {
					throw new InvalidCommandException();
				}
				player = new NetworkPlayer(this, playerName);
				
			} catch (InvalidCommandException | IOException | NumberFormatException e) {
				e.printStackTrace();
				System.err.println("ERROR: Invalid login command");
			}
		}
		
		
		sendCommand(new Command(Protocol.ACCEPT, ""+Config.GROUP_NUMBER));
		System.out.println("Client logged in: " + player.getName());
	}
	
	public void newGame(Game game) {
		this.game = game;
	}
	
	public int requestMove() {
		server.broadcastCommand(new Command(Protocol.REQUEST_MOVE, player.getName()));
		System.out.println("Requested move from: " + player.getName());
		
		try {
			lastMoveReady.acquire();
			lastMoveReady.acquire();
			lastMoveReady.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
			exit = true;
		}
		return lastMove;
		
	}
	
	private void parseCommand() {
		try {
			Command command = Command.parse(reader.readLine());
			System.out.println(command.toString());
			String action = command.getArgument(0);
			if (action.equals(Protocol.READY.toString())) {
				System.out.println("Client ready to play: " + player.getName());
				ready = true;
			} else if (lastMoveReady.availablePermits() == 0 && action.equals(Protocol.DO_MOVE.toString())) {
				try {
					lastMove = Integer.parseInt(command.getArgument(1));
					lastMoveReady.release();
				} catch (NumberFormatException e) {
					sendCommand(new Command(Protocol.ERROR, ProtocolError.INVALID_MOVE));
				}
			}
			
		} catch (InvalidCommandException | IOException | NumberFormatException e) {
			e.printStackTrace();
			System.err.println("ERROR: Invalid login command");
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
}
