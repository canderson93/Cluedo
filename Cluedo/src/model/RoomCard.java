package model;

/**
 * Cards representing the different weapons in the game
 * 
 * There is little difference between this and the other cards,
 * This is here purely so we're able to specify whether a card
 * is a room or not
 *
 */
public class RoomCard extends Card {	
	
	public RoomCard(String name){
		super(name);
	}
}
