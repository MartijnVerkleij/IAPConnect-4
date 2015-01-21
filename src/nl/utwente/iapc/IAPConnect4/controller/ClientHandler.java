package nl.utwente.iapc.IAPConnect4.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import nl.utwente.iapc.IAPConnect4.exception.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.model.Board;
import nl.utwente.iapc.IAPConnect4.model.Command;
import nl.utwente.iapc.IAPConnect4.model.HumanPlayer;
import nl.utwente.iapc.IAPConnect4.model.Player;
import nl.utwente.iapc.IAPConnect4.model.Server;
import nl.utwente.iapc.IAPConnect4.util.Config;
import nl.utwente.iapc.IAPConnect4.util.Protocol;

public class ClientHandler extends Thread{
	
	Socket sock;
	Server server;
	BufferedReader reader;
	BufferedWriter writer;
	Player player;
	boolean ready;
	
	public ClientHandler(Socket sock, Server server) {
		this.server = server;
		try {
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
		}
		boolean ready = false;
	}
	
	public void run() {
		login();
		parseCommands();
	}

	private void login() {
		try {
			Command join = Command.parse(reader.readLine());
			if (join.getArgument(0) == Protocol.JOIN.toString() ) {
				throw new InvalidCommandException();
			}
			String playerName = join.getArgument(1);
			int groupNumber = Integer.parseInt(join.getArgument(2));
			
			player = new HumanPlayer(playerName);
			
		} catch (InvalidCommandException | IOException | NumberFormatException e) {
			e.printStackTrace();
			System.err.println("ERROR: Invalid login command");
		}
		
		sendCommand(new Command(Protocol.ACCEPT, ""+Config.GROUP_NUMBER));		
	}
	
	private void parseCommands() {
		try {
			Command command = Command.parse(reader.readLine());
			String action = command.getArgument(0);
			if (action.equals(Protocol.READY.toString())) {
				ready = true;
			} else if (ready && action.equals(Protocol.DO_MOVE.toString())) {
				//TODO
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
}
