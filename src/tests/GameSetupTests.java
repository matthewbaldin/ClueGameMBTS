package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

import clueGame.Board;

public class GameSetupTests {
/*
	public static HumanPlayer human;
	public static ComputerPlayer cpu1;
	public static ComputerPlayer cpu2;
	public static ComputerPlayer cpu3;
	public static ComputerPlayer cpu4;
	public static ComputerPlayer cpu5;
	*/
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
}
