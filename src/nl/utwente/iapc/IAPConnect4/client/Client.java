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
	
	private Socket sock;
	private ServerHandler handler;
	private String playerName;
	private String winner;
	
	public Client(String playerNameArg, InetAddress server, int port) {
		try {
			this.playerName = playerNameArg;
			sock = new Socket(server, port);
			System.out.println("IAPConnect4 Client\nConnection established with server");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			System.exit(1);
		}
	}
	
	public void startClient() {
		handler = new ServerHandler(sock, this, playerName);
		handler.start();
		
	}
	
	public BoardModel getBoard() {
		return handler.getBoard();
	}
	
	
	public ServerHandler getHandler() {
		return handler;
	}
	
	public void newGame() {
		setChanged();
		notifyObservers(Protocol.START_GAME);
	}
	
	public void requestMoveFromPlayer() {
		setChanged();
		notifyObservers(Protocol.DO_MOVE);
	}
	
	public void moveDone() {
		setChanged();
		notifyObservers(Protocol.DONE_MOVE);
	}
	
	public void setWinner(String winnerArg) {
		winner = winnerArg;
	}
	public String getWinner() {
		return winner;
	}
	
	public void endGame(String winnerArg) {
		setWinner(winnerArg);
		setChanged();
		notifyObservers(Protocol.GAME_END);
	}
	
	public void doMove(int move) throws InvalidCommandException {
		handler.sendCommand(new Command(Protocol.DO_MOVE, "" + move));
	}
	
	public String getPlayerName() {
		return playerName;
	}
}

