package model.tiles;

import model.Board.Direction;

public class Warp extends Door {
	private Warp target;
	
	public Warp(char key, int x, int y){
		super(key, Direction.WARP, x, y);
	}
	
	@Override
	public boolean canMoveTo(Tile tile) {
		if (tile instanceof Room){return tile == room || tile == target.getRoom();}
		if (tile instanceof Warp){return tile == target || room.containsEntrance((Door)tile);}
		if (tile instanceof Door){return room.containsEntrance((Door)tile);}
		
		return false;
	}
	
	public void setTarget(Warp warp){
		this.target = warp;
	}
	
	public Warp getTarget(){
		return this.target;
	}
}
