package nl.utwente.iapc.IAPConnect4.client;

import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;

import nl.utwente.iapc.IAPConnect4.client.game.ComputerPlayer;
import nl.utwente.iapc.IAPConnect4.client.game.WinningComputer;
import nl.utwente.iapc.IAPConnect4.core.game.Board;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;

public class ClientController implements Observer {
	
	private ClientView cv;
	private Client client;
	private boolean aiEnabled;
	private boolean ourMove;
	private ComputerPlayer ai;
	
	public ClientController(String playerName, InetAddress server, int port) {
		
		ourMove = false;
		aiEnabled = false;
		client = new Client(playerName, server, port);
		client.addObserver(this);
		
	}

	public Client getClient() {
		return client;
	}
	
	public Board getBoard() {
		return client.getBoard();
	}
	public void startClient() {
		client.startClient();
	}
	
	public void disposeView() {
		cv.dispose();
		cv = null;
	}
	
	public void disconnect() {
		client.disconnect();
	}

	public void toggleAI() {
		aiEnabled = !aiEnabled;
	}
	
	public boolean aiIsEnabled() {
		return aiEnabled;
	}
	
	public boolean isOurMove() {
		return ourMove;
	}
	


	@Override
	public void update(Observable o, Object arg) {

		if ((Protocol) arg == Protocol.START_GAME) {
			cv = new ClientView(this);
			ai = new WinningComputer("");
		} else if ((Protocol) arg == Protocol.DO_MOVE) {
			System.out.println("Doe een move clientcontroller");
			ourMove = true;
			if (aiEnabled) {
				doAiMove();
			} else {
				cv.unlockBoard();
			}
		} else if ((Protocol) arg == Protocol.DONE_MOVE) {
			System.out.println("Move gedaan clientcontroller");
			cv.refreshBoard();
		} else if ((Protocol) arg == Protocol.GAME_END) {
			System.out.println("Game end");
			cv.gameEnd(((Client) o).getWinner());
		}
	}
	
	public void doAiMove() {
		doMove(ai.nextMove(getBoard()));
	}
	
	public void doMove(int x) {
		try {
			client.doMove(x);
			ourMove = false;
		} catch (InvalidCommandException e) {
			e.printStackTrace();
		}
	}
}
