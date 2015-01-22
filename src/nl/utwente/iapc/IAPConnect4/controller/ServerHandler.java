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
import nl.utwente.iapc.IAPConnect4.model.networking.Client;
import nl.utwente.iapc.IAPConnect4.model.networking.Command;
import nl.utwente.iapc.IAPConnect4.model.networking.Server;
import nl.utwente.iapc.IAPConnect4.util.Config;
import nl.utwente.iapc.IAPConnect4.util.Protocol;

public class ServerHandler extends Thread {
	
	Socket sock;
	Client client;
	BufferedReader reader;
	BufferedWriter writer;
	Player player;
	Game game;
	boolean ready;
	Semaphore lastMoveReady = new Semaphore(1);
	boolean exit;

	public ServerHandler(Socket sock, Client client) {
		this.sock = sock;
		this.client = client;
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
			while(!exit) {
				parseCommand();
			}
		}
	}
	
	private void login(String PlayerName) {
		String playerName = PlayerName;
		int groupNumber = Config.GROUP_NUMBER;
		try {
			Command join = new Command(Protocol.JOIN, playerName, groupNumber + "");
			System.out.println(join.toString());
			sendCommand(join);
			player = new NetworkPlayer(this, playerName);
			
		} catch (InvalidCommandException | IOException | NumberFormatException e) {
			e.printStackTrace();
			System.err.println("ERROR: Invalid login command");
		}
		
		
		
		sendCommand(new Command(Protocol.ACCEPT, ""+Config.GROUP_NUMBER));
		System.out.println("Client logged in: " + player.getName());
	}
	
	private void parseCommand() {
		
	}
	
	public void sendCommand(Command c) {
		
	}

	
	//evt. getters

}
