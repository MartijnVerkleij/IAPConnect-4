package nl.utwente.iapc.IAPConnect4.server;

import javax.swing.JOptionPane;

import nl.utwente.iapc.IAPConnect4.IAPConnect4;

public class ServerController {
	private Server server;
	private ServerView sv;
	public ServerController(int port) {
		server = new Server(port);
		sv = new ServerView(this);
	}
	public void stopServer() {
		server.stopServer();
		IAPConnect4.getInstance().returnToMenu();
	}
	public void startServer() {
		if (!server.isExiting()) {
			server.start();
		} else {
			server.stopServer();
			JOptionPane.showMessageDialog(sv, "Port is already in use. Please try another.");
			IAPConnect4.getInstance().returnToMenu();
		}
	}
}
