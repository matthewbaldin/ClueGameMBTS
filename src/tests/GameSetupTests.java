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
		int floor = board.getDeck().size() - 3;
		if (human.getMyCards().size() != floor || human.getMyCards().size() != floor + 1) {
			withinOne = false;
		}
		if(!cpus.isEmpty()) {
			for(ComputerPlayer i : cpus) { 
				cards.addAll(i.getMyCards());
				if (i.getMyCards().size() != floor || i.getMyCards().size() != floor + 1) {
					withinOne = false;
				}
			}
		}
		
		cards.add(board.getTheAnswer().weapon);
		cards.add(board.getTheAnswer().room);
		cards.add(board.getTheAnswer().person);
		//test that all players are dealt roughly the same number of cards
		assertEquals(true, withinOne);
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
		assertEquals("Erno Rubik", firstComputerPlayer.getPlayerName());
		assertEquals(new Color(255, 0, 0), firstComputerPlayer.getColor());
		assertEquals(10, firstComputerPlayer.getRow());
		assertEquals(18, firstComputerPlayer.getColumn());
	}
	@Test
	public void testDeck() {
		
	}
}
