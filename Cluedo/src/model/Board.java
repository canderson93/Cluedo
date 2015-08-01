package model;

import controller.Player;
import model.tiles.Door;
import model.tiles.Hall;
import model.tiles.Room;
import model.tiles.Tile;
import model.tiles.Warp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * The class that represents and handles the board
 * 
 * @author Carl
 *
 */
public class Board {
	private Map<Character, Room> rooms = new HashMap<Character, Room>();
	private Set<Hall> spawns = new HashSet<Hall>();
	private Tile[][] board;
	
	//Enum to represent the direction the door is facing
	public enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT,
		WARP
	}
	
	public Board(int x, int y){
		board = new Tile[x][y];
		
		//Initialise the board as tiles
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){
				board[i][j] = new Hall(i, j);
			}
		}
	}
	
	/**
	 * Creates and parses a new board from file
	 * @param filename
	 */
	public Board(String filename){
		Scanner sc = null;
		
		try{
			//Create a scanner, and parse the board
			sc = new Scanner(new File(filename));
			this.board = parseBoard(sc);
		} catch (FileNotFoundException e){
			System.out.println("Could not find the file "+filename);
			e.printStackTrace();
		} finally {
			if (sc != null){ sc.close(); }
		}
	}
	
	/**
	 * 3s the player in the given direction
	 * 
	 * @param player The player to be moved
	 * @param direction The direction to move
	 * @return whether the move was successful
	 */
	public boolean move(Player player, Direction direction){
		//Get the target tiles
		Tile fromTile = player.getTile();
		Tile toTile = getTile(fromTile, direction);
		
		//check the move direction is valid
		if (toTile == null){return false;}
		if (!fromTile.canMoveTo(toTile)){return false;}
		
		//Try set the new tile to the player
		if (!toTile.setPlayer(player)){return false;}
		fromTile.removePlayer(player);
		
		return true;
	}
	
	/**
	 * Adds a player to a spawn point
	 * @param p the player to be added
	 */
	public void addPlayer(Player p){
		for (Hall h : spawns){
			if (!h.containsPlayer()){
				h.setPlayer(p);
				p.setTile(h);
				return;
			}
		}
	}
	
	/**
	 * Gets the tile in the direction of the provided tile, or null
	 * if there is no eligible tile in that direction.
	 * 
	 * @param tile the tile to move from
	 * @param dir the direction to move
	 * @return the tile in the given direction, or null if there is none
	 */
	private Tile getTile(Tile tile, Direction dir){
		//TODO: Fix when two doors are facing the same direction in a room (eg the Ball Room)
		if (tile instanceof Room){
			//Check whether any of the doors can move in that direction
			for (Tile t : ((Room)tile).getEntrances()){
				Tile target = getTile(t, dir);
				if (target != null){return target;}
			}
			
			return null;
		}
		
		//Check whether we're trying to move the wrong way through a door
		if (tile instanceof Door && ((Door)tile).getDirection() != dir){return null;}
		
		int x = tile.getX();
		int y = tile.getY();
		
		//Get the tile in the corresponding direction
		switch (dir){
		case UP:
			return y < board.length-1 ? board[x][y+1] : null;
		case DOWN:
			return y > 0 ? board[x][y-1] : null;
		case LEFT:
			return x > 0 ? board[x-1][y] : null;
		case RIGHT:
			return x > 0 ? board[x+1][y] : null;
		case WARP:
			if (tile instanceof Warp){
				return ((Warp)tile).getTarget();
			}
		}
		return null;
	}
	
	/**
	 * Parses the board from a scanner containing a valid board representation
	 * @param sc
	 * @return
	 */
	private Tile[][] parseBoard(Scanner sc){
		Tile[][] board;
		int width;
		int height;
		
		sc.useDelimiter("[;\n]+");
		
		Map<Character, Room> rooms = parseRooms(sc);
		Pattern intRegex = Pattern.compile("[0-9]+");	
				
		//Parse the board size, and create the board
		try{
			width = Integer.parseInt(sc.next(intRegex));
			height = Integer.parseInt(sc.next(intRegex));
			
		} catch (RuntimeException e){
			//Catch the exception if something goes wrong, and provide a more useful error
			
			throw new RuntimeException("Could not parse: Board dimensions not declared");
		}
		
		board = parseTokens(sc, rooms, width, height);
		linkTokens(board);
		
		return board;
	}

	/**
	 * Parses each character in the scanner into a 2D tile array
	 * 
	 * @param sc
	 * @param width
	 * @param height
	 */
	private Tile[][] parseTokens(Scanner sc, Map<Character, Room> rooms, int width, int height) {
		Tile[][] board = new Tile[width][height];
		String line;
		
		//Maps the tiles to the warps
		Map<Character, Warp> warps = new HashMap<Character, Warp>();
		
		//Parse the board tokens
		for (int i = 0; i < width; i++){
			if ((line = sc.next()) == null || line.length() < height){throw new RuntimeException("Could not parse: Not enough tokens provided for board size");}
			
		for (int j = 0; j < height; j++){
				char token = line.charAt(0);
				if (rooms.containsKey(token)){
					board[i][j] = rooms.get(token);
				} else {
					//Parse the default tokens
					switch(token){
					case '_': //Hallway
						board[i][j] = new Hall(i, j);
						break;
					case '?': //Spawn Token
						Hall h = new Hall(i, j);
						spawns.add(h);
						board[i][j] = h;
						break;
					//Door tokens
					case 'u':
						board[i][j] = new Door(Direction.UP, i, j);
						break;
					case 'd':
						board[i][j] = new Door(Direction.DOWN, i, j);
						break;
					case 'l':
						board[i][j] = new Door(Direction.LEFT, i, j);
						break;
					case 'r':
						board[i][j] = new Door(Direction.RIGHT, i, j);
						break;
					default:
						//Default is assume we have a warp tile token
						Warp warp = new Warp(token, i, j);
						
						//Check to see if we've already found the matched warp tile
						if (warps.containsKey(token)){
							Warp other = warps.get(token);
							
							if (other == null){throw new RuntimeException("Warp tokens can only match 1 other warp token");}
							
							//Link the tiles
							warp.setTarget(other);
							other.setTarget(warp);
							
							//set the token to null to indicate it's been matched
							warps.put(token, null);
						} else {
							warps.put(token, warp);
						}
					}
				}
			}
		}
		
		//Check all warps were paired
		for (Warp w : warps.values()){
			if (w != null){
				throw new RuntimeException("Unmatched warp token found");
			}
		}
						
		return board;
	}
	
	/**
	 * Link together the relevant tokens
	 * @param board
	 */
	private void linkTokens(Tile[][] board){
		for (int i=0; i < board.length; i ++){
			for (int j=0; j<board[0].length; j++){
				Tile tile = board[i][j];
				
				//Link doors to their rooms
				if (tile instanceof Door){
					Door door = (Door)tile;
					Tile r = null;
					
					try{
						switch(door.getDirection()){
						case UP:
							r = board[i][j+1];
							break;
						case DOWN:
							r = board[i][j-1];
							break;
						case LEFT:
							r = board[i-1][j];
							break;
						case RIGHT:
							r = board[i+1][j];
							break;
						case WARP:
							//Check the surrounding tiles for a room tile that isn't
							//the blank room
							for (Direction d : Direction.values()){
								if (d != Direction.WARP){
									r = this.getTile(door, d);
									
									if (r instanceof Room && ((Room)r).getKey() != '#'){
										break;
									}
								} else {
									continue;
								}
								throw new RuntimeException("Warp tile was not connected to a room");
							}
						}
					} catch (IndexOutOfBoundsException e){
						throw new RuntimeException("Could not parse: Door at "+i+" "+j+" points off the board");
					}
					
					if (!(r instanceof Room)){throw new RuntimeException("Could not parse: Door at "+i+" "+j+" doesn't lead to a room");}
					
					Room room = (Room)r;
					room.addEntrance(door);
				}
			}
		}
	}

	/**
	 * Parse the room declarations from a file, adding them to the
	 * map of rooms
	 * 
	 * @param sc
	 */
	private Map<Character, Room> parseRooms(Scanner sc) {
		Pattern roomReg = Pattern.compile("[A-Z]:.+");
		
		//This is a special case room key for squares the player can't go
		rooms.put('#', new Room("Blank", '#'));
		
		//Parse the room list
		while (sc.hasNext(roomReg)){
			String[] rs = sc.next(roomReg).split(":");
			
			char key = rs[0].charAt(0);
			Room room = new Room(rs[1], key);
			if (rooms.containsKey(key)){
				throw new RuntimeException("Could not parse: Rooms declared with duplicate keys");
			}		
			rooms.put(key, room);
		}		
		
		return rooms;
	}
	
	@Override
	public String toString(){
		String rtn = "";
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[i].length; j++){
				rtn += board[i][j];
			}
			rtn += "\n";
		}
		
		return rtn;
	}
	
	//Getters and setters
	public List<Room> getRooms(){
		List<Room> roomsList = new ArrayList<Room>(rooms.values());
		return roomsList;
	}
}
