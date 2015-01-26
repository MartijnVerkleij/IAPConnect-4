package nl.utwente.iapc.IAPConnect4.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import nl.utwente.iapc.IAPConnect4.core.Game;

public class Client {
	Socket sock;
	ServerHandler handler;
	
	public Client(String playerName, InetAddress server, int port) {
		try {
			sock = new Socket(server, port);
			System.out.println("IAPConnect4 Client\nConnection established with server");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: Connection could not be succesfully established. Exiting.");
			System.exit(1);
		}
	}
	
	public void startClient() {
		handler = new ServerHandler(sock, this);
		handler.start();
		
	}
	
	public void requestMoveFromPlayer() {
		
	}
	

}

