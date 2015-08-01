package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Board;
import model.cards.*;
import model.tiles.Room;
import model.Weapons;
import model.Rooms;
import model.Characters;
/**
 * The main class for controlling game logic
 *
 */
public class Game {
	private Board board;
	private boolean gameComplete;
	List<Card> solution;
	List<Card> restOfDeck;
	List<Player> players;
	
	
	public Game(String filename, int numPlayers){
		this.board = new Board(filename);
		this.gameComplete = false;
		this.solution = new ArrayList<Card>();
		this.restOfDeck = new ArrayList<Card>();
		this.players = new ArrayList<Player>();
		loadCards();
		createPlayers(numPlayers);
	}
	
	/**
	 * Load all of the cards into lists and add three random cards to the solution
	 *
	 */
	private void loadCards(){
		
		List<Card> weapons = new ArrayList<Card>();
		for(Weapons w: Weapons.values()){
			weapons.add(new WeaponCard(w.name()));
		}
		List<Card> characters = new ArrayList<Card>();
		for(Characters c: Characters.values()){
			characters.add(new CharacterCard(c.name()));
		}		
		List<Card> rooms = new ArrayList<Card>();
		List<Room> roomObj = new ArrayList<Room>();
		roomObj = board.getRooms();
		for(Room r: roomObj){
			rooms.add(new RoomCard(r));
		}	
		createSolution(characters);
		createSolution(weapons);
		createSolution(rooms);
		restOfDeck.addAll(characters);
		restOfDeck.addAll(weapons);
		restOfDeck.addAll(rooms);
		
	}
	
	/**
	 * Randomly generate a card from each of the three sets of cards
	 *	@param cards
	 */
	private void createSolution(List<Card> cards) {	
		Card randomItem = cards.get(new Random().nextInt(cards.size()));
		System.out.println(randomItem.getValue());
		this.solution.add(randomItem);	
	}	
	

	private void createPlayers(int numPlayers) {
		
		List<Card> dealingPlayers = new ArrayList<Card>();
		for(int i = 0; i < 6; i++){
			dealingPlayers.add(restOfDeck.get(i));
		}
		
		for(int i = 0; i < numPlayers; i++){	
			String temp = dealingPlayers.remove(new Random().nextInt(dealingPlayers.size())).getValue(); 
			players.add(new Player(temp,temp.charAt(0)));
		}
	}
}
	