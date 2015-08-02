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
import model.Board.Direction;
/**
 * The main class for controlling game logic
 * @author Chloe
 */
public class Game {
	private Board board;
	private boolean gameComplete;
	List<Card> solution;
	List<Card> restOfDeck;
	List<Player> players;
	int roll;
	int rollCount;
	Player current;
	
	
	public Game(String filename, int numPlayers){
		this.board = new Board(filename);
		this.gameComplete = false;
		this.solution = new ArrayList<Card>();
		this.restOfDeck = new ArrayList<Card>();
		this.players = new ArrayList<Player>();
		loadCards();
		createPlayers(numPlayers);
		this.current = this.players.get(0);
		//run();
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
			if(r.getName() != "Blank"){ rooms.add(new RoomCard(r)); }
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
		this.solution.add(randomItem);
	}	
	
	private void createPlayers(int numPlayers) {
		
		List<Card> dealingPlayers = new ArrayList<Card>();
		for(int i = 0; i < 6; i++){
			dealingPlayers.add(restOfDeck.get(i));
		}
		
		for(int i = 0; i < numPlayers; i++){	
			String temp = dealingPlayers.remove(new Random().nextInt(dealingPlayers.size())).getValue(); 
			Player p = new Player(temp,temp.charAt(0));
			this.board.addPlayer(p);
			players.add(p);
		}
	}

	public void nextRound(){
		
		this.roll = new Random().nextInt(6) + 1;
		this.rollCount = roll;
		this.current = this.players.get((players.indexOf(current) + 1) % players.size());
		//System.out.println("current player = " + this.current.getName());
		
	}
	
	public String move(Direction d){
		
		if(this.rollCount == 0){ return "You have no more rolls"; }
		if(!this.board.move(this.current, d)){
			return "Can't go dat way";
		}
		this.rollCount--;
		return "you have " + rollCount + " rolls left";
	}
	
	/**
	 * Getters for game logic
	 *	
	 */
	
	public Board getBoard(){ return this.board; }
	
	public List<Player> getPlayers(){ return this.players; }
	
	public int getRoll(){ return this.roll; }
	
	public Player getCurrent(){ return this.current; }
	
	public boolean isFinished(){ return this.gameComplete; }
	
}