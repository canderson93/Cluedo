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
	
	protected Tile(char key){
		this.key = key;
	}
	
	/**
	 * Determines whether this tile accepts a move from another tile given
	 * a specific roll
	 * 
	 * @return
	 */
	public abstract boolean canBeReached(Tile tile, int roll);
	
	/**
	 * Accepts the players move, and updates the board
	 * @param tile
	 * @param roll
	 */
	public abstract void acceptMove(Player player, Tile tile, int roll);
	
	/**
	 * Gives the key used to represent this tile
	 * @return
	 */
	public char key(){return this.key;}
}
