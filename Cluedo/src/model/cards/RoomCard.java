package model.cards;

/**
 * Cards representing the different rooms in the game
 * 
 * There is little difference between this and the other cards,
 * This is here purely so we're able to specify whether a card
 * is a room or not
 *
 * @author Carl
 */
public class RoomCard extends Card {	
	
	public RoomCard(String room){
		super(room);
	}
}
