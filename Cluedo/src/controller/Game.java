package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Board;
import model.cards.*;
import model.tiles.Room;
import model.Weapons;
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
		this.board = Board.parseBoard(filename);
		this.gameComplete = false;
		this.solution = new ArrayList<Card>();
		this.restOfDeck = new ArrayList<Card>();
		this.players = new ArrayList<Player>();
		loadCards();
		createPlayers(numPlayers);
		this.current = this.players.get(0);
		dealRemainder();
	}

	/**
	 * Load all of the cards into lists and add three random cards to the solution. Add the remainder to the rest of the deck list
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
		//create a list of all of the character cards
		List<Card> dealingPlayers = new ArrayList<Card>();
		for(int i = 0; i < 6; i++){
			dealingPlayers.add(restOfDeck.get(i));
		}	
		//create players with random characters chosen from the list of characters
		for(int i = 0; i < numPlayers; i++){	
			String temp = dealingPlayers.remove(new Random().nextInt(dealingPlayers.size())).getValue(); 
			//create a list of all of the cards (including the solution)
			List<Card> allCards = this.solution;
			allCards.addAll(this.restOfDeck);
			//create specified number of players with their random character card and give them a list of all of the possible cards
			Player p = new Player(temp,temp.charAt(0), allCards);
			//add each player to the board and a list of all of the players
			this.board.addPlayer(p);
			players.add(p);
		}
	}
	/**
	 * Randomly assigns a card from the rest of the deck to each player until there are none left
	 *	
	 * */
	private void dealRemainder(){
		
		while(!this.restOfDeck.isEmpty()){
			for(Player p: this.players){
				if(this.restOfDeck.size() > 0){
					p.setCards(this.restOfDeck.remove(new Random().nextInt(this.restOfDeck.size())));
				}
			}	
		}		
	}
	
	/**
	 * Generates a random roll and updates the current player 
	 *	
	 */
	public void nextRound(){
		
		this.roll = new Random().nextInt(10) + 2;
		this.rollCount = roll;
		this.current = nextPlayer();
	}
	/**
	 * Returns the next viable player
	 *	@return Player
	 */
	private Player nextPlayer() {
		
		this.current = this.players.get((players.indexOf(current) + 1) % players.size());
		int count = this.players.size() - 1; //keep a count to avoid an infinite loop in the case of no players left in the game
		//while the next player in the round is out of the game, go to the next player
		while(!current.isPlaying() && count!= 0){
			this.current = this.players.get((players.indexOf(current) + 1) % players.size());
			count--;
		}		
		return this.current;
	}
	/**
	 * Keeps track of how many rolls the player has left, and then moves the player in the direction they desire if valid, returning appropriate status
	 *	@param direction 
	 *	@return String 
	 */ 
	public String move(Direction d){
		
		if(this.rollCount == 0){ return "You have no more rolls"; }
		if(!this.board.move(this.current, d)){
			return "Can't go dat way";
		}
		this.rollCount--;
		return "you have " + rollCount + " rolls left";
	}
	/**
	 * This method allows a player to guess the solution (three cards) and return true if their correct, otherwise return false 
	 *  and remove them from the game
	 *	@param String room, String character, String weapon
	 */
	public boolean accusation(String r, String c, String w){
		
		RoomCard room = new RoomCard(new Room(r));
		CharacterCard character = new CharacterCard(c);
		WeaponCard weapon = new WeaponCard(w);
		if(this.solution.contains(room) && this.solution.contains(character) && this.solution.contains(weapon)){ return true; }
		else {
			this.current.setPlaying(false);
			return false;
		}
	}
	//public List<Card>
	
	/**
	 * Getters for game logic
	 *	
	 */
	
	public Board getBoard(){ return this.board; }
	public List<Player> getPlayers(){ return this.players; }
	public int getRoll(){ return this.roll; }
	public Player getCurrent(){ return this.current; }
	public boolean isFinished(){ return this.gameComplete; }
	
	/**
	 * For testing: Remove randomness
	 *	
	 */
	public void setRoll(int i){ this.roll = i; }
	public void setSolution(List<Card> cards){ this.solution = cards; }
	public void setCurrent(Player p){ this.current = p; }
}