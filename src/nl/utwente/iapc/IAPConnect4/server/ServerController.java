package nl.utwente.iapc.IAPConnect4.server;

import nl.utwente.iapc.IAPConnect4.IAPConnect4;

public class ServerController {
	private Server server;
	public ServerController(int port) {
		server = new Server(port);
	}
	public void stopServer() {
		server.stopServer();
		IAPConnect4.getInstance().returnToMenu();
	}
	public void startServer() {
		server.start();
	}

}
