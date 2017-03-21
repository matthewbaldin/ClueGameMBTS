package tests;

import org.junit.BeforeClass;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;
import static org.junit.Assert.*;

import clueGame.Board;

public class GameActionTests {

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
	
	//test that it randomly selects rooms
	@Test
	public void testRandomTargetRoomless() {
		ComputerPlayer player = new ComputerPlayer("Testy", Color.BLACK, 21, 20);
		board.calcTargets(21, 20, 1);
		boolean case21_19 = false;
		boolean case20_20 = false;
		//run 100 times
		for (int i = 0; i < 100; ++i) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(21,  19)) {
				case21_19 = true;
			}
			else if (selected == board.getCellAt(20, 20)) {
				case20_20 = true;
			}
			else {
				fail("Invalid target selected.");
			}
		}
		assertTrue(case21_19);
		assertTrue(case20_20);
	}
	//test that it chooses rooms if it wasn't last in the room
	@Test
	public void testRoomAlwaysSelected() {
		ComputerPlayer player = new ComputerPlayer("Testy", Color.BLACK, 21, 20);
		board.calcTargets(21, 20, 2);
		boolean case20_19 = false;
		boolean case20_21 = false;
		boolean case21_18 = false;
		//run 100 times
		for (int i = 0; i < 100; ++i) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(20,  19)) {
				case20_19 = true;
			}
			else if (selected == board.getCellAt(20, 21)) {
				case20_21 = true;
			}
			else if (selected == board.getCellAt(21, 18)) {
				case21_18 = true;
			}
			else {
				fail("Invalid target selected.");
			}
		}
		//this means that room has priority, so it should be false
		assertTrue(!case20_19);
		assertTrue(case20_21);
		assertTrue(case21_18);
	}
	//test that if the room was last visited, it makes a random choice instead
	@Test
	public void testRoomLastVisited() {
		//first place the player at the doorway, then move him to the location we're checking from
		ComputerPlayer player = new ComputerPlayer("Testy", Color.BLACK, 20, 21);
		player.moveTo(board.getCellAt(20, 21));
		player.moveTo(board.getCellAt(20, 20));
		board.calcTargets(20, 20, 1);
		boolean case19_20 = false;
		boolean case20_19 = false;
		boolean case20_21 = false;
		boolean case21_20 = false;
		// run 100 times
		for (int i = 0; i < 100; ++i) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(19, 20)) {
				case19_20 = true;
			}
			else if (selected == board.getCellAt(20, 19)) {
				case20_19 = true;
			} else if (selected == board.getCellAt(20, 21)) {
				case20_21 = true;
			} else if (selected == board.getCellAt(21, 20)) {
				case21_20 = true;
			} else {
				fail("Invalid target selected.");
			}
		}
		// this means that room has priority, so it should be false
		assertTrue(case19_20);
		assertTrue(case20_19);
		assertTrue(case20_21);
		assertTrue(case21_20);
	}
	//makes sure the computer prefers new rooms over old rooms
	@Test
	public void testNewRoomAlwaysSelected() {
		ComputerPlayer player = new ComputerPlayer("Testy", Color.BLACK, 21, 20);
		//move the player to the L room
		player.moveTo(board.getCellAt(20, 21));
		board.calcTargets(21, 20, 2);
		boolean case20_19 = false;
		boolean case20_21 = false;
		boolean case21_18 = false;
		//run 100 times
		for (int i = 0; i < 100; ++i) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(20,  19)) {
				case20_19 = true;
			}
			else if (selected == board.getCellAt(20, 21)) {
				case20_21 = true;
			}
			else if (selected == board.getCellAt(21, 18)) {
				case21_18 = true;
			}
			else {
				fail("Invalid target selected.");
			}
		}
		//this means that room has priority, so it should be false
		assertTrue(!case20_19);
		assertTrue(!case20_21);
		assertTrue(case21_18);
	}
}
