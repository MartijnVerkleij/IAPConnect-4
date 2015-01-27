package nl.utwente.iapc.IAPConnect4.client;

import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;

import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;

public class ClientController implements Observer {
	
	private ClientView cv;
	private Client client;

	public ClientController(String playerName, InetAddress server, int port) {
		client = new Client(playerName, server, port);
		client.addObserver(this);
	}

	public Client getClient() {
		return client;
	}
	
	public BoardModel getBoard() {
		return client.getBoard();
	}
	public void startClient() {
		client.startClient();
	}



	@Override
	public void update(Observable o, Object arg) {

		if ((Protocol) arg == Protocol.START_GAME) {
			cv = new ClientView(this);
		} else if ((Protocol) arg == Protocol.DO_MOVE) {
			System.out.println("Doe een move clientcontroller");
			cv.unlockBoard();
		} else if ((Protocol) arg == Protocol.DONE_MOVE) {
			System.out.println("Move gedaan clientcontroller");
			cv.refreshBoard();
		}
		
	}
	public void doMove(int x) {
		try {
			client.doMove(x);
		} catch (InvalidCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
