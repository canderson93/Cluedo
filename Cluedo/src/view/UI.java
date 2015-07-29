package view;

import controller.Game;

/**
 * A class for interacting with players for the Cluedo Game,
 *
 */
public class UI {
	
	public static void startNewGame(String filename){
		new Game(filename);
	}
	
	public static void main(String args[]){
		startNewGame("board.txt");
	}
}