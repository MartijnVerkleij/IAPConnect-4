package nl.utwente.iapc.IAPConnect4.server;

import nl.utwente.iapc.IAPConnect4.menu.MenuView;

public class ServerController {
	private ServerView sv;
	private Server server;
	public ServerController(int port) {
		sv = new ServerView(this);
		server = new Server(port);
		
	}
	public void stopGame() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sv.dispose();
	}

}
