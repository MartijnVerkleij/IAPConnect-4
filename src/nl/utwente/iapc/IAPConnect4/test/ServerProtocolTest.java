package nl.utwente.iapc.IAPConnect4.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import nl.utwente.iapc.IAPConnect4.core.networking.Protocol;
import nl.utwente.iapc.IAPConnect4.server.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerProtocolTest {

	public Server server;
	public Socket clientSocket;
	public BufferedWriter writer;
	public BufferedReader reader;
	public Socket clientSocket2;
	public BufferedWriter writer2;
	public BufferedReader reader2;

	@Before
	public void setUp() {
		server = new Server(2026);
		server.start();

		try {
			clientSocket = new Socket(InetAddress.getByName("localhost"), 2026);
			reader = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(
					clientSocket.getOutputStream()));

		} catch (IOException e) {
			fail("Exception while opening client-connection");
		}

		try {
			clientSocket2 = new Socket(InetAddress.getByName("localhost"), 2026);
			reader2 = new BufferedReader(new InputStreamReader(
					clientSocket2.getInputStream()));
			writer2 = new BufferedWriter(new OutputStreamWriter(
					clientSocket2.getOutputStream()));

		} catch (IOException e) {
			fail("Exception while opening client-connection");
		}
	}

	@Test
	public void testLogin() {
		try {
			// HashMap<String, String> test
			writer.write(Protocol.JOIN.toString() + " test 20\n");
			writer.flush();
			String accept = reader.readLine();
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26",
							accept);
		} catch (IOException e) {
			fail("Exception while testing conversation");
		}
	}

	@Test
	public void testLogin1() {
		try {
			writer.write(Protocol.JOIN.toString() + " test 20 bla\n");
			writer.flush();
			String accept1arg = reader.readLine();
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26",
							accept1arg);
		} catch (IOException e) {
			fail("Exception while testing conversation");
		}
	}

	@Test
	public void testLogin2() {
		try {
			writer.write(Protocol.JOIN.toString() + " test 20 bla 20000000\n");
			writer.flush();
			String accept2arg = reader.readLine();
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26",
							accept2arg);
		} catch (IOException e) {
			fail("Exception while testing conversation");
		}
	}

	@Test
	public void testLoginF() {
		try {
			writer.write(Protocol.JOIN.toString() + " 22\n");
			writer.flush();
			String acceptFarg = reader.readLine();
			assertEquals("Accept message", Protocol.ERROR.toString() + " 002",
							acceptFarg);

		} catch (IOException e) {
			fail("Exception while testing conversation");
		}
	}

	@Test
	public void testGame() {
		try {
			writer.write(Protocol.JOIN.toString() + " Martijn 26\n");
			writer.flush();
			String acceptArg = reader.readLine();
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26",
							acceptArg);
			writer.write(Protocol.READY_FOR_GAME.toString() + "\n");
			writer.flush();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			writer2.write(Protocol.JOIN.toString() + " Axel 26\n");
			writer2.flush();
			String acceptArg2 = reader2.readLine();
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26",
							acceptArg2);
			writer2.write(Protocol.READY_FOR_GAME.toString() + "\n");
			writer2.flush();

			String gameArg = reader.readLine();
			String gameArg2 = reader2.readLine();

			assertEquals("Game message", Protocol.START_GAME.toString()
							+ " Martijn Axel", gameArg);
			assertEquals("Game message", Protocol.START_GAME.toString()
							+ " Martijn Axel", gameArg2);

			String moveArg = reader.readLine();
			String moveArg2 = reader2.readLine();

			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Martijn", moveArg);
			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Martijn", moveArg2);

			writer.write(Protocol.DO_MOVE + " 3\n");
			writer.flush();

			String doneMoveArg = reader.readLine();
			String doneMoveArg2 = reader2.readLine();

			assertEquals("move done 1 message", Protocol.DONE_MOVE.toString()
							+ " Martijn 3", doneMoveArg);
			assertEquals("move done 1 message", Protocol.DONE_MOVE.toString()
							+ " Martijn 3", doneMoveArg2);

			String move2Arg = reader.readLine();
			String move2Arg2 = reader2.readLine();

			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Axel", move2Arg);
			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Axel", move2Arg2);

			writer2.write(Protocol.DO_MOVE + " 2\n");
			writer2.flush();

			String doneMove2Arg = reader.readLine();
			String doneMove2Arg2 = reader2.readLine();

			assertEquals("move done 2 message", Protocol.DONE_MOVE.toString()
							+ " Axel 2", doneMove2Arg);
			assertEquals("move done 2 message", Protocol.DONE_MOVE.toString()
							+ " Axel 2", doneMove2Arg2);

			String move3Arg = reader.readLine();
			String move3Arg2 = reader2.readLine();

			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Martijn", move3Arg);
			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Martijn", move3Arg2);

			writer.write(Protocol.DO_MOVE + " 3\n");
			writer.flush();

			String doneMove3Arg = reader.readLine();
			String doneMove3Arg2 = reader2.readLine();

			assertEquals("move done 1 message", Protocol.DONE_MOVE.toString()
							+ " Martijn 3", doneMove3Arg);
			assertEquals("move done 1 message", Protocol.DONE_MOVE.toString()
							+ " Martijn 3", doneMove3Arg2);

			String move4Arg = reader.readLine();
			String move4Arg2 = reader2.readLine();

			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Axel", move4Arg);
			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Axel", move4Arg2);

			writer2.write(Protocol.DO_MOVE + " 2\n");
			writer2.flush();

			String doneMove4Arg = reader.readLine();
			String doneMove4Arg2 = reader2.readLine();

			assertEquals("move done 2 message", Protocol.DONE_MOVE.toString()
							+ " Axel 2", doneMove4Arg);
			assertEquals("move done 2 message", Protocol.DONE_MOVE.toString()
							+ " Axel 2", doneMove4Arg2);

			String move5Arg = reader.readLine();
			String move5Arg2 = reader2.readLine();

			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Martijn", move5Arg);
			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Martijn", move5Arg2);

			writer.write(Protocol.DO_MOVE + " 3\n");
			writer.flush();

			String doneMove5Arg = reader.readLine();
			String doneMove5Arg2 = reader2.readLine();

			assertEquals("move done 1 message", Protocol.DONE_MOVE.toString()
							+ " Martijn 3", doneMove5Arg);
			assertEquals("move done 1 message", Protocol.DONE_MOVE.toString()
							+ " Martijn 3", doneMove5Arg2);

			String move6Arg = reader.readLine();
			String move6Arg2 = reader2.readLine();

			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Axel", move6Arg);
			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Axel", move6Arg2);

			writer2.write(Protocol.DO_MOVE + " 2\n");
			writer2.flush();

			String doneMove6Arg = reader.readLine();
			String doneMove6Arg2 = reader2.readLine();

			assertEquals("move done 2 message", Protocol.DONE_MOVE.toString()
							+ " Axel 2", doneMove6Arg);
			assertEquals("move done 2 message", Protocol.DONE_MOVE.toString()
							+ " Axel 2", doneMove6Arg2);

			String move7Arg = reader.readLine();
			String move7Arg2 = reader2.readLine();

			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Martijn", move7Arg);
			assertEquals("1move message", Protocol.REQUEST_MOVE.toString()
							+ " Martijn", move7Arg2);

			writer.write(Protocol.DO_MOVE + " 3\n");
			writer.flush();

			String doneMove7Arg = reader.readLine();
			String doneMove7Arg2 = reader2.readLine();

			assertEquals("move done 1 message", Protocol.DONE_MOVE.toString()
							+ " Martijn 3", doneMove7Arg);
			assertEquals("move done 1 message", Protocol.DONE_MOVE.toString()
							+ " Martijn 3", doneMove7Arg2);

			String winArg = reader.readLine();
			String winArg2 = reader2.readLine();

			assertEquals("1move message", Protocol.GAME_END.toString()
							+ " Martijn", winArg);
			assertEquals("1move message", Protocol.GAME_END.toString()
							+ " Martijn", winArg2);

		} catch (IOException e) {
			fail("Exception while testing game");
		}
	}

	@After
	public void closeConnections() {
		server.stopServer();
		try {
			writer.close();
			reader.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
