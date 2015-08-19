package model.tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import controller.Player;

/**
 * A representation of a space on the cluedo board
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
			return player.getKey();
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
	 * Draws this tile onto the graphics pane
	 * @param g
	 */
	public abstract void draw(Graphics g, int x, int y);
	
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
		if (this.player != null){return false;}
		this.player = p;
		p.setTile(this);
		return true;
	}
	
	public boolean setPlayer(Player p, Image img, boolean destination){
		if (this.player != null){return false;}
		this.player = p;
		p.setTile(this);
		return true;
	}
	
	/**
	 * Removes the player from the room, if present
	 * @param p
	 */
	public void removePlayer(Player p){
		if (this.player == p){
			this.player = null;
		}
	}
	
	/**
	 * Returns the euclidean distance between two tiles
	 * @param other
	 * @return
	 */
	public double getDistance(Tile other){
		if (other instanceof Room){
			double min = Integer.MAX_VALUE;
			
			for (Door d : ((Room)other).getEntrances()){
				min = Math.min(min, d.getDistance(other));
			}
			
			return min;
		}
		
		return Math.sqrt(Math.pow(this.x-other.x, 2)+Math.pow(this.y-other.y, 2));
	}
	
	//getters and setters
	public int getX(){return x;}
	public int getY(){return y;}
	public Player getPlayer(){return player;}
}
