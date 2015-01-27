package nl.utwente.iapc.IAPConnect4.menu;

import java.net.InetAddress;
import java.net.UnknownHostException;

import nl.utwente.iapc.IAPConnect4.IAPConnect4;

public class MenuController {
	private MenuView mv;
	public MenuController() {
		mv = new MenuView(this);
		
	}
	public void serverMode(int port) {
		IAPConnect4.getInstance().serverMode(port);
	}
	public void clientMode(String playerName, String hostname, int port) {
		try {
			InetAddress address = InetAddress.getByName(hostname);
			IAPConnect4.getInstance().clientMode(playerName, address, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void quitGame() {
		System.exit(0);
	}
	public void returnToMenu() {
		mv.showView();
	}
}
