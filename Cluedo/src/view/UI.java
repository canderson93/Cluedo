package view;

import java.util.Scanner;

import model.Board.Direction;
import controller.Game;

/**
 * A class for interacting with players for the Cluedo Game.
 * 
 * @author Carl
 *
 */
public class UI{
	private Game game;
	
	public UI(Game game){
		this.game = game;
	}
	
	public void startGame(){
		Scanner sc = new Scanner(System.in);
		
		//Start the game
		game.nextRound();
		redraw();
		
		//main game loop
		while (true){
			String in = sc.next();
			
			switch(in){
			case "u": //Up command
			case "up":
				System.out.println(game.move(Direction.UP));
				break;
			case "d": //Down command
			case "down":
				System.out.println(game.move(Direction.DOWN));
				break;
			case "l": //Left Command
			case "left":
				System.out.println(game.move(Direction.LEFT));
				break;
			case "r": //Right command
			case "right":
				System.out.println(game.move(Direction.RIGHT));
				break;
			case "s": //Shortcut command
			case "shortcut":
				System.out.println(game.move(Direction.WARP));
				break;
			case "accuse": //Accusation command
				System.out.println("IT WAS YOU!!!!");
				break;
			case "suggest": //Suggestion command
				System.out.println("IT WAS PROBABLY YOU!!!?!");
				break;
			case "end": //indicate the end of the round
				System.out.println("It's over, buddy");
				continue;
			case "help": //Help command
				showHelp();
				continue;
			case "key":
				printKey();
				continue;
			case "q":
			case "quit":
				break; //Leave the loop
			default:
				System.out.println(in + "?... That sounds like something the killer would say.");
				continue;
			}
			
			redraw();
		}
		
		sc.close();
	}
	
	/**
	 * Display the help menu
	 */
	public void showHelp(){
		System.out.println("\t--Commands--");
		System.out.println("help:\t\tshow this menu");
		System.out.println("up/u:\t\tmove up one space");
		System.out.println("down/d:\t\tmove down one space");
		System.out.println("left/l:\t\tmove left one space");
		System.out.println("right/r:\tmove right one space");
		System.out.println("shortcut/s:\ttake the shortcut, if present");
	}
	
	/**
	 * Handles a player accusation 
	 */
	public void doAccusation(){
		
	}
	
	/**
	 * Handles a player suggestion
	 */
	public void doSuggestion(){
		
	}
	
	public void printKey(){
		
	}
	
	/**
	 * Redraws the board view, and present special options
	 */
	public void redraw(){
		System.out.println(game.getBoard().toString());
	}
	
	/**
	 * Create a new game, loading the board from file
	 * @param filename board file name
	 * @param numPlayers number of players
	 */
	public static void startNewGame(String filename, int numPlayers){
		new UI(new Game(filename, numPlayers)).startGame();
	}
	
	/**
	 * Helper method to convert the string to title case for pretty printing.
	 *  
	 * @param orig
	 * @return
	 */
	//This is mainly used for converting enums to a printable form
	public static String toTitleCase(String orig){
		String rtn = "";
		boolean firstChar = true;
		
		for (int i = 0; i < orig.length(); i++){
			char c = orig.charAt(0);
			
			//Convert underscores to spaces, and update whether this is the first letter
			//in a word
			if (c == ' ' || c == '_'){
				firstChar = true;
				rtn += ' ';
				continue;
			}
			
			//Apply title case rules
			if (firstChar){
				rtn += Character.toUpperCase(c);
			} else {
				rtn += Character.toLowerCase(c);
			}
		}
		
		return rtn;
	}
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome! How many players between 3 and 6 do y'all wanna play with?");

		//Loop until we get a valid player count
		while (true){
			String numPlayers = sc.next();
			
			//Try convert the input to a number
			try{
				int num = Integer.parseInt(numPlayers);
				
				//Check the number is in range
				if (num < 3 || num > 6){
					System.out.println("Please choose a number between 3 and 6");
					continue;
				}
				
				startNewGame("board.txt", num);
				break; //Break the loop if everything is fine
			} catch (NumberFormatException e){
				System.out.println("That isn't a valid suggestion. Try a number next time.");
			}
		}
		
		sc.close();
	}
}