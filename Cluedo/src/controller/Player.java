package controller;

/**
 * A player class for the Cluedo Game
 * @author Carl
 *
 */
public class Player {
	private final String name;
	private final char key;
	
	public Player(String name, char key){
		this.name = name;
		this.key = key;
	}
	
	public String name(){return name;}
	public char key(){return key;}
}
