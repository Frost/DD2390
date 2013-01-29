package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread {
	private Socket socket;
	private PrintWriter toServer;
	private BufferedReader fromServer;
	private String nickname;

	public ClientThread(String nickname) {
		this.nickname = nickname;
		try {
			socket = new Socket("127.0.0.7", 4711);
			toServer = new PrintWriter(socket.getOutputStream(), true);
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		toServer.println(nickname);
		toServer.flush();

		boolean listening = true;
		try {
			Scanner fromUser = new Scanner(System.in);

			while(listening) {
				System.out.println(fromServer.readLine());

				if(fromServer.ready()) {
					String input = fromUser.nextLine();
					System.out.println(input);
					if (input.trim() == "exit") {
						join();
					}
				}
			}
			fromUser.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				fromServer.close();
				toServer.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			
		}
	}
}
