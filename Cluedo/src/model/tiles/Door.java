package model.tiles;

import model.Board.Direction;

public class Door extends Tile {
	private final Direction dir;
	private Room room;
	
	public Door(Direction dir, int x, int y){
		super('d', x, y);
		this.dir = dir;
	}
	
	//Getters and setters
	public void setRoom(Room room){this.room = room;}
	public Direction getDirection(){return dir;}

	@Override
	public boolean canMoveTo(Tile tile) {
		//Check if the tile is the room this door leads to, or is another entrance to the same room
		if (tile instanceof Room){return tile == room;}
		if (tile instanceof Door){return room.containsEntrance((Door)tile);}
		
		//Otherwise, check if the tile is 1 square away in the direction the door is facing
		int x = this.x-tile.getX();
		int y = this.y-tile.getY();
		
		switch(dir){
		case UP:
			return x == 0 && y == -1;
		case DOWN:
			return x == 0 && y == 1;
		case LEFT:
			return x == -1 && y == 0;
		default:
			return x == 1 && y == 0;
		}
	}
}
