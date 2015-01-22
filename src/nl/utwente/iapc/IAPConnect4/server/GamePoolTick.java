package nl.utwente.iapc.IAPConnect4.server;


public class GamePoolTick extends Thread {
	
	int seconds;
	Server server;
	
	public GamePoolTick( int sec, Server server) {
		seconds = sec;
		this.server = server;
	}
	
	public void run() {
		try {
			while (true) {
				sleep(seconds * 1000);
				server.checkForNewGame();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
