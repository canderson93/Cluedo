package tests;

import java.util.ArrayList;
import java.util.List;

import controller.Game;
import controller.Player;
import model.Board.Direction;
import model.cards.Card;
import model.cards.CharacterCard;
import model.cards.RoomCard;
import model.cards.WeaponCard;
import model.tiles.Hall;
import model.tiles.Room;
import model.tiles.Tile;

import org.junit.*;

import static org.junit.Assert.*;

public class CluedoTests {
	
	@Test
	//Test that we can't move if we have a roll of 0
	public void testMove_1(){
		Game g = new Game("testroom.txt", 1);
		Tile t = g.getCurrent().getTile();
		g.setRoll(0);
		
		g.move(Direction.UP);
		
		assertTrue(t == g.getCurrent().getTile());
	}
	
	@Test
	//Test that we change tiles when we move
	public void testMove_2(){
		Game g = new Game("testroom.txt", 1);
		Tile t = g.getCurrent().getTile();
		g.setRoll(1);
			
		g.move(Direction.UP);
		
		Tile t2 = g.getCurrent().getTile();
		
		assertTrue(t.getX() == t2.getX() && (t.getY() - t2.getY()) == 1);
	}
	
	@Test
	//Test that when we enter a door, we enter a room
	public void testMove_3(){
		Game g = new Game("testroom.txt", 1);
		g.setRoll(3);
			
		g.move(Direction.LEFT);
		g.move(Direction.LEFT);
		g.move(Direction.UP);
		
		assertTrue(g.getCurrent().getTile() instanceof Room);
	}
	
	@Test
	//Test that we can't enter a door from the wrong direction
	public void testMove_4(){
		Game g = new Game("testroom.txt", 1);
		g.setRoll(3);
			
		g.move(Direction.UP);
		g.move(Direction.LEFT);
		
		Tile t = g.getCurrent().getTile();
		g.move(Direction.UP);
		
		assertTrue(g.getCurrent().getTile() == t);
	}
	
	@Test
	//Test that we can't enter a room through a wall
	public void testMove_5(){
		Game g = new Game("testroom.txt", 1);
		g.setRoll(5);
			
		g.move(Direction.UP);
		g.move(Direction.UP);
		g.move(Direction.UP);
		g.move(Direction.LEFT);
		
		Tile t = g.getCurrent().getTile();
		g.move(Direction.LEFT);
		
		assertTrue(g.getCurrent().getTile() == t);
	}
	
	@Test
	//Test that we can take shortcuts correctly
	public void testMove_6(){
		Game g = new Game("testroom.txt", 1);
		
		//Round 1 -- Enter the room
		g.setRoll(3);
		g.move(Direction.LEFT);
		g.move(Direction.LEFT);
		g.move(Direction.UP);
		
		g.nextRound();
		g.move(Direction.WARP);
		
		Tile t = g.getCurrent().getTile();
		assertTrue(t instanceof Room && ((Room)t).getName().equals("TestRoom2"));
	}
	
	@Test
	//Test that we can't move again when we've entered a room 
	public void testMove_7(){
		Game g = new Game("testroom.txt", 1);
		
		//Round 1 -- Enter the room
		g.setRoll(100);
		g.move(Direction.LEFT);
		g.move(Direction.LEFT);
		g.move(Direction.UP);
		
		Tile t = g.getCurrent().getTile();
		g.move(Direction.DOWN);
		
		Tile t2 = g.getCurrent().getTile();
		assertTrue(t == t2);
	}
	
	@Test
	//test the hands do not contain the same cards
	public void testHand_1(){
		Game g = new Game("testroom.txt", 2);
		
		List<Card> hand1 = g.getCurrent().getHand();
		
		g.nextRound();
		List<Card> hand2 = g.getCurrent().getHand();
		
		for (Card c1 : hand1){
			for (Card c2 : hand2){
				if (c1.equals(c2)){
					fail();
				}
			}
		}
	}
	
