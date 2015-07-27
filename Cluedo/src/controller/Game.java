package controller;

/**
 * The main class for controlling game logic
 *
 */
public class Game {
	private final Player[] players;
	
	public Game(Player[] players){
		if (players.length > 6 || players.length < 3){
			throw new UnsupportedOperationException("There must be between 3 and 6 players");
		}
		
		this.players = players;
	}
}
