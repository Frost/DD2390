package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket;
	private ClientConnectionHandler handler;
	private PrintWriter toClient;
	private BufferedReader fromClient;
	private String nickname;
	
	/**
	 * Create a new ServerThread.
	 * 
	 * @param socket I/O to/from client.
	 * @param handler Where to send input from client.
	 */
	public ServerThread(Socket socket, ClientConnectionHandler handler) {
		this.socket = socket;
		this.handler = handler;
		try {
			toClient = new PrintWriter(socket.getOutputStream(), true);
			fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
		}
	}
	
	public final void run() {
		try {
			while(!fromClient.ready()) {}
			nickname = fromClient.readLine().trim();
			System.out.println("Client connected: " + nickname);
			
			toClient.println("Client connected, hello from server");
			toClient.flush();
			
			String line;
			
			while((line = fromClient.readLine()) != null) {
				handler.sendMessageToAllClients(nickname, line);
			}
		} catch (IOException e) {
			disconnect();
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}
	
	public void sendMessageToUser(String message) {
		toClient.println(message);
	}
	
	public void disconnect() {
		System.out.println("Client disconnected: " + nickname);
		handler.disconnectClient(this);
		try {
			fromClient.close();
			toClient.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
