package model.cards;

/**
 * An abstract class for representing the card objects held by players,
 * and used to represent the solutions
 *
 *	@author Carl
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
	
	@Override
	//Generated
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	//Generated
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Card other = (Card) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
