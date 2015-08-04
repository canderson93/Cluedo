package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.Board.Direction;
import model.cards.Card;
import model.cards.CharacterCard;
import model.tiles.Room;
import model.tiles.Tile;
import controller.Game;
import controller.Player;

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
		
		showHelp(); //Tell the players what they can do
		
		//Start the game
		game.nextRound();
		redraw();
		
		//main game loop
		gameLoop:
		while (true){
			String in = sc.next();
			
			switch(in){
			case "u": //Up command
			case "up":
				redraw(game.move(Direction.UP));
				continue;
			case "d": //Down command
			case "down":
				redraw(game.move(Direction.DOWN));
				continue;
			case "l": //Left Command
			case "left":
				redraw(game.move(Direction.LEFT));
				continue;
			case "r": //Right command
			case "right":
				redraw(game.move(Direction.RIGHT));
				continue;
			case "s": //Shortcut command
			case "shortcut":
				redraw(game.move(Direction.WARP));
				continue;
			case "accuse": //Accusation command
				doAccusation(sc);
				if (!game.isFinished()){game.nextRound();}
				break;
			case "suggest": //Suggestion command
				System.out.println("IT WAS PROBABLY YOU!!!?!");
				continue;
			case "end": //indicate the end of the round
				game.nextRound();
				redraw("You rolled a "+game.getRoll());
				break;
			case "h":
			case "help": //Help command
				showHelp();
				continue;
			case "key": //show key command
				printKey();
				continue;
			case "q": //quit command
			case "quit":
				break gameLoop;
			case "redraw":
				break;
			default:
				System.out.println(in + "?... That sounds like something the killer would say.");
				continue;
			}
			
			if (game.isFinished()){break;} //Put this here so that the redraw doesn't happen
			redraw();
		}
		
		System.out.println("The game is over");
		
		sc.close();
	}
	
	/**
	 * Display the help menu
	 */
	public void showHelp(){
		System.out.println("\t--Commands--");
		System.out.println("help/h:\t\tshow this menu");
		System.out.println("key:\t\tshow the rooms legend");
		System.out.println("redraw:\t\tredraw the board");
		System.out.println("");
		System.out.println("accuse:\t\tguess the solution");
		System.out.println("suggest:\tmake a suggestion");
		System.out.println("end:\t\tend your turn");
		System.out.println("");
		System.out.println("up/u:\t\tmove up one space");
		System.out.println("down/d:\t\tmove down one space");
		System.out.println("left/l:\t\tmove left one space");
		System.out.println("right/r:\tmove right one space");
		System.out.println("shortcut/s:\ttake the shortcut, if present");
		System.out.println("");
	}
	
	/**
	 * Handles a player accusation 
	 */
	public void doAccusation(Scanner sc){		
		String character = selectCard(sc, game.getCharacter(), "Who was the murderer? ");
		String weapon = selectCard(sc, game.getWeapons(), "What was the weapon?");
		String room = selectCard(sc, game.getRoom(), "Where did they do it?");
		
		if (game.accusation(room, character, weapon)){
			System.out.println("That's correct! You win the game!");
		} else {
			System.out.println("That's not correct. Games up, buddy.");
		}
	}

	/**
	 * Handles a player suggestion
	 */
	public void doSuggestion(Scanner sc){
		Tile t = game.getCurrent().getTile();
		Room room;
		
		if (!(t instanceof Room)){
			System.out.println("You have to be in a room to make a suggestion");
			return;
		} else {
			room = (Room)t;
		}
		
		String character = selectCard(sc, game.getCharacter(), "Who was the murderer? ");
		String weapon = selectCard(sc, game.getWeapons(), "What was the weapon?");
		
		Card matched = game.suggestion(character, weapon, room);
		if (matched == null){
			System.out.println("Nobody had any cards from your suggestion");
		} else {
			System.out.println("You were shown "+toTitleCase(matched.getValue()));
		}
	}
	
	/**
	 * Prints a list of cards, and returns the string of the selected card
	 * 
	 * @param sc
	 * @param msg The message to 
	 * @return
	 */
	private String selectCard(Scanner sc, List<? extends Card> cards, String msg){
		int index = 0; //the index the character selects
		
		System.out.println(msg);
		for (int i = 0; i < cards.size() ; i++){
			System.out.print((i+1)+":\t"+toTitleCase(cards.get(i).getValue()));
			
			if (!game.getCurrent().getUnseenCards().contains(cards.get(i))){
				System.out.print(" *** Seen");
			}
			
			System.out.print("\r\n");
		}
		
		//Try get user input until a valid response is given
		while (sc.hasNext()){
			try{
				index = Integer.parseInt(sc.next());
				
				//If it's a valid input, break out of the loop
				if (index > 0 && index <= cards.size()){
					break;
				}
				
				System.out.println("You have to select a number between 1 and " + cards.size());
			} catch (NumberFormatException e){
				System.out.println("Input the number of the character to select");
			}
		}
				
		return cards.get(index-1).getValue();
	}
	
	/**
	 * Prints a list of all the rooms, and their associated keys
	 */
	public void printKey(){
		List<Room> rooms = game.getBoard().getRooms();
		
		System.out.println("Rooms:");
		for (Room r : rooms){
			System.out.println(r.getKey() + ": " + r.getName());
		}
	}
	
	/**
	 * Redraws the board view, and present special options
	 */
	public void redraw(){
		Player p = game.getCurrent();
		System.out.print(game.getBoard().toString());
		System.out.println("Player: "+toTitleCase(p.getName())+ " ("+p.getKey()+")");
		if (p.getTile() instanceof Room){
			Room r = (Room)p.getTile();
			System.out.println("You are in the "+r.getName());
		}
		
		System.out.print("Input command, or h for help: ");
	}
	
	/**
	 * Redraws the board view, showing a message beneath it
	 * @param msg
	 */
	public void redraw(String msg){
		redraw();
		System.out.println(msg);
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
			char c = orig.charAt(i);
			
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
				firstChar = false;
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
				System.out.print("That isn't a valid suggestion. Try a number next time.");
			}
		}
		
		sc.close();
	}
}