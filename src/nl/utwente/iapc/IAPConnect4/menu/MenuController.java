package nl.utwente.iapc.IAPConnect4.menu;

public class MenuController {
	private MenuView mv;
	public MenuController () {
		mv = new MenuView(this);
		
	}
	public void QuitGame () {
		System.exit(0);
	}

}
