package model.tiles;

import controller.Player;

public class Room extends Tile {
	//The name of the room, and the letter to represent it on the board
	private final String name;
	
	public Room(String name, char key){
		super(key);
		this.name = name;
	}
	
	public Room(String name){
		this(name, name.charAt(0));
	}
	

	@Override
	public boolean canBeReached(Tile tile, int roll) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptMove(Player player, Tile tile, int roll) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * The name of the room
	 * @return
	 */
	public String name(){return name;}
}
