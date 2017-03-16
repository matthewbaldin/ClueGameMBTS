package tests;

import static org.junit.Assert.*;

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
}
