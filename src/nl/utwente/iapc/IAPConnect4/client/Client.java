package nl.utwente.iapc.IAPConnect4.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;
import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;

public class Client extends Observable {
	
	Socket sock;
	ServerHandler handler;
	
	public Client(String playerName, InetAddress server, int port) {
		try {
			sock = new Socket(server, port);
			System.out.println("IAPConnect4 Client\nConnection established with server");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			System.exit(1);
		}
	}
	
	public void startClient() {
		handler = new ServerHandler(sock, this);
		handler.start();
		
	}
	
	public BoardModel getBoard() {
		return handler.getBoard();
	}
	
	public void newGame() {
		System.out.println("notifyObserver()" + countObservers());
		setChanged();
		notifyObservers(1);
	}
	
	public void requestMoveFromPlayer() {
		setChanged();
		notifyObservers(2);
	}
	
	public void doMove(int move) throws InvalidCommandException {
		handler.sendCommand(new Command(Protocol.DO_MOVE, "" + move));
	}
}

