package server;

import java.util.Random;

public class Session {
	public int numberOfGuesses;
	public int answer;
	
	public Session() {
		numberOfGuesses = 0;
		answer = new Random().nextInt(100) + 1;
	}
}
