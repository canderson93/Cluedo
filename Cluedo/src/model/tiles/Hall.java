package model.tiles;

import java.awt.Color;
import java.awt.Graphics;

import model.Board;

/**
 * The tile object used to represent the hallway of the cluedo game
 * @author Carl
 *
 */
public class Hall extends Tile{
	public static Color color = new Color(255, 255, 60);
	public static Color lineColor = new Color(240, 240, 0);
	
	public Hall(int x, int y){
		super(' ', x, y);
	}

	@Override
	public boolean canMoveTo(Tile tile) {
		//Check the room is one the hall is allowed to move to
		if (tile instanceof Room || tile instanceof Warp){return false;}
		if (tile instanceof Door){return tile.canMoveTo(this);}
		
		return Math.abs(this.x-tile.x) + Math.abs(this.y-tile.y) == 1;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
		int size = Board.tileSize;
		
		//Draw the base tile
		g.setColor(color);
		g.fillRect(x*size, y*size, size, size);
		
		//Draw a tile border
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(x*size, y*size, size, size);
	}
}
