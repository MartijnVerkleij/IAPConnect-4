package nl.utwente.iapc.IAPConnect4;

import nl.utwente.iapc.IAPConnect4.menu.MenuController;
import nl.utwente.iapc.IAPConnect4.server.ServerController;

public class IAPConnect4 {
	private static IAPConnect4 instance = null;
	
	MenuController menuController;
	ServerController serverController;
	protected IAPConnect4() {
		// Safety precaution
	}
	public static void main(String[] args) {
		getInstance().menuController = new MenuController();
	}
	
	public static synchronized IAPConnect4 getInstance() {
		if (instance == null) {
			instance = new IAPConnect4();
		}
		return instance;
	}
	public void serverMode(int port) {
		getInstance().serverController = new ServerController(port);
		getInstance().serverController.startServer();
	}
	public void returnToMenu() {
		//serverController = null;
		getInstance().menuController.returnToMenu();
	}
}
