package tests;
import experiment.*;

import static org.junit.Assert.*;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class IntBoardTests {
	
	/*
	 * Constructs a 4x4 grid for testing
	 */
	IntBoard board;
	@Before
	public void setBoard()
	{
		board = new IntBoard(4,4);
	}
	
	/*
	 * Test adjacencies for position [0][0]
	 */
	@Test
	public void testAdjacency0() 
	{
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(0,1)));	
	}
	
	/*
	 * Test adjacencies for position [3][3]
	 */
	@Test
	public void testAdjacency3_3()
	{
		BoardCell cell = board.getCell(3,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3,2))); 
		assertTrue(testList.contains(board.getCell(2,3))); 
	}

	/*
	 * Test adjacencies for position [2][3]
	 */
	@Test
	public void testAdjacency2_3()
	{
		BoardCell cell = board.getCell(2,3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(1,3))); 
		assertTrue(testList.contains(board.getCell(3,3)));
		assertTrue(testList.contains(board.getCell(2,2)));
	}
	
	/*
	 * Test adjacencies for position [1][0]
	 */
	@Test
	public void testAdjacency1_0()
	{
		BoardCell cell = board.getCell(1,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(0,0))); 
		assertTrue(testList.contains(board.getCell(1,1)));
		assertTrue(testList.contains(board.getCell(2,0)));
	}
	
	/*
	 * Test adjacencies for position [1][2]
	 */
	@Test
	public void testAdjacency1_2()
	{
		BoardCell cell = board.getCell(1,2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(0,2))); 
		assertTrue(testList.contains(board.getCell(1,3)));
		assertTrue(testList.contains(board.getCell(2,2)));
		assertTrue(testList.contains(board.getCell(1,1)));
	}
	
	/*
	 * Test adjacencies for position [2][1]
	 */
	@Test
	public void testAdjacency2_1()
	{
		BoardCell cell = board.getCell(2,1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(1,1))); 
		assertTrue(testList.contains(board.getCell(2,2)));
		assertTrue(testList.contains(board.getCell(3,1)));
		assertTrue(testList.contains(board.getCell(2,0)));
	}
	
	/*
	 * Test list of targets for position [0][0] with a diceRoll of 3 
	 */
	@Test
	public void testTargets0_3()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));	
	}
	
	/*
	 *  Test list of targets for position [0][0] with a diceRoll of 1
	 */
	@Test
	public void testTargets0_1()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));	
	}
	
	/*
	 *  Test list of targets for position [0][0] with a diceRoll of 2
	 */
	@Test
	public void testTargets0_2()
	{
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
	}
	
	/*
	 *  Test list of targets for position [2][3] with a diceRoll of 2
	 */
	@Test
	public void testTargets23_2()
	{
		BoardCell cell = board.getCell(2, 3);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
	}
	
	/*
	 *  Test list of targets for position [2][3] with a diceRoll of 3
	 */
	@Test
	public void testTargets23_3()
	{
		BoardCell cell = board.getCell(2, 3);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 3)));
	}
	
	/*
	 *  Test list of targets for position [2][2] with a diceRoll of 1
	 */
	@Test
	public void testTargets22_1()
	{
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
	}
}
