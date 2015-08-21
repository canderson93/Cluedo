package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Board.Direction;
import model.tiles.Door;
import model.tiles.Room;
import model.tiles.Tile;

/**
 * Class to find paths between two tiles
 * @author Carl
 *
 */
public class PathFinder {
	
	public static List<Tile> findPath(Board board, Tile fromTile, Tile toTile){
		List<Tile> path = new ArrayList<Tile>();
		Set<Tile> visited = new HashSet<Tile>();
		
		List<SearchNode> fringe = new ArrayList<SearchNode>();
		
		//If either tile is a door, search from the associated room instead
		if (fromTile instanceof Door){
			fromTile = ((Door)fromTile).getRoom();
		}
		
		if (toTile instanceof Door){
			toTile = ((Door) toTile).getRoom();
		}
		
		//If both tiles are rooms, try each combination of doors
		if (fromTile instanceof Room && toTile instanceof Room){
			for (Door d : ((Room) fromTile).getEntrances()){
				for (Door d2 : ((Room) toTile).getEntrances()){
					fringe.add(new SearchNode(d, null, d2, 0));
				}
			}
		}
		//If fromTile is a room, try each door
		else if (fromTile instanceof Room){
			for (Door d : ((Room) fromTile).getEntrances()){
				fringe.add(new SearchNode(d, null, toTile, 0));
			}
		}
		//As above
		else if (toTile instanceof Room){
			for (Door d : ((Room) toTile).getEntrances()){
				fringe.add(new SearchNode(fromTile, null, d, 0));
			}
		}
		else {
			fringe.add(new SearchNode(fromTile, null, toTile, 0));
		}
		
		//If nothing was added to the fringe, that means that we have a room with no entrances
		//(e.g. blank room) so no possible path exists
		if (fringe.isEmpty()){return path;}
		
		SearchNode current = null;
		
		//Perform the A* search
		while(!fringe.isEmpty()){
			current = null;
			
			//select the best node from the fringe
			for (SearchNode n : fringe){
				if (current == null || n.heuristic() < current.heuristic()){
					current = n;
				}
			}
			if (current.node == current.target){break;}

			fringe.remove(current);
			if (!visited.add(current.node)){continue;}
			if (current.node instanceof Room){continue;}
			
			//Search surrounding tiles, and add them to the fringe
			for (Direction d : Direction.values()){
				Tile t = board.getTile(current.node, d);
				if(t != null){
					fringe.add(new SearchNode(t, current, current.target, current.dist+1));
				}
			}
		}
		
		while (current.parent != null){
			path.add(0, current.node);
			current = current.parent;
		}
		System.out.println("Path Size: "+path.size());
		return path;
	}
		
	private static class SearchNode {
		public Tile node = null;
		public SearchNode parent = null;
		public Tile target = null;
		public double cost = 0;
		public double dist = 0;
		
		public SearchNode(Tile node, SearchNode parent, Tile target, double dist){
			this.node = node;
			this.parent = parent;
			this.dist = dist;
			this.target = target;
			this.cost = node.getDistance(target);
		}
		
		public double heuristic(){
			return dist+cost;
		}
	}
}
