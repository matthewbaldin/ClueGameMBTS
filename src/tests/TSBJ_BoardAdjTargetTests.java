package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class TSBJ_BoardAdjTargetTests {

	private static Board board;
	
	@BeforeClass
	public static void setUp() 
	{
		
		board = Board.getInstance();
		
		board.setConfigFiles("TSBJ_ClueLayout.csv", "TSBJ_ClueLegend.txt");
		board.initialize();
	}
	
	// Ensure that player cannot move within a room
	// shows as Orange on spreadsheet
	@Test
	public void testAdjacenciesInsideRooms() 
	{
		// test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// test middle of the room
		testList = board.getAdjList(2, 18);
		assertEquals(0, testList.size());
		// test cell with walkway underneath
		testList = board.getAdjList(4, 0);
		assertEquals(0, testList.size());
		// test cell with walkway on the left
		testList = board.getAdjList(8, 17);
		assertEquals(0, testList.size());
		// test cell with walkway on the right
		testList = board.getAdjList(9, 4);
		assertEquals(0, testList.size());
		// test cell with walkway on the top
		testList = board.getAdjList(15, 17);
		assertEquals(0, testList.size());
	}
	
	// Ensure that the adjacency list from a doorway
	// is only the walkway.
	// shows as Green on spreadsheet
	@Test
	public void testAdjacencyRoomExits() {
		// test doorway RIGHT
		Set<BoardCell> testList = board.getAdjList(11, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 5)));
		// test doorway DOWN
		testList = board.getAdjList(5, 13);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 13)));
		// test doorway LEFT
		testList = board.getAdjList(14, 18);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 17)));
		// test doorway UP
		testList = board.getAdjList(15, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		// test doorway that is next to another walkway
		testList = board.getAdjList(4, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 4)));
	}
	
	// test adjacency of cells next to doorways
	// shows as white on spreadsheet
	@Test
	public void testAdjacencyDoorways() {
		// test cell next to door direction DOWN
		Set<BoardCell> testList = board.getAdjList(6, 13);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 14)));
		assertTrue(testList.contains(board.getCellAt(7, 13)));
		assertTrue(testList.contains(board.getCellAt(6, 12)));
		assertTrue(testList.contains(board.getCellAt(5, 13)));
		// test cell next to door direction LEFT
		testList = board.getAdjList(14, 17);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 17)));
		assertTrue(testList.contains(board.getCellAt(14, 18)));
		assertTrue(testList.contains(board.getCellAt(14, 16)));
		// test cell next to door direction UP
		testList = board.getAdjList(14, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 8)));
		assertTrue(testList.contains(board.getCellAt(15, 7)));
		assertTrue(testList.contains(board.getCellAt(14, 6)));
		assertTrue(testList.contains(board.getCellAt(13, 7)));
		// test cell next to door direction RIGHT
		testList = board.getAdjList(11, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		assertTrue(testList.contains(board.getCellAt(11, 4)));
		assertTrue(testList.contains(board.getCellAt(12, 5)));
		assertTrue(testList.contains(board.getCellAt(10, 5)));
	}
	// test various adjacencies for walkways
	// shows as Light Green on spreadsheet
	@Test
	public void testAdjacencyWalkway() {
		// test left edge; three adjacent walkways
		Set<BoardCell> testList = board.getAdjList(7, 0);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		
		// test top edge; three adjacent walkways
		testList = board.getAdjList(0, 10);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(0, 9)));
		assertTrue(testList.contains(board.getCellAt(1, 10)));
		assertTrue(testList.contains(board.getCellAt(0, 11)));
		
		// test right edge; two adjacent walkways
		// next to room that isn't a door
		testList = board.getAdjList(11, 19);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 19)));
		assertTrue(testList.contains(board.getCellAt(11, 18)));
		
		// test bottom edge; one adjacent walkway
		// next to room that isn't a door
		testList = board.getAdjList(19, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 4)));
		
		// test walkway piece in middle of walkway; four adjacent walkways
		testList = board.getAdjList(10, 15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 16)));
		assertTrue(testList.contains(board.getCellAt(11, 15)));
		assertTrue(testList.contains(board.getCellAt(10, 14)));
		assertTrue(testList.contains(board.getCellAt(9, 15)));
	}
	// Test various locations for targets at one step away,
	// includes cell at the edge of the board
	// All targets show as Light Purple on spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(19, 9, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 9)));
		assertTrue(targets.contains(board.getCellAt(19, 10)));
		assertTrue(targets.contains(board.getCellAt(19, 8)));

		board.calcTargets(7, 15, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(8, 15)));
		assertTrue(targets.contains(board.getCellAt(7, 16)));	
		assertTrue(targets.contains(board.getCellAt(6, 15)));
		assertTrue(targets.contains(board.getCellAt(7, 14)));

	}
	// Test various locations for targets at two steps away,
	// includes cell at the edge of the board
	@Test
	public void testTargetsTwoStep() {
		
		board.calcTargets(14, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 14)));
		assertTrue(targets.contains(board.getCellAt(14, 16)));
		assertTrue(targets.contains(board.getCellAt(16, 14)));
		assertTrue(targets.contains(board.getCellAt(14, 12)));
		assertTrue(targets.contains(board.getCellAt(13, 15)));
		assertTrue(targets.contains(board.getCellAt(15, 15)));
		assertTrue(targets.contains(board.getCellAt(15, 13)));
		assertTrue(targets.contains(board.getCellAt(13, 13)));

		board.calcTargets(0, 15, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 14)));
		assertTrue(targets.contains(board.getCellAt(2, 15)));
	}
	// Test locations for targets at four steps away
	// includes test for one doorway
	@Test
	public void testTargetsFourStep() {
		
		board.calcTargets(6, 3, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 0)));
		assertTrue(targets.contains(board.getCellAt(7, 0)));
		assertTrue(targets.contains(board.getCellAt(8, 1)));
		assertTrue(targets.contains(board.getCellAt(4, 3)));
		assertTrue(targets.contains(board.getCellAt(3, 4)));
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 7)));
		assertTrue(targets.contains(board.getCellAt(7, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 5)));
		assertTrue(targets.contains(board.getCellAt(8, 5)));
		assertTrue(targets.contains(board.getCellAt(6, 1)));
		assertTrue(targets.contains(board.getCellAt(7, 4)));
		assertTrue(targets.contains(board.getCellAt(5, 4)));
		assertTrue(targets.contains(board.getCellAt(7, 2)));
	}
	// Test locations for targets at six steps away
	// includes test for four doorways
	@Test
	public void testTargetsSixStep() {
		
		board.calcTargets(14, 3, 6);
		Set<BoardCell> targets= board.getTargets();
		/* Commented out as unnecessary
		for(BoardCell x: targets){
			System.out.println(x.getRow() + " " + x.getColumn());
		}
		*/
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 4)));
		assertTrue(targets.contains(board.getCellAt(14, 9)));
		assertTrue(targets.contains(board.getCellAt(15, 7)));
		assertTrue(targets.contains(board.getCellAt(15, 6)));
		assertTrue(targets.contains(board.getCellAt(13, 8)));
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 5)));
		assertTrue(targets.contains(board.getCellAt(11, 4)));
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		assertTrue(targets.contains(board.getCellAt(12, 5)));
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(17, 4)));
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(14, 5)));
		assertTrue(targets.contains(board.getCellAt(16, 3)));
	}
	// Test cells for exiting a room
	@Test
	public void testRoomExit() {
		// Take one step 
		board.calcTargets(14, 18, 1);
		Set<BoardCell> testList = board.getTargets();
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 17)));
		// Take two steps
		board.calcTargets(11, 4, 2);
		testList = board.getTargets();
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(12, 5)));
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		
	}
}
