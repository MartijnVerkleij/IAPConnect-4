package nl.utwente.iapc.IAPConnect4.view;

import java.util.Observable;
import java.util.Scanner;

public class IAPConnect4TUI extends Observable{

	public IAPConnect4TUI() {
		// TODO Auto-generated constructor stub
	}
	
	public void showIntro() {
		System.out.println("            Welcome to IAPConnect4            ");
		System.out.println("                                              ");
		System.out.println("                                              ");
		System.out.println("  '    ..   .--. .---                      .  ");
		System.out.println("  |   /  \\  |   ||    .-. . .. ..- .- --- /|  ");
		System.out.println("  |  /----\\ |--' |    | | |\\||\\||- |   | '-|  ");
		System.out.println("  | /      \\|    '--- '-' ' '' ''- '-  '   |  \n");
	}
	
	public void showMainMenu() {
		System.out.println(" game: start new game         help: show help \n");
		
		Scanner user_input = new Scanner(System.in);
		
		switch (user_input.nextLine()) {
		case "game": controller.startGame(); break;
		case "help": controller.showHelp(); break;
		case default: System.out.println("Invalid option") break;
		}
		
		
	}
	
	public static void main(String[] args) {
		new IAPConnect4TUI().showMainMenu();
	}

}
