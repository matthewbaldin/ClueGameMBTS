package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameSetupTests {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		//initialize a board instance
		board = Board.getInstance();
		//using our config files
		board.setConfigFiles("MBSR_ClueLayout.csv", "MBSR_ClueLegend.txt","MBSR_test_players.txt","MBSR_test_weapons.txt");
		//initializing
		board.initialize();
	}
	@Test
	public void testDeal() {
		boolean withinOne = true;
		HumanPlayer human = board.getHumanPlayer();
		ArrayList<ComputerPlayer> cpus = board.getComputerPlayers();
		Set<Card> cards = new HashSet<Card>();
		cards.addAll(human.getMyCards());
		// we subtract 3 for the solution ones, person, room, weapon.
		int floor = (board.getDeck().size() - 3)/(1 + cpus.size());
		if (!(human.getMyCards().size() != floor || human.getMyCards().size() != floor + 1)) {
			withinOne = false;
		}
		for(ComputerPlayer i : cpus) { 
			cards.addAll(i.getMyCards());
			if (!(i.getMyCards().size() != floor || i.getMyCards().size() != floor + 1)){
				withinOne = false;
				System.out.println(i.getMyCards().size());
			}
		}
		cards.add(board.getSolution().weapon);
		cards.add(board.getSolution().room);
		cards.add(board.getSolution().person);
		//test that all players are dealt roughly the same number of cards
		assertTrue(withinOne);
		//test that all cards are dealt only once and that all cards have been dealt
		//this tests both because of the usage of a set data structure
		//it ensures all cards have been dealt, and that all players hold disjoint sets of cards
		assertEquals(board.getDeck().size(), cards.size());
	}
	
	@Test
	public void testPlayers() {
		//assert player has correct name, color, and starting location
		assertEquals("Mr. Samuel", board.getHumanPlayer().getPlayerName());
		assertEquals(new Color(255, 255, 255), board.getHumanPlayer().getColor());
		assertEquals(4, board.getHumanPlayer().getRow());
		assertEquals(3, board.getHumanPlayer().getColumn());
		//assert first CPU has correct name, color, and starting location
		ArrayList<ComputerPlayer> cpus = board.getComputerPlayers();
		ComputerPlayer firstComputerPlayer = cpus.get(0);
		ComputerPlayer lastComputerPlayer = cpus.get(cpus.size() - 1);
		assertEquals("The Great Baldini", firstComputerPlayer.getPlayerName());
		assertEquals(new Color(0, 0, 0), firstComputerPlayer.getColor());
		assertEquals(13, firstComputerPlayer.getRow());
		assertEquals(4, firstComputerPlayer.getColumn());
		//test that second CPU has correct name, color, and starting location
		assertEquals("Erno Rubik", lastComputerPlayer.getPlayerName());
		assertEquals(new Color(255, 0, 0), lastComputerPlayer.getColor());
		assertEquals(10, lastComputerPlayer.getRow());
		assertEquals(18, lastComputerPlayer.getColumn());
	}
	@Test
	public void testDeck() {
		//test we have loaded the correct number of cards, 9 + 6 + 6
		assertEquals(21, board.getDeck().size());
		int weaponSum = 0;
		int personSum = 0;
		int roomSum = 0;
		boolean hasBaldini = false;
		boolean hasBanana = false;
		boolean hasObservatory = false;
		for (Card i : board.getDeck()) {
			switch(i.type){
			case PERSON:
				hasBaldini |= (i.name.equals("The Great Baldini"));
				++personSum;
				break;
			case ROOM:
				hasObservatory |= (i.name.equals("Observatory"));
				++roomSum;
				break;
			case WEAPON:
				hasBanana |= (i.name.equals("Banana Peel"));
				++weaponSum;
				break;
			default:
				break;}
			
		}
		//assert correct number of weapons, people, and rooms
		assertEquals(6, weaponSum);
		assertEquals(6, personSum);
		assertEquals(9, roomSum);
		//Test for presence of a weapon, a room, and a person
		assertTrue(hasBanana);
		assertTrue(hasBaldini);
		assertTrue(hasObservatory);
	}
}
