package controller;
import java.util.ArrayList;
import java.util.List;

import model.cards.*;

import model.tiles.Tile;

/**
 * A player class for the Cluedo Game
 * @author Carl
 *
 */
public class Player {
	private final String name;
	private final char key;
	private List<Card> cards;
	
	private Tile tile; //The tile the player is currently standing on
	
	public Player(String name, char key){
		this.name = name;
		this.key = key;
		cards = new ArrayList<Card>();
	}
	
	
	public String getName(){return name;}
	public char getKey(){return key;}
	public Tile getTile(){return tile;}
	public void setTile(Tile t){this.tile = t;}
}
