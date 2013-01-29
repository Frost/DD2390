package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket("127.0.0.7", 4711);
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader fromServer  = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("What is your nickname? ");
			String nickname = fromUser.readLine().trim();
			
			toServer.println(nickname);
			
			boolean listening = true;
			
			while(listening) {
				String inputFromServer;
				
				if(fromServer.ready()) {
					inputFromServer = fromServer.readLine();
					if(inputFromServer != null) {
						System.out.println(inputFromServer);
					}
				}
				
				if(fromUser.ready()) {
					String userInput = fromUser.readLine();
					if (userInput.equals("exit")) {
						break;
					}
					toServer.println(userInput);
				}
			}
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}