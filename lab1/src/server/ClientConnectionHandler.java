package server;

import java.net.Socket;
import java.util.ArrayList;

public class ClientConnectionHandler {
	private ArrayList<ServerThread> clients;
	
	public ClientConnectionHandler() {
		clients = new ArrayList<ServerThread>();
	}

	public void connectClient(Socket socket) {
		ServerThread client = new ServerThread(socket, this);
		clients.add(new ServerThread(socket, this));
		client.start();
	}
	
	public void disconnectClient(ServerThread thread) {
		clients.remove(thread);
	}

	public void sendMessageToAllClients(String line) {
		for (ServerThread client : clients) {
			client.sendMessageToUser(line);
		}
	}
}
