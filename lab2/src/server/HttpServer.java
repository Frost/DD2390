package server;

import java.io.*;
import java.net.*;
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
			int guess = parseParamString(tokens.nextToken());
			
			System.out.println("Requested document: " + requestedDocument);
			while( (str = request.readLine()) != null && str.length() > 0){
				//System.out.println(str);
			}
			System.out.println("Förfrågan klar.\n");
			s.shutdownInput();

			PrintStream response =
					new PrintStream(s.getOutputStream());
			response.println("HTTP/1.1 200 OK");
			response.println("Server : Slask 0.1 Beta");
			if(requestedDocument.indexOf(".html") != -1)
				response.println("Content-Type: text/html");
			if(requestedDocument.indexOf(".gif") != -1)
				response.println("Content-Type: image/gif");

			response.println("Set-Cookie: clientId=1; expires=Wednesday,31-Dec-13 21:00:00 GMT");

			response.println();
			
			try {
				File f = new File("."+requestedDocument);
				FileInputStream infil = new FileInputStream(f);
				byte[] b = new byte[1024];
				while( infil.available() > 0){
					response.write(b,0,infil.read(b));
				}
				infil.close();				
			} catch (FileNotFoundException e) {
				System.out.println("File not found:" + e.getStackTrace());
			}
			s.shutdownOutput();
			s.close();
		}
		ss.close();
	}

	private static int parseParamString(String paramString) {
		for (String paramPair : paramString.split("&")) {
			String[] pair = paramPair.split("=");
			if (pair[0].equals("guess")) {
				return Integer.parseInt(pair[1]);
			}
		}
		return -1;
	}
}