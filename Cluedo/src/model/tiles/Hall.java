package model.tiles;

import controller.Player;

/**
 * The tile object used to represent the hallway of the cluedo game
 * @author Carl
 *
 */
public class Hall extends Tile{
	private Player player = null;
	int x; //position
	int y;
	
	public Hall(int x, int y){
		super('_');
		
		this.x = x;
		this.y = y;
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
	
	@Override
	public char key(){
		//If this tile contains a player, show the player instead
		if (player != null){
			return player.key();
		}
		
		return super.key();
	}
}
