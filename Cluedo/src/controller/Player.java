package controller;

import model.tiles.Tile;

/**
 * A player class for the Cluedo Game
 * @author Carl
 *
 */
public class Player {
	private final String name;
	private final char key;
	
	private Tile tile; //The tile the player is currently standing on
	
	public Player(String name, char key){
		this.name = name;
		this.key = key;
	}
	
	
	public String getName(){return name;}
	public char getKey(){return key;}
	public Tile getTile(){return tile;}
	
	public void setTile(Tile t){this.tile = t;}
}
