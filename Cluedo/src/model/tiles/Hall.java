package model.tiles;

/**
 * The tile object used to represent the hallway of the cluedo game
 * @author Carl
 *
 */
public class Hall extends Tile{
	
	public Hall(int x, int y){
		super('_', x, y);
	}

	@Override
	public boolean canMoveTo(Tile tile) {
		//Check the tile is exactly 1 tile away
		return !(tile instanceof Room) && (Math.abs(this.x-tile.x) + Math.abs(this.y-tile.y) == 1);
	}
}
