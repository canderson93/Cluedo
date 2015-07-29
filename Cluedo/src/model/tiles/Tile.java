package model.tiles;

import controller.Player;

/**
 * The tile 
 * @author Carl
 *
 */
public abstract class Tile {
	//The key to represent the character in the console
	private final char key;
	private Player player = null;
	
	protected Tile(char key){
		this.key = key;
	}
	
	/**
	 * Gives the key used to represent this tile
	 * @return
	 */
	public char key(){
		//If this tile contains a player, show the player instead
		if (player != null){
			return player.key();
		}
				
		return this.key;
	}
}
