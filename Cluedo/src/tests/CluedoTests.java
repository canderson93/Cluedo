package tests;

import controller.Game;
import controller.Player;
import model.Board;
import model.Board.Direction;

import org.junit.*;

public class CluedoTests {
	
	@Test
	public void testMove_1(){
		new Game("testroom.txt", 1);
	}
	
	// ---- HELPER METHODS -----
	
	public void testValidMove(Direction dir){
		
	}
	
	public void testInvalidMove(Direction dir){
		
	}
}
