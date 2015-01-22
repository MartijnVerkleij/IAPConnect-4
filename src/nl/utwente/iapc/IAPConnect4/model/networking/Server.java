package nl.utwente.iapc.IAPConnect4.model.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import nl.utwente.iapc.IAPConnect4.controller.ClientHandler;
import nl.utwente.iapc.IAPConnect4.controller.Game;
import nl.utwente.iapc.IAPConnect4.util.GamePoolTick;
import nl.utwente.iapc.IAPConnect4.util.Protocol;

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
	
	public void startServer() {
		GamePoolTick tick = new GamePoolTick(1, this);
		tick.start();
		while(true) {
			try {
				Socket newClient = ssock.accept();
				ClientHandler handler = new ClientHandler(newClient, this);
				clients.add(handler);
				handler.start();
				System.out.println("New Client");
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
			games.add(game);
			broadcastCommand(new Command(Protocol.START_GAME, 
					notInGame.get(0).getPlayer().getName(), 
					notInGame.get(1).getPlayer().getName()));
			game.start();
		} else {
			System.out.println("No new game made");
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
			server.startServer();
		}
		
	}
	
	
}
