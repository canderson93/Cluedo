package model.cards;

/**
 * Cards representing the different characters in the game
 * 
 * There is little difference between this and the other cards,
 * This is here purely so we're able to specify whether a card
 * is a character or not
 *
 */
public class CharacterCard extends Card {
	
	public CharacterCard(String name){
		super(name);
	}
}
