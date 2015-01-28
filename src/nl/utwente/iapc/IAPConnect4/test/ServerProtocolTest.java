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
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			
		} catch (IOException e) {
			fail("Exception while opening client-connection");
		}
		
		try {
			clientSocket2 = new Socket(InetAddress.getByName("localhost"), 2026);
			reader2 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writer2 = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			
		} catch (IOException e) {
			fail("Exception while opening client-connection");
		}
	}

	@Test
	public void testLogin() {
		try {
//			HashMap<String, String> test
			writer.write(Protocol.JOIN.toString() + " test 20\n");
			writer.flush();
			String accept = reader.readLine();
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26", accept);
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
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26", accept1arg);
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
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26", accept2arg);
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
			assertEquals("Accept message", Protocol.ERROR.toString() + " 002", acceptFarg);
			
		} catch (IOException e) {
			fail("Exception while testing conversation");
		}
	}
	
	@Test
	public void testGameStart() {
		try {
			writer.write(Protocol.JOIN.toString() + "Martijn 26\n");
			writer.flush();
			String acceptArg = reader.readLine();
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26", acceptArg);
			writer.write(Protocol.READY_FOR_GAME.toString() + "\n");
			
			writer2.write(Protocol.JOIN.toString() + "Axel 26\n");
			writer2.flush();
			String acceptArg2 = reader2.readLine();
			assertEquals("Accept message", Protocol.ACCEPT.toString() + " 26", acceptArg2);
			writer2.write(Protocol.READY_FOR_GAME.toString() + "\n");
			
			String gameArg = reader.readLine();
			String gameArg2 = reader2.readLine();
			
			assertEquals("Game message", Protocol.START_GAME.toString() + " Martijn Axel", gameArg);
			assertEquals("Game message", Protocol.START_GAME.toString() + " Martijn Axel", gameArg2);
			
			
			
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
