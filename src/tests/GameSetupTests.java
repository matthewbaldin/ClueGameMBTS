package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameSetupTests {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		//initialize a board instance
		board = Board.getInstance();
		board.setConfigFiles("MBSR_ClueLayout.csv", "MBSR_ClueLegend.txt","MBSR_test_players.txt","MBSR_test_weapons.txt");
		board.initialize();
	}
	@Test
	public void testDeck() {
		//test that all cards have been dealt
		assertequals(0, board.getDeck().length());
		boolean withinOne = false;
		HumanPlayer human = board.getHumanPlayer();
		ArrayList<ComputerPlayer> cpus = board.getComputerPlayers();
		
	}
	
}
