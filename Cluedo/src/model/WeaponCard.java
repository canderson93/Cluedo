package model;

/**
 * Cards representing the different weapons in the game
 * 
 * There is little difference between this and the other cards,
 * This is here purely so we're able to specify whether a card
 * is a weapon or not
 *
 */
public class WeaponCard extends Card {
	
	public WeaponCard(String type){
		super(type);
	}
}
