package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class MBSR_BoardAdjTargetTest {
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("MBSR_ClueLayout.csv", "MBSR_ClueLegend.txt");		
		board.initialize();
	}
	
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(21, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(3, 1);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(5, 1);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(16, 6);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(14, 2);
		assertEquals(0, testList.size());
		// Test one in a corner of room on the left edge
		testList = board.getAdjList(15, 0);
		assertEquals(0, testList.size());
	}
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(8, 2);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 3)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(13, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 13)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(3, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(4, 7)));
		//TEST DOORWAY UP
		testList = board.getAdjList(10, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 9)));
		//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
		testList = board.getAdjList(10, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 2)));
		
	}
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(14, 12);
		assertTrue(testList.contains(board.getCellAt(14, 11)));
		assertTrue(testList.contains(board.getCellAt(14, 13)));
		assertTrue(testList.contains(board.getCellAt(15, 12)));
		assertTrue(testList.contains(board.getCellAt(13, 12)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN on bottom edge
		testList = board.getAdjList(9, 22);
		assertTrue(testList.contains(board.getCellAt(8, 22)));
		assertTrue(testList.contains(board.getCellAt(10, 22)));
		assertTrue(testList.contains(board.getCellAt(9, 21)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(1, 13);
		assertTrue(testList.contains(board.getCellAt(0, 13)));
		assertTrue(testList.contains(board.getCellAt(1, 12)));
		assertTrue(testList.contains(board.getCellAt(1, 14)));
		assertTrue(testList.contains(board.getCellAt(2, 13)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(17, 2);
		assertTrue(testList.contains(board.getCellAt(17, 1)));
		assertTrue(testList.contains(board.getCellAt(16, 2)));
		assertTrue(testList.contains(board.getCellAt(17, 3)));
		assertTrue(testList.contains(board.getCellAt(18, 2)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(21, 4);
		assertTrue(testList.contains(board.getCellAt(20, 4)));
		assertEquals(1, testList.size());
		
		// Test on top edge of board, two walkway pieces
		testList = board.getAdjList(0, 12);
		assertTrue(testList.contains(board.getCellAt(0, 13)));
		assertTrue(testList.contains(board.getCellAt(1, 12)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(9, 13);
		assertTrue(testList.contains(board.getCellAt(8, 13)));
		assertTrue(testList.contains(board.getCellAt(10, 13)));
		assertTrue(testList.contains(board.getCellAt(9, 12)));
		assertTrue(testList.contains(board.getCellAt(9, 14)));
		assertEquals(4, testList.size());

		
	}
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 1 away
		board.calcTargets(18, 8, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 9)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(17, 8)));
		assertTrue(targets.contains(board.getCellAt(19, 8)));
	}
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(19, 4, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 3)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));		
		assertTrue(targets.contains(board.getCellAt(16, 4)));		
	}
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(3, 2, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 2)));
		// Take two steps
		board.calcTargets(3, 2, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 1)));
		assertTrue(targets.contains(board.getCellAt(4, 3)));
	}
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(14, 8, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 8)));
		assertTrue(targets.contains(board.getCellAt(18, 8)));

	}	
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(20, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 8)));
		board.calcTargets(8, 17, 2);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 17)));
		assertTrue(targets.contains(board.getCellAt(9, 16)));	
		assertTrue(targets.contains(board.getCellAt(9, 18)));		
		assertTrue(targets.contains(board.getCellAt(8, 15)));
		assertTrue(targets.contains(board.getCellAt(8, 19)));	
		assertTrue(targets.contains(board.getCellAt(7, 16)));	
		assertTrue(targets.contains(board.getCellAt(7, 18)));
		assertTrue(targets.contains(board.getCellAt(6, 17)));	
	}
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(6, 14, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 13)));
		assertTrue(targets.contains(board.getCellAt(6, 15)));	
		assertTrue(targets.contains(board.getCellAt(7, 14)));
		assertTrue(targets.contains(board.getCellAt(5, 14)));			
	}
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(4, 10, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		assertTrue(targets.contains(board.getCellAt(4, 13)));	
		assertTrue(targets.contains(board.getCellAt(3, 12)));	
		assertTrue(targets.contains(board.getCellAt(5, 12)));	
	}	
}
