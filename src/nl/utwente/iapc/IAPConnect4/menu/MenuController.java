package nl.utwente.iapc.IAPConnect4.menu;

import nl.utwente.iapc.IAPConnect4.IAPConnect4;

public class MenuController {
	private MenuView mv;
	public MenuController() {
		mv = new MenuView(this);
		
	}
	public void serverMode(int port) {
		IAPConnect4.getInstance().serverMode(port);
	}
	public void quitGame() {
		System.exit(0);
	}
	public void returnToMenu() {
		mv.showView();
	}
}
