package model.cards;

/**
 * An abstract class for representing the card objects held by players,
 * and used to represent the solutions
 *
 */
public abstract class Card {
	private final String value;
	
	public Card(String value){
		this.value = value;
	}
	
	/**
	 * Gets the face value of the card
	 * @return
	 */
	public String getValue(){
		return value;
	}
}
