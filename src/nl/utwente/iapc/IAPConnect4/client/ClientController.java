package nl.utwente.iapc.IAPConnect4.client;

import java.net.InetAddress;
import java.util.Observable;

import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;

public class ClientController extends Observable {
	
	private ClientView cv;
	private Client client;

	public ClientController(String playerName, InetAddress server, int port) {
		cv = new ClientView(this);
		client = new Client(playerName, server, port, this);
	}
	
	
	
	public BoardModel getBoard() {
		return client.getBoard();
	}
}
