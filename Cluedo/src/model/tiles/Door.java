package model.tiles;

public class Door extends Tile {
	private final Direction dir;
	private Room room;
	
	//Enum to represent the direction the door is facing
	public enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT,
	}
	
	public Door(Direction dir){
		super('d');
		this.dir = dir;
		this.room = room;
	}
	
}
