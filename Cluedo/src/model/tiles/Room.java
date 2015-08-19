package model.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Board;
import model.Weapons;
import controller.Player;

/**
 * A class to represent a room in the cluedo game.
 * This can hold multiple players, unlike other Tile classes.
 * @author Carl
 *
 */
public class Room extends Tile {
	public static Color color = new Color(240, 240, 210);
	public static Color blankColor = new Color(70, 190, 200);
	
	//The name of the room, and the letter to represent it on the board
	private final String name;
	private List<Door> entrances = new ArrayList<Door>();
	private Set<Player> players = new HashSet<Player>(); //The players currently inside this room
	private Set<Weapons> weapons = new HashSet<Weapons>();

	//Room bounding box
	int minX = -1;
	int minY = -1;
	int maxX = 0;
	int maxY = 0;
	
	public Room(String name, char key){
		super(key, -1, -1); //don't have x and y positions for this
		this.name = name;
	}
	
	public Room(String name){
		this(name, name.charAt(0));
	}
	
	/**
	 * Specify a door as an entry point to this room
	 * @param door
	 */
	public void addEntrance(Door door){
		entrances.add(door);
		door.setRoom(this);
	}
	
	/**
	 * Determine whether a door belongs to this room
	 * @param door
	 * @return
	 */
	public boolean containsEntrance(Door door){
		return entrances.contains(door);
	}
	
	/**
	 * Add a weapon to this room
	 * @param wep
	 */
	public void addWeapon(Weapons wep){
		weapons.add(wep);
	}
	
	/**
	 * Remove the weapon from the room
	 * @param wep
	 */
	public void removeWeapon(Weapons wep){
		weapons.remove(wep);
	}
	
	/**
	 * Determines whether the room contains a weapon
	 * @param wep
	 * @return
	 */
	public boolean containsWeapon(Weapons wep){
		return weapons.contains(wep);
	}
	
	/**
	 * Adds a coordinate as a room location
	 * @param x
	 * @param y
	 */
	public void addLocation(int x, int y){
		//Update the bounding box variables
		if (x < this.minX || this.minX == -1){
			this.minX = x;
		} else if (x > this.maxX){
			this.maxX = x;
		}
		
		if (y < this.minY || this.minY == -1) {
			this.minY = y;
		} else if (y > this.maxY){
			this.maxY = y;
		}
	}
	
	@Override
	public boolean canMoveTo(Tile tile) {
		if (tile == this){return true;}
		//Check to see if any of our entrances can reach the tile
		for (Door d : entrances){
			if (d.canMoveTo(tile)){
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		int size = Board.tileSize;
		
		g.setColor(color);
		if (name.equals("Blank")){g.setColor(blankColor);}
		g.fillRect(x*size, y*size, size, size);
	}
	
	//Getters and setters
	public String getName(){return name;}
	public List<Door> getEntrances(){return new ArrayList<Door>(entrances);}
	
	@Override
	public char getKey(){return this.key;}

	@Override
	public boolean setPlayer(Player p){
		if (players.add(p)){
			p.setTile(this);
			return true;
		}
		return false;
	}
	
	@Override
	public void removePlayer(Player p){
		players.remove(p);
	}
	
	@Override
	public int getX(){return minX;}
	
	@Override
	public int getY(){return minY;}
	
	public int getWidth(){return maxX-minX+1;}
	public int getHeight(){return maxY-minY+1;}
}
