package nl.utwente.iapc.IAPConnect4.core.game;

import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;
import nl.utwente.iapc.IAPConnect4.server.ClientHandler;

public class NetworkPlayer implements Player {
	
	ClientHandler handler;
	String nickName;
	
	public NetworkPlayer(ClientHandler handler, String nickName) {
		this.handler = handler;
		this.nickName = nickName;
	}

	@Override
	public int nextMove(BoardModel board) {
		return handler.requestMove();
	}

	@Override
	public String getName() {
		return nickName;
	}
	
	public void sendCommand(Command c) {
		handler.sendCommand(c);
	}

	@Override
	public void result(Player player) {
		if (player != null) {
			handler.sendCommand(new Command(Protocol.GAME_END, player.getName()));
		} else {
			handler.sendCommand(new Command(Protocol.GAME_END));
		}
		handler.endGame();
	}

}
