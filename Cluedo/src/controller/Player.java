package controller;
import java.util.ArrayList;
import java.util.List;

import model.cards.*;
import model.tiles.Tile;

/**
 * A player class for the Cluedo Game
 * @author Carl and Chloe
 *
 */
public class Player {
	private final String name;
	private final char key;
	private List<Card> cards;
	private List<Card> unseenCards;
	private boolean playing;
	
	private Tile tile; //The tile the player is currently standing on
	
	public Player(String name, char key, List<Card> allCards){
		this.name = name;
		this.key = key;
		this.playing = true;
		cards = new ArrayList<Card>();
		this.unseenCards = allCards;
	}
	
	/**
	 * Getters
	 */
	public String getName(){return this.name; }
	public char getKey(){ return this.key; }
	public Tile getTile(){ return this.tile; }
	public boolean isPlaying(){return this.playing; }
	public List<Card> getUnseenCards(){ return this.unseenCards; }
	public List<Card> getHand(){ return this.cards; }
	/**
	 * Setters
	 */
	public void setCards(Card c){ this.cards.add(c); }
	public void setTile(Tile t){ this.tile = t; }
	public void setPlaying(boolean b) { this.playing = b; }
}
