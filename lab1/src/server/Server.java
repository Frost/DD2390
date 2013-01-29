package server;

import java.io.IOException;
import java.net.ServerSocket;

import server.ClientConnectionHandler;

/**
 * Main class for the chat server.
 * Starts up a server on localhost and listens for connections.
 * 
 * @author Martin Frost
 * @author Cecilia Roes
 *
 */
public class Server {
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		ServerSocket socket = null;
		ClientConnectionHandler handler = new ClientConnectionHandler();
		try {
			socket = new ServerSocket(4711);
			boolean listening = true;
			
			System.out.println("Server started. Waiting for clients.");
			
			while(listening){
				handler.connectClient(socket.accept());
			}
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
