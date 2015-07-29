package controller;

import model.Board;
/**
 * The main class for controlling game logic
 *
 */
public class Game {
	private final Player[] players;
	private Board board;
	
	public Game(Player[] players){
		if (players.length > 6 || players.length < 3){
			throw new UnsupportedOperationException("There must be between 3 and 6 players");
		}
		
		this.players = players;
		board = new Board();
	}
}
