package model.cards;

import model.tiles.Room;

/**
 * Cards representing the different rooms in the game
 * 
 * There is little difference between this and the other cards,
 * This is here purely so we're able to specify whether a card
 * is a room or not
 *
 */
public class RoomCard extends Card {	
	private Room room; //the related room object
	
	public RoomCard(Room room){
		super(room.getName());
		this.room = room;
	}
}
