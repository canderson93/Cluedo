package model.tiles;

import java.util.ArrayList;
import java.util.List;

public class Room extends Tile {
	//The name of the room, and the letter to represent it on the board
	private final String name;
	private List<Door> entrances = new ArrayList<Door>();
	
	public Room(String name, char key){
		super(key);
		this.name = name;
	}
	
	public Room(String name){
		this(name, name.charAt(0));
	}
	
	public void addEntrance(Door door){
		entrances.add(door);
	}
	
	/**
	 * The name of the room
	 * @return
	 */
	public String getName(){return name;}
}
