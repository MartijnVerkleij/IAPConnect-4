package nl.utwente.iapc.IAPConnect4.client;

import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;

import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;

public class ClientController implements Observer{
	
	private ClientView cv;
	private Client client;

	public ClientController(String playerName, InetAddress server, int port) {
		client = new Client(playerName, server, port);
		client.addObserver(this);
	}
	
	
	
	public BoardModel getBoard() {
		return client.getBoard();
	}
	public void startClient() {
		client.startClient();
	}



	@Override
	public void update(Observable o, Object arg) {

		System.out.println("new game!!!" + (int) arg);
		
		if ((int) arg == 1) {
			cv = new ClientView(this);
		} else if ((int) arg == 2) {
			//TODO notify UI of able to do a move
		}
		
	}
}
