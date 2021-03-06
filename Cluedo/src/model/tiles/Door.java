package model.tiles;

import java.awt.Graphics;

import controller.Player;
import model.Board.Direction;

/**
 * A class to represent a door tile, which allows access to a room.
 * @author Carl
 *
 */
public class Door extends Tile {
	protected final Direction dir;
	protected Room room;
	
	public Door(Direction dir, int x, int y){
		this('d', dir, x ,y);
	}
	
	public Door(char key, Direction dir, int x, int y){
		super(key, x, y);
		this.dir = dir;
	}

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
			return x == 0 && y == 1;
		case DOWN:
			return x == 0 && y == -1;
		case LEFT:
			return x == 1 && y == 0;
		case RIGHT:
			return x == -1 && y == 0;
		default:
			return false;
		}
	}
	

	//Getters and setters
	public void setRoom(Room room){this.room = room;}
	
	@Override
	public boolean setPlayer(Player p){
		return room.setPlayer(p);
	}
	
	public Direction getDirection(){return dir;}
	public Room getRoom(){return room;}

	@Override
	public void draw(Graphics g, int x, int y, int size) {
		room.draw(g, x, y, size);
	}
}