	@Test
	//test that both players have cards in their hands
	public void testHand_2(){
		Game g = new Game("testroom.txt", 2);
		
		List<Card> hand1 = g.getCurrent().getHand();
		
		g.nextRound();
		List<Card> hand2 = g.getCurrent().getHand();
		
		
		assertFalse(hand1.isEmpty() || hand2.isEmpty());
	}
	
	@Test
	//test that cards are dealt out evenly amongst players
	public void testHand_3(){
		Game g = new Game("testroom.txt", 3);
		
		List<Card> hand1 = g.getCurrent().getHand();
		g.nextRound();
		List<Card> hand2 = g.getCurrent().getHand();
		g.nextRound();
		List<Card> hand3 = g.getCurrent().getHand();
		
		int low = Math.min(hand1.size(), Math.min(hand2.size(), hand3.size()));
		int high = Math.max(hand1.size(), Math.max(hand2.size(), hand3.size()));
		
		assertTrue(high-low <= 1);
	}
	
	@Test
	//Test solution cards aren't dealt out
	public void testHand_4(){
		Game g = new Game("testroom.txt", 1);
		
		List<Card> solution = g.getSolution();
		List<Card> hand = g.getCurrent().getHand();
		
		for (Card c : hand){
			if (solution.contains(c)){fail();}
		}
	}
	
	@Test
	//Test that all cards are dealt out
	public void testHand_5(){
		Game g = new Game("testroom.txt", 1);
		
		List<Card> solution = g.getSolution();
		
		List<CharacterCard> characters = g.getCharacterCards();
		List<RoomCard> rooms = g.getRoomCards();
		List<WeaponCard> weapons = g.getWeaponCards();
		
		List<Card> hand = g.getCurrent().getHand();
		for (Card c : hand){
			if (!characters.contains(c) && !rooms.contains(c) && 
				!weapons.contains(c) && !solution.contains(c)){
				fail();
			}
		}
	}
	
	@Test
	//Test when a player makes a suggestion, the player is shown a card
	public void testSuggestion_1(){
		Game g = new Game("testroom.txt", 2);
		
		g.nextRound();
		Player other = g.getCurrent();
		CharacterCard chCard = null;
		WeaponCard weCard = null;
		
		//Move to the correct room
		g.nextRound();
		g.setRoll(6);
		
		g.move(Direction.LEFT);
		g.move(Direction.LEFT);
		g.move(Direction.UP);
		
		//Get two cards from the other players hand
		for (Card c : other.getHand()){
			if (chCard == null && c instanceof CharacterCard){
				chCard = (CharacterCard)c;
			} else if (weCard == null && c instanceof WeaponCard){
				weCard = (WeaponCard)c;
			}
			
			if (chCard != null && weCard != null){
				break;
			}
		}
		
		//Make a copy of the players hand, and then try make a suggestion
		List<Card> unseen = new ArrayList<Card>(g.getCurrent().getUnseenCards());
		g.suggestion(chCard.getValue(), weCard.getValue(), (Room)g.getCurrent().getTile());
		
		assertTrue(!unseen.equals(g.getCurrent().getUnseenCards()));
	}
	
	@Test
	//Test when a player makes a suggestion equal to the solution, they aren't shown a card
	public void testSuggestion_2(){
		Game g = new Game("testroom.txt", 1);
		
		List<Card> solution = g.getSolution();
		CharacterCard chCard = null;
		WeaponCard wepCard = null;
		RoomCard rmCard = null;
		
		//find a character card we know isn't correct
		for (Card c : solution){
			if (c instanceof CharacterCard){
				chCard = (CharacterCard)c;
			} else if (c instanceof WeaponCard){
				wepCard = (WeaponCard)c;
			} else if (c instanceof RoomCard){
				rmCard = (RoomCard)c;
			}
		}
		
		//Make a copy of the players hand, and then try make a suggestion
		Card rtn = g.suggestion(chCard.getValue(), wepCard.getValue(), new Room(rmCard.getValue()));
		
		assertTrue(rtn == null);
	}
	
