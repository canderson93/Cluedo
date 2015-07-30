package model;

import controller.Player;

import model.tiles.Door;
import model.tiles.Hall;
import model.tiles.Room;
import model.tiles.Tile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
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
	
	public boolean move(Player player, Direction direction, int roll){
		return false;
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
		
		//TODO: Include warp declarations in the board file
		
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
						throw new RuntimeException("Could not parse: Unrecognized token");
					}
				}
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
					Tile r;
					
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
						default:
							r = board[i+1][j];
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
		Map<Character, Room> rooms = new HashMap<Character, Room>();
		
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
}
