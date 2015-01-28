package nl.utwente.iapc.IAPConnect4.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import nl.utwente.iapc.IAPConnect4.core.networking.Command;
import nl.utwente.iapc.IAPConnect4.core.networking.InvalidCommandException;
import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;

public class Server extends Thread {

	private ServerSocket ssock;
	private LinkedList<ClientHandler> clients = new LinkedList<ClientHandler>();
	private LinkedList<Game> games = new LinkedList<Game>();
	private boolean exit;

	/**
	 * Starts a {@link Server} on the given port. This will accept new
	 * {@link Client}s, creating new {@link ClientHandler}s to handle them. It
	 * will also after each ready change from a {@link Client} check if a new
	 * {@link Game} can be made. It also holds all {@link ClientHandler}s and
	 * {@link Game}s currently playing.
	 * 
	 * @param port
	 *            port to start the Server on.
	 */
	// @ requires port > 0 && port < 65535;
	// @ ensures !isExiting() ==> getServerSocket() != null;
	public Server(int port) {
		exit = false;
		try {
			ssock = new ServerSocket(port);
		} catch (BindException e) {
			System.err.println("ERROR: Port " + port
							+ " already in use. Exiting.");
			exit = true;
		} catch (IOException e) {
			e.printStackTrace();
			System.err
					.println("ERROR: Connection could not be succesfully established. Exiting.");
			exit = true;
		}
	}

	/**
	 * Thread wrapper function for startServer().
	 */
	// @ requires !isExiting() ==> getServerSocket() != null;
	public void run() {
		startServer();
	}

	/**
	 * Starts the actual handling of new {@link Client}s, creating
	 * {@link ClientHandler}s for them, and starting these in their own
	 * {@link Thread}.
	 */
	// @ requires !isExiting() ==> getServerSocket() != null;
	public void startServer() {
		while (!exit) {
			try {
				System.out
								.println("IAPConnect4 Server\nAccepting connections on port "
								+ ssock.getLocalPort());
				Socket newClient = ssock.accept();
				ClientHandler handler = new ClientHandler(newClient, this);
				clients.add(handler);
				handler.start();
				System.out.println("New Client");
			} catch (IOException e) {
				if (exit) {
					System.out.println("Server Closed");
				} else {
					System.err
							.println("ERROR: Unsuccesfully accepted new Client.");
				}

			}
		}
	}

	/**
	 * Broadcasts a {@link Command} to all {@link Client}s connected to this
	 * Server.
	 * 
	 * @param c
	 *            Command to send
	 */
	public synchronized void broadcastCommand(Command c) {
		for (ClientHandler cl : clients) {
			cl.sendCommand(c);
		}
	}

	/**
	 * Returns a list of {@link Player}s that are connected, not in a
	 * {@link Game}, and ready for a new {@link Game}.
	 * 
	 * @return list of available players.
	 */
	// @pure
	public synchronized LinkedList<ClientHandler> availablePlayers() {
		LinkedList<ClientHandler> availablePlayers = new LinkedList<ClientHandler>();
		for (ClientHandler cl : clients) {
			if (cl.getGame() == null && cl.isReady()) {
				availablePlayers.add(cl);
			}
		}
		return availablePlayers;
	}

	/**
	 * Starts a new {@link Game} if availablePlayers().size() => 2. Adds this
	 * {@link Game} to these {@link ClientHandler}s.
	 */
	/*
	 * @ ensures \old(availablePlayers().size() > 1) ==> getGames().size() ==
	 * \old(getGames().size()) + 1; @
	 */
	public synchronized void checkForNewGame() {
		LinkedList<ClientHandler> notInGame = availablePlayers();

		if (notInGame.size() > 1) {
			Game game = new Game(notInGame.get(0).getPlayer(), notInGame.get(1)
							.getPlayer());
			System.out.println("New game with: "
							+ notInGame.get(0).getPlayer().getName() + " + "
							+ notInGame.get(1).getPlayer().getName());
			notInGame.get(0).newGame(game);
			notInGame.get(1).newGame(game);
			notInGame.get(0).getPlayer().addGame(game);
			notInGame.get(1).getPlayer().addGame(game);
			games.add(game);
			broadcastCommand(new Command(Protocol.START_GAME, notInGame.get(0)
							.getPlayer().getName(), notInGame.get(1).getPlayer()
							.getName()));

			game.startGame();
		}
	}

	/**
	 * Returns a {@link ClientHandler} for the given username, or
	 * <code>null</code> if it does not exist.
	 * 
	 * @param name
	 *            username of the requested ClientHandler
	 * @return ClientHandler instance with the player having the requested
	 *         username.
	 */
	public synchronized ClientHandler findClient(String name) {
		for (ClientHandler c : clients) {
			System.out.println(c.toString());
			if (c.getPlayer() != null && c.getPlayer().getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Sends the given {@link Command} to the player with the given username.
	 * Will throw an {@link InvalidCommandException} if no player with the given
	 * username exists on this {@link Server}.
	 * 
	 * @param clientName
	 *            {@link Client}'s name we want to send the {@link Command} to
	 * @param c
	 *            {@link Command}d to send
	 * @throws InvalidCommandException
	 *             On unknown username
	 */
	// @ requires c.getArgument(0) != null;
	public synchronized void sendCommand(String clientName, Command c)
			throws InvalidCommandException {
		ClientHandler addressee = findClient(clientName);
		if (addressee != null) {
			addressee.sendCommand(c);
		} else {
			throw new InvalidCommandException();
		}
	}

	/**
	 * Removes the given {@link ClientHandler}.
	 * 
	 * @param ch
	 */
	// @ requires getClients().contains(ch);
	public void removeClient(ClientHandler ch) {
		clients.remove(ch);
	}

	/**
	 * Stops the {@link Server}, closing all the connections with the Players
	 * gracefully through {@link ClientHandler.closeConnection()}. It also
	 * closes the {@link ServerSocket} associated after a second.
	 */
	public void stopServer() {
		exit = true;
		for (ClientHandler ch : clients) {
			ch.closeConnection();
		}
		try {
			Thread.sleep(1000);
			ssock.close();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for the {@link ServerSocket} UML.
	 * @return {@link ServerSocket} instance.
	 */
	// @pure
	public ServerSocket getServerSocket() {
		return ssock;
	}

	/**
	 * Getter for the {@link Game}s UML.
	 * @return {@link LinkedList} instance with {@link Game}s.
	 */
	// @pure
	public LinkedList<Game> getGames() {
		return games;
	}

	/**
	 * Whether the client will exit UML.
	 * @return .
	 */
	// @pure
	public boolean isExiting() {
		return exit;
	}

	/**
	 * Getter for all Clients UML.
	 * @return {@link LinkedList} instance with {@link ClientHandler}s
	 */
	// @pure
	public LinkedList<ClientHandler> getClients() {
		return clients;
	}
}
