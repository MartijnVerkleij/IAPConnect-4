package nl.utwente.iapc.IAPConnect4.menu;

public class MenuController {
	private MenuView mv;
	public MenuController() {
		mv = new MenuView(this);
		
	}
	public void quitGame() {
		System.exit(0);
	}

}
