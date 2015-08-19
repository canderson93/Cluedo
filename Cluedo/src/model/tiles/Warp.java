package model.tiles;

import java.awt.Color;
import java.awt.Graphics;

import model.Board;
import model.Board.Direction;

/**
 * This is a representation of the shortcut tiles between
 * rooms. This acts as a special door, which can only be accessed
 * from the linked room, or the other linked warp tile.
 * 
 * @author Carl
 *
 */
public class Warp extends Door {
	public static Color color = Color.BLACK;
	
	private Warp target;

	public Warp(char key, int x, int y) {
		super(key, Direction.WARP, x, y);
	}

	@Override
	public boolean canMoveTo(Tile tile) {
		if (tile instanceof Room) {
			return tile == room || tile == target.getRoom();
		}
		if (tile instanceof Warp) {
			return tile == target || room.containsEntrance((Door) tile);
		}
		if (tile instanceof Door) {
			return room.containsEntrance((Door) tile);
		}

		return false;
	}

	//Getters and setters
	public void setTarget(Warp warp) {
		this.target = warp;
	}

	public Warp getTarget() {
		return this.target;
	}
	
	@Override
	public double getDistance(Tile other){
		if (other == target){
			return 1;
		}
		
		return super.getDistance(other);
	}
	
	@Override
	public void draw(Graphics g, int x, int y){
		int size = Board.tileSize;
		
		g.setColor(color);
		g.fillRect(x*size, y*size, size, size);
	}
}