	@Test
	//Test accusations prevent the player from taking a turn again
	public void testAccusations_1(){
		Game g = new Game("testroom.txt", 2);
		
		List<Card> solution = g.getSolution();
		
		Player player = g.getCurrent();
		CharacterCard chCard = null;
		
		//find a character card we know isn't correct
		for (Card c : player.getHand()){
			if (c instanceof CharacterCard && !solution.contains(c)){
				chCard = (CharacterCard)c;
				break;
			}
		}
		
		//Make the incorrect accusation
		g.accusation("TestRoom1", "CANDLESTICK", chCard.getValue());
		
		//Cycle back to what should be original players turn
		g.nextRound();
		g.nextRound();
		
		assertTrue(g.getCurrent() != player);
	}
	
	@Test
	//Test an incorrect accusation with 1 remaining player ends the game
	public void testAccusations_2(){
		Game g = new Game("testroom.txt", 1);
		
		List<Card> solution = g.getSolution();
		
		Player player = g.getCurrent();
		CharacterCard chCard = null;
		
		//find a character card we know isn't correct
		for (Card c : player.getHand()){
			if (c instanceof CharacterCard && !solution.contains(c)){
				chCard = (CharacterCard)c;
				break;
			}
		}
		
		//Make the incorrect accusation
		g.accusation("TestRoom1",  chCard.getValue(), "CANDLESTICK");
		
		assertTrue(g.isFinished());
	}
	
	@Test
	//Test a correct accusation ends the game
	public void testAccusations_3(){
		Game g = new Game("testroom.txt", 1);
		
		List<Card> solution = g.getSolution();
				
		CharacterCard chCard = null;
		WeaponCard wepCard = null;
		RoomCard rmCard = null;
		
		//find a character card we know isn't correct
		for (Card c : solution){
			if (c instanceof CharacterCard){
				chCard = (CharacterCard)c;
			} else if (c instanceof WeaponCard){
				wepCard = (WeaponCard)c;
			} else if (c instanceof RoomCard){
				rmCard = (RoomCard)c;
			}
		}
		
		//Make the accusation
		g.accusation(rmCard.getValue(), chCard.getValue(), wepCard.getValue());
		
		assertTrue(g.isFinished());
	}
	
	@Test
	//Test that a tile will accept a player
	public void testTile_1(){
		Player p = new Player("Test_Dummy", 'T', null);
		
		Tile t = new Hall(0,0);
		t.setPlayer(p);
		
		assertTrue(t.getPlayer() == p);
	}
	
	@Test
	//Test that a tile will not accept a player if the tile already holds a player
	public void testTile_2(){
		Player p = new Player("Test_Dummy", 'T', null);
		Player p2 = new Player("Test_Dummy2", 'D', null);
		
		Tile t = new Hall(0,0);
		t.setPlayer(p);
		t.setPlayer(p2);
		
		assertTrue(t.getPlayer() == p);
	}
	
	@Test
	//Test that a the player tile is updated when changing tiles
	public void testTile_3(){
		Player p = new Player("Test_Dummy", 'T', null);
		
		Tile t = new Hall(0,0);
		Tile t2 = new Hall(0, 1);
		
		t.setPlayer(p);
		if (p.getTile() != t){fail();}
		
		t2.setPlayer(p);
		
		assertTrue(p.getTile() == t2);
	}
	
	@Test
	//Test that a the player is removed from the old tile when changing tiles
	public void testTile_4(){
		Player p = new Player("Test_Dummy", 'T', null);
		
		Tile t = new Hall(0,0);
		Tile t2 = new Hall(0, 1);
		
		t.setPlayer(p);
		if (t.getPlayer() != p){fail();}
		
		t2.setPlayer(p);
		
		assertTrue(t2.getPlayer() == p);
	}
}
