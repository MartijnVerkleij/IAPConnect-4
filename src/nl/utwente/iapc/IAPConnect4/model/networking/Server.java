package nl.utwente.iapc.IAPConnect4.model.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import nl.utwente.iapc.IAPConnect4.controller.ClientHandler;
import nl.utwente.iapc.IAPConnect4.controller.Game;

public class Server {
	ServerSocket ssock;
	LinkedList<ClientHandler> clients = new LinkedList<ClientHandler>();
	LinkedList<Game> games = new LinkedList<Game>();
	
	public Server(int port) {
		try {
			ssock = new ServerSocket(port);
			System.out.println("IAPConnect4 Server\nAccepting connections");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			System.exit(0);
		}
	}
	
	public void run() {
		while(true) {
			try {
				Socket newClient = ssock.accept();
				ClientHandler handler = new ClientHandler(newClient, this);
				clients.add(handler);
				System.out.println("New Client");
				checkForNewGame();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void broadcastCommand(Command c) {
		for (ClientHandler cl : clients) {
			cl.sendCommand(c);
		}
	}
	
	private void checkForNewGame() {
		for (ClientHandler cl : clients) {
			if (cl) {
				return c;
			}
		}
	}
	
	private ClientHandler findClient(String name) {
		for (ClientHandler c : clients) {
			if (c.getPlayer().getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public void sendCommand(String clientName, Command c) throws NullPointerException{
		findClient(clientName).sendCommand(c);
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			Server server = new Server(Integer.parseInt(args[0]));
			server.run();
		}
		
	}
	
	
}
