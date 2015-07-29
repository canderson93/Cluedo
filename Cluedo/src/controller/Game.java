package controller;

import model.Board;
/**
 * The main class for controlling game logic
 *
 */
public class Game {
	private Board board;
	
	public Game(String filename){
		this.board = new Board(filename);
	}
}
