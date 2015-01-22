package nl.utwente.iapc.IAPConnect4.server;

import nl.utwente.iapc.IAPConnect4.core.Game;
import nl.utwente.iapc.IAPConnect4.core.game.BoardModel;
import nl.utwente.iapc.IAPConnect4.core.game.InvalidMoveException;
import nl.utwente.iapc.IAPConnect4.core.game.Player;
import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;

public class ServerPlayer implements Player {
	
	ClientHandler handler;
	String nickName;
	Game game;
	
	public ServerPlayer(ClientHandler handler, String nickName) {
		this.handler = handler;
		this.nickName = nickName;
	}
	
	@Override
	public void doMove(int move) {
		try {
			game.doMove(this, move);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
			sendCommand(new Command(Protocol.ERROR, "002"));
		}
	}
	
	public void addGame(Game game) {
		this.game = game;
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
