package view;

import java.util.Scanner;

import controller.Game;

/**
 * A class for interacting with players for the Cluedo Game,
 *
 */
public class UI {
	
	public static void startNewGame(String filename, int numPlayers){
		new Game(filename, numPlayers);
	}
	
	public static void main(String args[]){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome! How many players between 3 and 6 do y'all wna play with?");
		String numPlayers = sc.next();
		int num = Integer.parseInt(numPlayers);
		startNewGame("board.txt", num);
		sc.close();
	}
}