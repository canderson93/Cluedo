package controller;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.cards.*;
import model.Weapons;
import model.Rooms;
import model.Characters;
/**
 * The main class for controlling game logic
 *
 */
public class Game {
	private Board board;
	private boolean gameComplete;
	int numPlayers;
	
	
	public Game(String filename, int numPlayers){
		this.board = new Board(filename);
		this.numPlayers = numPlayers;
		this.gameComplete = false;
		loadCards();
		
	}
	
	/**
	 * Load all of the cards into lists 
	 *
	 */
	private void loadCards(){
		
		List<Card> weapons = new ArrayList<Card>();
		for(Weapons w: Weapons.values()){
			weapons.add(new WeaponCard(w.name()));
		}
		List<Card> characters = new ArrayList<Card>();
		for(Characters c: Characters.values()){
			characters.add(new CharacterCard(c.name()));
		}
	}	
}
	