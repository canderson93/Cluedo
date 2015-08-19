package controller;
import java.awt.Image;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.List;

import view.BoardCanvas;
import model.cards.*;
import model.tiles.Room;
import model.tiles.Tile;

/**
 * A player class for the Cluedo Game
 * @author Carl and Chloe
 *
 */
public class Player {
	private final Image img;
	private final String character;
	private final char key;
	private List<Card> hand = new ArrayList<Card>();
	private List<Card> unseenCards; //cards the player hasn't seen, this is updated throughout the game
	private boolean playing = true;
	private Room lastSuggestion = null;
	private Tile tile; //The tile the player is currently standing on
	
	public Player(String name, char key, List<Card> allCards){
		this.character = name;
		this.key = key;
		this.unseenCards = allCards;
		this.img = BoardCanvas.loadImage("characters/"+name.toLowerCase()+".png");
	}
	
	/**
	 * Adds a card to the players hand
	 * @param c
	 */
	public void addCard(Card c){ 
		this.hand.add(c); 
		this.unseenCards.remove(c);
	}
	
	/**
	 * Removes a card from the players unseen list
	 * @param c
	 */
	public void showCard(Card c){ 
		this.unseenCards.remove(c);
	}
	
	/*
	 * Getters
	 */
	public String getName(){return this.character; }
	public char getKey(){ return this.key; }
	public Tile getTile(){ return this.tile; }
	public boolean isPlaying(){return this.playing; }
	public List<Card> getUnseenCards(){ return this.unseenCards; }
	public List<Card> getHand(){ return this.hand; }
	public Room getLastSuggestion(){ return this.lastSuggestion; }
	public Image getImage(){return img;}
	
	/*
	 * Setters
	 */
	public void setTile(Tile t){ this.tile = t; }
	public void setPlaying(boolean b) { this.playing = b; }
	public void setLastSuggestion(Room r){this.lastSuggestion = r;}
}
