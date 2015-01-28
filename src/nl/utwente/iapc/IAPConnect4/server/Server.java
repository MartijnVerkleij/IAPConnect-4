package nl.utwente.iapc.IAPConnect4.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;


public class Server extends Thread {
	
	private ServerSocket ssock;
	private LinkedList<ClientHandler> clients = new LinkedList<ClientHandler>();
	private LinkedList<Game> games = new LinkedList<Game>();
	private boolean exit;
	
	
	//@ requires port > 0 && port < 65535;
	//@ ensures !isExiting() ==> getServerSocket() != null;
	public Server(int port) {
		exit = false;
		try {
			ssock = new ServerSocket(port);
		} catch (BindException e) {
			System.err.println("ERROR: Port " + port + " already in use. Exiting.");
			exit = true;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			exit = true;
		}
	}
	
	
	public void run() {
		startServer();
	}
	
	//@ requires !isExiting() ==> getServerSocket() != null;
	public void startServer() {
		while (!exit) {
			try {
				System.out.println("IAPConnect4 Server\nAccepting connections on port " + 
								ssock.getLocalPort());
				Socket newClient = ssock.accept();
				ClientHandler handler = new ClientHandler(newClient, this);
				clients.add(handler);
				handler.start();
				System.out.println("New Client");
			} catch (IOException e) {
				if (exit) {
					System.out.println("Server Closed");
				} else {
					System.err.println("ERROR: Unsuccesfully accepted new Client.");
				}
				
			}
		}
	}
	
	
	
	public void broadcastCommand(Command c) {
		for (ClientHandler cl : clients) {
			cl.sendCommand(c);
		}
	}
	
	//@pure
	public LinkedList<ClientHandler> availablePlayers() {
		LinkedList<ClientHandler> availablePlayers = new LinkedList<ClientHandler>();
		for (ClientHandler cl : clients) {
			if (cl.getGame() == null && cl.isReady()) {
				availablePlayers.add(cl);
			}
		}
		return availablePlayers;
	}
	
	
	/*@ ensures \old(availablePlayers().size() > 1) ==> 
	  	getGames().size() == \old(getGames().size()) + 1; @*/
	public void checkForNewGame() {
		LinkedList<ClientHandler> notInGame = availablePlayers();

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
	
	public void removeClient(ClientHandler ch) {
		clients.remove(ch);
	}
	
	//requires foreach
	public void stopServer() {
		exit = true;
		for (ClientHandler ch : clients) {
			ch.closeConnection();
		}
		try {
			Thread.sleep(1000);
			ssock.close();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//@pure
	public ServerSocket getServerSocket() {
		return ssock;
	}
	
	//@pure
	public LinkedList<Game> getGames() {
		return games;
	}
	
	//@pure
	public boolean isExiting() {
		return exit;
	}
}
