package nl.utwente.iapc.IAPConnect4.server;

import nl.utwente.iapc.IAPConnect4.IAPConnect4;
import nl.utwente.iapc.IAPConnect4.menu.MenuView;

public class ServerController {
	private ServerView sv;
	private Server server;
	public ServerController(int port) {
		sv = new ServerView(this);
		server = new Server(port);
		server.startServer();
	}
	public void stopGame() {
		server.stopServer();
		IAPConnect4.getInstance().returnToMenu();
	}

}
