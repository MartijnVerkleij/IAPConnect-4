package nl.utwente.iapc.IAPConnect4.client;

import java.net.InetAddress;


import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;

public class ClientController {
	
	private ClientView cv;
	private Client client;

	public ClientController(String playerName, InetAddress server, int port) {
		cv = new ClientView(this);
		client = new Client(playerName, server, port, this);
	}
	
	
	
	public BoardModel getBoard() {
		return client.getBoard();
	}
	public void startClient() {
		client.startClient();
	}
}
