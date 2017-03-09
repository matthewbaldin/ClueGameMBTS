package tests;


import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class TSBJ_FileInitTests {
	// Constants used to test whether files loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLS = 20;
	
	private static Board board;
	
	@BeforeClass
	public static void setUP() 
	{
		board = Board.getInstance();
		board.setConfigFiles("TSBJ_ClueLayout.csv", "TSBJ_ClueLegend.txt");
		board.initialize();
	}
	
	// Checks whether ClueLegend was loaded correctly
	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		
		assertEquals(LEGEND_SIZE, legend.size());
		
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Dining Room", legend.get('D'));
		assertEquals("Bathroom", legend.get('B'));
		assertEquals("Fitness Room", legend.get('F'));
		assertEquals("Closet", legend.get('X'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	// Checks that the size of the board was initialized correctly
	@Test
	public void testBoardSize() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLS, board.getNumColumns());
	}
	
	// Checks that the board contains a door for each direction
	// Checks that doorway cells are created correctly
	// Tests that cells that aren't doorways don't return as such
	@Test
	public void testDoorwayDirection() {
		BoardCell room = board.getCellAt(15, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(10, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(4, 7);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(5, 17);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		// Checks that cell in "office" is not a door
		room = board.getCellAt(17, 18);
		assertFalse(room.isDoorway());
		// Checks that "walkway" is not a door
		room = board.getCellAt(7, 10);
		assertFalse(room.isDoorway());
	}
	//Checks that the correct number of doorways was read
	@Test
	public void testNumDoors() {
		int numDoors = 0;
		
		for (int i = 0; i < NUM_ROWS; i++)
		{
			for (int j = 0; j < NUM_COLS; j++)
			{
				if(board.getCellAt(i, j).isDoorway())
				{
					numDoors++;
				}
			}
		}
		assertEquals(13, numDoors);
	}
	// Checks that cells have the correct initials
	@Test
	public void testInitials() {
		BoardCell room = board.getCellAt(2, 7);
		assertEquals('D', room.getInitial());
		room = board.getCellAt(10, 3);
		assertEquals('K', room.getInitial());
		room = board.getCellAt(19, 13);
		assertEquals('F', room.getInitial());
		room = board.getCellAt(10, 10);
		// Tests that closet initial is assigned correctly
		assertEquals('X', room.getInitial());
		// Tests that walkway initial is assigned correctly
		room = board.getCellAt(6, 0);
		assertEquals('W', room.getInitial());
	}
}

