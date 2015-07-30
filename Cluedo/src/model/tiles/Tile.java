package model.tiles;

import controller.Player;

/**
 * The tile 
 * @author Carl
 *
 */
public abstract class Tile {
	//The key to represent the character in the console
	protected final char key;
	protected Player player = null;
	
	protected final int x;
	protected final int y;
	
	protected Tile(char key, int x, int y){
		this.key = key;
		this.x = x;
		this.y = y;
	}
	
	
	
	/**
	 * Gives the key used to represent this tile
	 * @return
	 */
	public char getKey(){
		//If this tile contains a player, show the player instead
		if (player != null){
			return player.key();
		}
				
		return this.key;
	}
	
	/**
	 * Checks whether this tile can move to another tile
	 * @param tile
	 * @return
	 */
	public abstract boolean canMoveTo(Tile tile);
	
	/**
	 * Checks whether this room contains a player
	 * @return
	 */
	public boolean containsPlayer(){
		return player != null;
	}
	
	/**
	 * Attempts to set the player on this tile
	 * @param p
	 * @return whether setting the player was successful
	 */
	public boolean setPlayer(Player p){
		if (player != null){return false;}
		
		this.player = p;
		return true;
	}
	
	//getters and setters
	public int getX(){return x;}
	public int getY(){return y;}
	public Player getPlayer(){return player;}
}
