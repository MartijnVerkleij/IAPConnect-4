package nl.utwente.iapc.IAPConnect4.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import nl.utwente.iapc.IAPConnect4.core.Game;
import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;


public class Server {
	ServerSocket ssock;
	LinkedList<ClientHandler> clients = new LinkedList<ClientHandler>();
	LinkedList<Game> games = new LinkedList<Game>();
	boolean exit;
	
	public Server(int port) {
		exit = false;
		try {
			ssock = new ServerSocket(port);
			System.out.println("IAPConnect4 Server\nAccepting connections on port " + port);
		} catch (BindException e) {
			System.err.println("ERROR: Port " + port + " already in use. Exiting.");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			exit = true;
		}
	}
	
	public void startServer() {
		while (!exit) {
			try {
				Socket newClient = ssock.accept();
				ClientHandler handler = new ClientHandler(newClient, this);
				clients.add(handler);
				handler.start();
				System.out.println("New Client");
			} catch (IOException e) {
				System.err.println("ERROR: Unsuucesfully accepted new Client.");
				e.printStackTrace();
			}
		}
	}
	
	
	public void broadcastCommand(Command c) {
		for (ClientHandler cl : clients) {
			cl.sendCommand(c);
		}
	}
	
	public void checkForNewGame() {
		LinkedList<ClientHandler> notInGame = new LinkedList<ClientHandler>();
		for (ClientHandler cl : clients) {
			if (cl.getGame() == null && cl.isReady()) {
				notInGame.add(cl);
			}
		}

		if (notInGame.size() > 1) {
			Game game = new Game(notInGame.get(0).getPlayer(), notInGame.get(1).getPlayer());
			System.out.println("New game with: " + notInGame.get(0).getPlayer().getName() + 
							" + " + notInGame.get(1).getPlayer().getName());
			notInGame.get(0).newGame(game);
			notInGame.get(1).newGame(game);
			notInGame.get(0).getPlayer().addGame(game);
			notInGame.get(1).getPlayer().addGame(game);
			games.add(game);
			broadcastCommand(new Command(Protocol.START_GAME, 
							notInGame.get(0).getPlayer().getName(), 
							notInGame.get(1).getPlayer().getName()));
			
			game.startGame();
		} else {
			System.out.print(".");
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
	
	public void sendCommand(String clientName, Command c) throws InvalidCommandException {
		ClientHandler addressee = findClient(clientName);
		if (addressee != null) {
			addressee.sendCommand(c);
		} else {
			throw new InvalidCommandException();
		}
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			
		}
		
	}
	
	public void stopServer() {
		exit = true;
		
	}
}
