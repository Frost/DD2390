package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Simple guessing game.
 * 
 * @author Cecilia Roes, Martin Frost
 */
public class HttpServer{
	// TODO: Make sure it's possible to submit a guess
	// TODO: Not guessing should not print "YOU MADE IT!"
	// TODO: The server should not crash
	// TODO: Below code is ugly :(
	
	public static void main(String[] args) throws IOException{
		System.out.println("Skapar Serversocket");
		ServerSocket ss = new ServerSocket(8080);
		ArrayList<Session> sessions = new ArrayList<Session>();
		
		boolean listening = true;
		while(listening){
			System.out.println("Väntar på klient...");
			Socket s = ss.accept();
			System.out.println("Klient är ansluten");
			BufferedReader request =
					new BufferedReader(new InputStreamReader(s.getInputStream()));
			String str = request.readLine();
			System.out.println(str);
			StringTokenizer tokens =
					new StringTokenizer(str," ?");
			tokens.nextToken(); // Ordet GET
			String requestedDocument = tokens.nextToken();
			int guess = getGuessParam(tokens.nextToken());
			int sessionId = -1;
			System.out.println("Requested document: " + requestedDocument);
			while( (str = request.readLine()) != null && str.length() > 0){
				if (str.startsWith("Cookie: ")) {
					sessionId = getSessionId(str);
				}
				System.out.println(str);
			}
			System.out.println("Förfrågan klar.\n");
			s.shutdownInput();
			
			if (sessionId == -1 || sessions.size() <= sessionId) {
				sessionId = sessions.size();
				sessions.add(new Session());
			}

			PrintStream response =
					new PrintStream(s.getOutputStream());
			response.println("HTTP/1.1 200 OK");
			response.println("Server : Slask 0.1 Beta");
			if(requestedDocument.indexOf(".html") != -1)
				response.println("Content-Type: text/html");
			if(requestedDocument.indexOf(".gif") != -1)
				response.println("Content-Type: image/gif");

			response.println("Set-Cookie: clientId=" + sessionId +
					"; expires=Wednesday,31-Dec-13 21:00:00 GMT");

			response.println();
			
			response.println(
					"<html><body><form action=\"guess.html\" method=\"get\">" +
					"<p>Guess a number between 1 and 100!</p>");
			
			if(guess > 0) {
				Session session = sessions.get(sessionId);
				session.numberOfGuesses++;
				if(session.answer == guess) {
					response.println("<p>Correct! You won after " + 
									 session.numberOfGuesses +" tries</p>");
					sessions.set(sessionId, new Session());
				} else {
					if (session.answer < guess) {
						response.println("<p>Wrong, guess lower!</p>");
					} else {
						response.println("<p>Wrong, guess higher!</p>");
					}
					response.println("<p>Number of guesses: " + session.numberOfGuesses + "</p>");
				}
			}
			
			response.println(
					"<input id=\"guess\" name=\"guess\" type=\"text\">" +
					"<input type=\"submit\" value=\"Guess!\" /></form>" +
					"<script>document.getElementById('guess').focus();</script>" +
					"</body></html>");
			
			s.shutdownOutput();
			s.close();
		}
		ss.close();
	}

	private static int getGuessParam(String paramString) {
		for (String paramPair : paramString.split("&")) {
			String[] pair = paramPair.split("=");
			if (pair[0].equals("guess")) {
				return Integer.parseInt(pair[1]);
			}
		}
		return -1;
	}
	
	private static int getSessionId(String cookieString) {
		for (String cookie : cookieString.split(" ")) {
			String[] pair = cookie.split("=");
			if (pair[0].equals("clientId")) {
				return Integer.parseInt(pair[1]);
			}
		}
		return -1;
	}
}