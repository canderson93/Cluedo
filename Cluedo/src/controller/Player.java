package controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.cards.*;

/**
 * A player class for the Cluedo Game
 * @author Carl
 *
 */
public class Player {
	private final String name;
	private final char key;
	private List<Card> cards;
	
	public Player(String name, char key){
		this.name = name;
		this.key = key;
		cards = new ArrayList<Card>();
		
	}
	
	public String name(){return name;}
	public char key(){return key;}
}
