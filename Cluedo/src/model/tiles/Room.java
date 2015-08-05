package model.tiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Weapons;
import controller.Player;

public class Room extends Tile {
	//The name of the room, and the letter to represent it on the board
	private final String name;
	private List<Door> entrances = new ArrayList<Door>();
	private Set<Player> players = new HashSet<Player>(); //The players currently inside this room
	private Set<Weapons> weapons = new HashSet<Weapons>();

	
	public Room(String name, char key){
		super(key, -1, -1); //don't have x and y positions for this
		this.name = name;
	}
	
	public Room(String name){
		this(name, name.charAt(0));
	}
	
	public void addEntrance(Door door){
		entrances.add(door);
		door.setRoom(this);
	}
	
	public boolean containsEntrance(Door door){
		return entrances.contains(door);
	}
	
	public void addWeapon(Weapons wep){
		weapons.add(wep);
	}
	
	public void removeWeapon(Weapons wep){
		weapons.remove(wep);
	}
	
	public boolean containsWeapon(Weapons wep){
		return weapons.contains(wep);
	}
	
	/**
	 * The name of the room
	 * @return
	 */
	public String getName(){return name;}
	
	//Returns a copy of the rooms entrances
	public List<Door> getEntrances(){return new ArrayList<Door>(entrances);}
	
	@Override
	public char getKey(){return this.key;}

	@Override
	public boolean canMoveTo(Tile tile) {
		//Check to see if any of our entrances can reach the tile
		for (Door d : entrances){
			if (d.canMoveTo(tile)){
				return true;
			}
		}
		
		return false;
	}
	
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
	public int getX(){throw new RuntimeException("Cannot get x position of a Room");}
	
	@Override
	public int getY(){throw new RuntimeException("Cannot get y position of a Room");}
}
