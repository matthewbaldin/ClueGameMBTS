package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class MBSR_FileInitTests {
	public final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	
private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("MBSR_ClueLayout.csv", "MBSR_ClueLegend.txt");		
		board.initialize();
	}
	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		assertEquals("Observatory", legend.get('O'));
		assertEquals("Mancave", legend.get('M'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Doghouse", legend.get('D'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(6, 2);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(3, 0);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(10, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(18, 0);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(0, 0);
		assertFalse(room.isDoorway());	
		BoardCell cell = board.getCellAt(11, 3);
		assertFalse(cell.isDoorway());		

	}

	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(30, numDoors);
	}

	@Test
	public void testRoomInitials() {
		assertEquals('M', board.getCellAt(0, 0).getInitial());
		assertEquals('U', board.getCellAt(5, 4).getInitial());
		assertEquals('P', board.getCellAt(10, 5).getInitial());

		assertEquals('L', board.getCellAt(21, 22).getInitial());
		assertEquals('O', board.getCellAt(21, 0).getInitial());

		assertEquals('W', board.getCellAt(10, 4).getInitial());

		assertEquals('D', board.getCellAt(13,11).getInitial());
	}

}
